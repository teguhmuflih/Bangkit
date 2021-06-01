package capstone.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import capstone.myapplication.R
import capstone.myapplication.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        setSupportActionBar(mainBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBa r?.title = resources.getString(R.string.app_name)

        val navController = findNavController(R.id.nav_host_fragment)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.nav_home, R.id.nav_camera
        ).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



        /*mainBinding.bottomNavigationView.background = null
        mainBinding.bottomNavigationView.menu.getItem(1).isEnabled = false

        addFragment(HomeFragment.newInstance())

        mainBinding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.nav_home -> replaceFragment(HomeFragment.newInstance())
                R.id.nav_account -> replaceFragment(ProfileFragment.newInstance())
                //else -> replaceFragment(HomeFragment.newInstance())
            }
            true
        }

        mainBinding.fab.setOnClickListener {
            replaceFragment(CameraFragment.newInstance())
        }*/
    }



    /*
    private fun replaceFragment(fragment: Fragment){
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragment_container, fragment).addToBackStack(Fragment::class.java.simpleName).commit()
    }

    private fun addFragment(fragment: Fragment){
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.add(R.id.fragment_container, fragment).addToBackStack(Fragment::class.java.simpleName).commit()
    }
     */

    //setting menu in action bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.bt_setting -> Toast.makeText(this, "Ke setting", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}