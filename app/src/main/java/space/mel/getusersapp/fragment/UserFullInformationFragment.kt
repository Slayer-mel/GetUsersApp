package space.mel.getusersapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import space.mel.getusersapp.R
import space.mel.getusersapp.data.Result
import space.mel.getusersapp.databinding.UserFullInformationBinding


class UserFullInformationFragment : BaseFragment() {
    lateinit var userFullInformationBinding: UserFullInformationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userFullInformationBinding = UserFullInformationBinding.inflate(inflater)
        return userFullInformationBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userFullInformationBinding.radioRed.setOnClickListener {
            userFullInformationBinding.userFullInformation.setBackgroundColor(resources.getColor(R.color.red))
        }
        userFullInformationBinding.radioBlue.setOnClickListener {
            userFullInformationBinding.userFullInformation.setBackgroundColor(resources.getColor(R.color.blue))
        }
        userFullInformationBinding.radioYellow.setOnClickListener {
            userFullInformationBinding.userFullInformation.setBackgroundColor(resources.getColor(R.color.yellow))
        }
        userFullInformationBinding.radioGreen.setOnClickListener {
            userFullInformationBinding.userFullInformation.setBackgroundColor(resources.getColor(R.color.green))
        }
        userFullInformationBinding.radioWhite.setOnClickListener {
            userFullInformationBinding.userFullInformation.setBackgroundColor(resources.getColor(R.color.white))
        }


        /*val data = arguments?.getParcelable<Result>("UserFullInformation")
        if (data != null) {
            setContent(data)
        }*/
        val data = requireArguments().getParcelable<Result>("UserFullInformation")
        if (data != null) {
            setContent(data)
        }
    }

    fun setContent(result: Result) {
        with(userFullInformationBinding) {
            tvFullName.text = "${result.name?.first} ${result.name?.last} ${result.name?.title} " +
                    " I live on ${result.location?.street?.name}${result.location?.street?.number} " +
                    "in ${result.location?.city}"
            Glide
                .with(userFullInformationBinding.root.context)
                .load(result.picture?.large)
                .into(userFullInformationBinding.ivProfile)
        }
    }

    override fun getTitle(): Int {
        return R.string.exact_search
    }
}
