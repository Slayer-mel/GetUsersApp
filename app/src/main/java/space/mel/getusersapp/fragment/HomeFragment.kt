package space.mel.getusersapp.fragment

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import space.mel.getusersapp.R
import space.mel.getusersapp.RecyclerViewAdapter
import space.mel.getusersapp.dao.UsersDao
import space.mel.getusersapp.data.Result
import space.mel.getusersapp.databinding.HomeFragmentBinding
import space.mel.getusersapp.retrofit.UserApi

class HomeFragment : BaseFragment() {
    lateinit var myBinding: HomeFragmentBinding
    var myAdapter: RecyclerViewAdapter? = null
    var progressBar: Dialog? = null
    var resultList: ArrayList<Result> = arrayListOf()
    val userApi: UserApi by inject()
    val dao: UsersDao by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        myBinding = HomeFragmentBinding.inflate(layoutInflater)
        return myBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initListeners()
        initSwipeToRefresh()
        initProgressBar()
        lifecycleScope.launch(Dispatchers.IO) {
            val results = dao.getAllUsers()?.results
                ?: emptyList() // if results!=null, return userDataBaseDao.getAllUsers()?.results  else if results==null, return emptyList()
            resultList.clear() //ArrayList<Result> will be empty
            resultList.addAll(results) // add results to resultList
            withContext(Dispatchers.Main) {
                myAdapter?.setItems(resultList)
            }
        }
    }

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
        findNavController().navigate(
            R.id.action_homeFragment_to_userFullInformationFragment2,
            Bundle().apply {
                putParcelable(
                    "UserFullInformation",
                    result
                )
            })
    }

    fun getData() {
        val inputIntText: Int = myBinding.edtAmount.text.toIntOrZero()
        val handler = CoroutineExceptionHandler { _, t ->

            myBinding.swipe.isRefreshing = false
            progressBar?.dismiss()
            Log.d("LOGSLOGS", "Network Error: ${t.message}")
        }
        progressBar?.show()

        CoroutineScope(Dispatchers.IO).launch(handler) {
            val users = userApi.getUsers(inputIntText, gender = "female")
            dao.insert(users) // enter data to DB from retrofit
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
        progressBar = Dialog(requireContext())
        progressBar?.setContentView(R.layout.progress_bar_alert)
    }

    fun Editable?.toIntOrZero(): Int {
        return try {
            toString().toInt()
        } catch (e: Exception) {
            0
        }
    }


    override fun getTitle(): Int {
        return R.string.app_name
    }

    override fun isSideBarNeeded(): Boolean {
        return true
    }

}
