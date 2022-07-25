package space.mel.getusersapp.fragment

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.*
import space.mel.getusersapp.R
import space.mel.getusersapp.RecyclerViewAdapter
import space.mel.getusersapp.data.Result
import space.mel.getusersapp.databinding.HomeFragmentBinding
import space.mel.getusersapp.retrofit.UserNetwork

class HomeFragment : BaseFragment() {
    lateinit var myBinding: HomeFragmentBinding
    var myAdapter: RecyclerViewAdapter? = null
    var progressBar: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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
        findNavController().navigate(R.id.action_homeFragment_to_userFullInformationFragment2, Bundle().apply {
            putParcelable(
                "UserFullInformation",
                result
            )
        })
    }

    fun startFindInfo(resultList: List<Result>) {

        findNavController().navigate(R.id.action_homeFragment_to_findInfoFragment2, Bundle().apply {
            putParcelableArray(
                "FindInfo",
                resultList.toTypedArray()
            )
        })
    }

    fun getData() {
        val inputIntText: Int = myBinding.edtAmount.text.toIntOrZero()
        val handler = CoroutineExceptionHandler { _, _ ->

            myBinding.swipe.isRefreshing = false
            progressBar?.dismiss()
        }
        progressBar?.show()

        CoroutineScope(Dispatchers.IO).launch(handler) {
            val users = UserNetwork.retrofit.getUsers(inputIntText, gender = "female")

            withContext(Dispatchers.Main) {
                myAdapter?.setItems(users.results)
                setDataInActivity(users.results)
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
        myAdapter?.setItems(getDataFromActivity())
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