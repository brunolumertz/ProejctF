package lul.myapplication.ui.activitys

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import lul.myapplication.R

class LoginActivity : AppCompatActivity() {

    private lateinit var fba: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        fba = FirebaseAuth.getInstance()

        texto_cadastre_agora.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
            finish()
        }

        login_botao.setOnClickListener {
            fazLogin()
        }

        texto_esqueceu_senha.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Esqueci a senha:")
            val view = layoutInflater.inflate(R.layout.dialog_esqueceu_senha, null)
            val username = view.findViewById<EditText>(R.id.campo_email_para_resetar_senha)
            builder.setView(view)
            builder.setPositiveButton("Resetar senha", DialogInterface.OnClickListener { _, _ ->
                esqueceuSenha(username)
            })
            builder.setNegativeButton("Fechar", DialogInterface.OnClickListener { _, _ -> })
            builder.show()
        }
    }

    private fun esqueceuSenha(username: EditText) {
        if (username.text.toString().isEmpty()) {
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()) {
            return
        }
        fba.sendPasswordResetEmail(username.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email enviado.", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun fazLogin() {
        if (login_email.text.toString().isEmpty()) {
            login_email.error = "Por favor, digite um email."
            login_email.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(login_email.text.toString()).matches()) {
            login_email.error = "Por favor, digite um email válido."
            login_email.requestFocus()
            return
        }
        if (login_senha.text.toString().isEmpty()) {
            login_senha.error = "Por favor, digite uma senha."
            login_senha.requestFocus()
            return
        }

        fba.signInWithEmailAndPassword(login_email.text.toString(), login_senha.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = fba.currentUser
                    updateUI(user)
                } else {
                    updateUI(null)
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = fba.currentUser
        updateUI(currentUser)
    }

    //envia email de verificação
    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    baseContext,
                    "Por favor, verifica seu endereço de email.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


}