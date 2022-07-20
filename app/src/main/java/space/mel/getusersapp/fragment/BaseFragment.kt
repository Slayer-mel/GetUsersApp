package space.mel.getusersapp.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import space.mel.getusersapp.MainActivity
import space.mel.getusersapp.R
import space.mel.getusersapp.data.Result

open class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setTitle(getTitle())
       val currentActivity = activity as? MainActivity
        val isNeeded = isSideBarNeeded()
        if (isNeeded) currentActivity?.enableSideBar()
        else currentActivity?.disableSideBar()
    }

    open fun getTitle() : Int = R.string.app_name

    open fun isSideBarNeeded() : Boolean = false

    fun setDataInActivity(list : List<Result>) {
        (activity as? MainActivity)?.setCurrentData(list)
    }

    fun getDataFromActivity() = (activity as? MainActivity)?.getCurrentList() ?: emptyList()
}