package lul.myapplication.ui.activitys

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import lul.myapplication.R
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import java.lang.RuntimeException

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 120
    }

    private lateinit var fba: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        fba = FirebaseAuth.getInstance()
        callbackManager = CallbackManager.Factory.create()

        login_fb_button.setOnClickListener {
            logarFB()
        }

        texto_cadastre_agora.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
            finish()
        }

        login_botao.setOnClickListener {
            fazLogin()
        }

        botao_conta_google.setOnClickListener {
            signIn()
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

//        throw RuntimeException("Test Crash")
    }

    private fun logarFB() {
        login_fb_button.registerCallback(callbackManager, object: FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                handleFacebookAcessToken(result!!.accessToken)
            }

            override fun onCancel() {
                TODO("Not yet implemented")
            }

            override fun onError(error: FacebookException?) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun handleFacebookAcessToken(accessToken: AccessToken?) {
        val credential = FacebookAuthProvider.getCredential(accessToken!!.token)
        fba.signInWithCredential(credential)
            .addOnFailureListener{  e->
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()

            }
            .addOnSuccessListener(this) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
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

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SingInActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("SingInActivity", "Google sign in failed", e)
                }
            } else {
                Log.w("SingInActivity", exception.toString())
            }
        }
    }


    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        fba.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SingInActivity", "signInWithCredential:success")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SingInActivity", "signInWithCredential:failure", task.exception)
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