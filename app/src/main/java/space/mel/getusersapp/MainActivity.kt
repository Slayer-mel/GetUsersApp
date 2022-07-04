package space.mel.getusersapp

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.MenuItem
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
import java.lang.Exception

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
                    myAdapter?.currentList?.let { list ->
                        if(list.isNotEmpty()) startFindInfo(list)
                    }
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

    fun startFindInfo(resultList: List<Result>) {
        val intent = Intent().apply {
            putExtra(
                "FindInfo",
                resultList.toTypedArray()
            )
            setClass(this@MainActivity, FindInfo::class.java)
        }
        startActivity(intent)
    }

    fun getData() {
        val s: Int = myBinding.edtAmount.text.toIntOrZero()
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

    fun Editable?.toIntOrZero() : Int {
        return try {
            toString().toInt()
        } catch (e : Exception) {
            0
        }
    }
}