package space.mel.getusersapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import space.mel.getusersapp.data.Result
import space.mel.getusersapp.databinding.ActivityMainBinding
import space.mel.getusersapp.retrofit.UserNetwork

lateinit var myBinding: ActivityMainBinding
var myAdapter: RecyclerViewAdapter? = null
private lateinit var actionBarToggle: ActionBarDrawerToggle
var progressBar : Dialog? = null


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(myBinding.root)
        initAdapter()
        initListeners()
        initSwipeToRefresh()
        initProgressBar()
        initBarToggle()

        myBinding.drawerLayout.addDrawerListener(actionBarToggle)
        actionBarToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBarToggle.syncState()
        myBinding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.myProfile -> {
                    Toast.makeText(this, "Найти", Toast.LENGTH_LONG).show()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun initBarToggle() {
        actionBarToggle = ActionBarDrawerToggle(
            this,
            myBinding.drawerLayout,
            R.string.nav_open,
            R.string.nav_close
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        if (actionBarToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)


    private fun initSwipeToRefresh() {
        myBinding.swipe.setOnRefreshListener {
            getData()
        }
    }

    private fun initListeners() {
        myBinding.btnGet.setOnClickListener {
            getData()
        }
    }

    fun startFullInformation(result: Result) {
        val intent = Intent()
        intent.putExtra(
            "UserFullInformation",
            result
        )
        intent.setClass(this, UserFullInformation::class.java)
        startActivity(intent)
    }

    fun startFindInfo(result: Result) {
        val intent = Intent()
        intent.putExtra(
            "FindInfo",
            result
        )
        intent.setClass(this, FindInfo::class.java)
        startActivity(intent)
    }

    fun getData() {
        val s: Int = myBinding.edtAmount.text.toString().toInt()
        val handler = CoroutineExceptionHandler { _, _ ->

            myBinding.swipe.isRefreshing = false
            progressBar?.dismiss()
        }
        progressBar?.show()

        lifecycleScope.launch(handler) {
            val users = UserNetwork.retrofit.getUsers(s, gender = "female")

            withContext(Dispatchers.Main) {
                myAdapter?.setItems(users.results)
                myBinding.swipe.isRefreshing = false
                progressBar?.dismiss()
            }
        }
    }

    fun initAdapter() {
        myAdapter = RecyclerViewAdapter(
            onClick = ::startFullInformation
        )
        myBinding.rvUsers.adapter = myAdapter
    }

    fun initProgressBar() {
        progressBar = Dialog(this)
        progressBar?.setContentView(R.layout.progress_bar_alert)
    }
}