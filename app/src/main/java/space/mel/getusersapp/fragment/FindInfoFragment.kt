package space.mel.getusersapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import space.mel.getusersapp.R
import space.mel.getusersapp.RecyclerViewAdapter
import space.mel.getusersapp.dao.UsersDao
import space.mel.getusersapp.data.Result
import space.mel.getusersapp.databinding.FindInfoBinding

class FindInfoFragment : BaseFragment() {
    lateinit var findInfoBinding: FindInfoBinding
    var resultList: ArrayList<Result> = arrayListOf()
    var findInfoAdapter: RecyclerViewAdapter? = null
    val dao : UsersDao by inject()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        findInfoBinding = FindInfoBinding.inflate(layoutInflater)
        return findInfoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        lifecycleScope.launch (Dispatchers.IO){
            val results = dao.getAllUsers()?.results ?: emptyList() // if results!=null, return userDataBaseDao.getAllUsers()?.results  else if results==null, return emptyList()
            resultList.clear() //ArrayList<Result> will be empty
            resultList.addAll(results) // add results to resultList
            withContext(Dispatchers.Main) {
                findInfoAdapter?.setItems(resultList)
            }
        }

        findInfoBinding.btnGet.setOnClickListener {
            findInfoAdapter?.setItems(compareUserData())
        }
    }


    fun compareUserData(): List<Result> {
        val enteredText = findInfoBinding.edtInput.text.toString().lowercase()
        val filtredResult = resultList.filter { person ->
            when {
                person.name?.first?.lowercase()?.contains(enteredText) == true -> true
                person.name?.last?.lowercase()?.contains(enteredText) == true -> true
                else -> false
            }
        }
        return filtredResult
    }

    fun initAdapter() {
        findInfoAdapter = RecyclerViewAdapter(
            onClick = ::startFoundUserFullInformation
        )
        findInfoBinding.rvUsers.adapter = findInfoAdapter
    }

    fun startFoundUserFullInformation(result: Result) {

        val bundle = Bundle()
        bundle.putSerializable(
            "UserFullInformation",
            result)
        findNavController().navigate(R.id.action_homeFragment_to_findInfoFragment2, bundle)
    }

    override fun getTitle(): Int {
        return R.string.find_user
    }
}