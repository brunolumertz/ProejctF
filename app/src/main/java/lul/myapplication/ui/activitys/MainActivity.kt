package lul.myapplication.ui.activitys

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.facebook.login.LoginManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_menu_lateral.*
import lul.myapplication.R


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var fba: FirebaseAuth
    private lateinit var toggle: ActionBarDrawerToggle
    var currentPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fba = FirebaseAuth.getInstance()

//        Glide.with(this).load(currentuser?.photoUrl).into(foto_do_perfil)

        configuraNavController()
        configuraDrawble()
        fecthUser()
    }

    private fun configuraNavController() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_navi) as NavHostFragment
        val navController = navHostFragment.navController

        navView.setupWithNavController(navController)
    }

    //Menu lateral
    private fun configuraDrawble() {
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.abrir, R.string.fechar)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()



        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        nav_menu_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.camera_menu_item -> Toast.makeText(
                    this,
                    "Clicked item three",
                    Toast.LENGTH_LONG
                )
                R.id.mapa_menu_item -> {
                    val intent = Intent(this, MapsActivity::class.java)
                    startActivity(intent)
                }
                R.id.configura_menu_item -> Toast.makeText(
                    this,
                    "Clicked item three",
                    Toast.LENGTH_LONG
                )
                R.id.botao_de_logout -> desconectar()
            }
            true
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun desconectar() {
        fba.signOut()
        LoginManager.getInstance().logOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun fecthUser() {
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    it.toString()
                    val user = it.getValue(User::class.java)
                    texto_nome_nav_menu.text = user!!.name
                    Glide.with(this@MainActivity).load(user.profileImageUrl).into(foto_do_perfil)
//                    Glide.with(this).load("http://goo.gl/gEgYUd").into(imageView)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO()
            }
        })
    }


}