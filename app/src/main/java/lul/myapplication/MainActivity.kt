package lul.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_filme.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configuraNavController()
        configuraDrawble()

    }

    private fun configuraNavController() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_navi) as NavHostFragment
        val navController = navHostFragment.navController

        navView.setupWithNavController(navController)
    }

    private fun configuraDrawble(){
        toggle = ActionBarDrawerToggle(this,drawerLayout, R.string.abrir, R.string.fechar)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        nav_menu_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.perfil_menu_item -> Toast.makeText(this, "Clicked item three", Toast.LENGTH_SHORT)
                R.id.camera_menu_item -> Toast.makeText(this, "Clicked item three", Toast.LENGTH_SHORT)
                R.id.configura_menu_item -> Toast.makeText(this, "Clicked item three", Toast.LENGTH_SHORT)
                R.id.mapa_menu_item -> Toast.makeText(this, "Clicked item three", Toast.LENGTH_SHORT)
            }
            true
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}