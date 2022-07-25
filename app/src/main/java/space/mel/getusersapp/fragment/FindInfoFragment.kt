package space.mel.getusersapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import space.mel.getusersapp.R
import space.mel.getusersapp.RecyclerViewAdapter
import space.mel.getusersapp.data.Result
import space.mel.getusersapp.databinding.FindInfoBinding

class FindInfoFragment : BaseFragment() {
    lateinit var findInfoBinding: FindInfoBinding
    var resultList: List<Result>? = null
    var findInfoAdapter: RecyclerViewAdapter? = null

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
        resultList = requireArguments().getParcelableArrayList("FindInfo")
        initAdapter()
        resultList?.let { findInfoAdapter?.setItems(it) }


        findInfoBinding.btnGet.setOnClickListener {
            findInfoAdapter?.setItems(compareUserData())
        }
    }

    fun compareUserData(): List<Result> {
        val enteredText = findInfoBinding.edtInput.text.toString().lowercase()
        return getFilteredList(enteredText)
    }

    private fun getFilteredList(enteredText: String) = resultList?.filter { person ->
        when {
            person.name?.first?.lowercase()?.contains(enteredText) == true -> true
            person.name?.last?.lowercase()?.contains(enteredText) == true -> true
            else -> false
        }
    } ?: emptyList()

    fun initAdapter() {
        findInfoAdapter = RecyclerViewAdapter(
            onClick = ::startFoundUserFullInformation
        )
        findInfoBinding.rvUsers.adapter = findInfoAdapter
    }

    fun startFoundUserFullInformation(result: Result) {
        findNavController().navigate(R.id.action_homeFragment_to_findInfoFragment2, Bundle().apply {
            putParcelable(
                "UserFullInformation",
                result)
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = childFragmentManager
        fragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .replace(R.id.fragmentContainer, HomeFragment())
            .addToBackStack(null)
            .commit()
        }

    override fun getTitle(): Int {
        return R.string.find_user
    }
}