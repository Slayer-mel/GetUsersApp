package space.mel.getusersapp

import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import space.mel.getusersapp.data.Result
import space.mel.getusersapp.databinding.ActivityMainBinding
import space.mel.getusersapp.fragment.HomeFragment

lateinit var mainBinding: ActivityMainBinding
private lateinit var actionBarToggle: ActionBarDrawerToggle


class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    val currentDataList: ArrayList<Result> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        initBarToggle()
        setUpSideBar()

        //navController=Navigation.findNavController(this, R.id.nav_host)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

        if (savedInstanceState == null) {
            navigateToFragment(HomeFragment())
        }
    }

    private fun setUpSideBar() {
        mainBinding.drawerLayout.addDrawerListener(actionBarToggle)
        actionBarToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBarToggle.syncState()
        mainBinding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.myProfile -> {
                    //TODO: Change to Room
                    if (currentDataList.isNotEmpty()) {
                        findNavController(R.id.navView).navigate(R.id.action_homeFragment_to_findInfoFragment2,
                            Bundle().apply
                            {
                                putParcelableArrayList(
                                    "FindInfo",
                                    currentDataList                                )
                            })

                        mainBinding.drawerLayout.closeDrawer(Gravity.LEFT)
                    }

                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        if (actionBarToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)

    private fun navigateToFragment(fragment: Fragment) {
        //navController.navigate(R.id.action_homeFragment_to_findInfoFragment2)
    }

    private fun initBarToggle() {
        actionBarToggle = ActionBarDrawerToggle(
            this,
            mainBinding.drawerLayout,
            R.string.nav_open,
            R.string.nav_close
        )
    }

    open fun enableSideBar() {
        mainBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    fun disableSideBar() {
        mainBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    fun setCurrentData(list: List<Result>) {
        currentDataList.clear()
        currentDataList.addAll(list)
    }

    fun getCurrentList() = currentDataList
}
