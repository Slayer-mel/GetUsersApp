package space.mel.getusersapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import space.mel.getusersapp.data.Result
import space.mel.getusersapp.databinding.UserFullInformationBinding

lateinit var userFullInformationBinding: UserFullInformationBinding

class UserFullInformation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userFullInformationBinding = UserFullInformationBinding.inflate(layoutInflater)
        setContentView(userFullInformationBinding.root)

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

        val data = intent.getParcelableExtra<Result>("UserFullInformation")
        data?.let {
            setContent(it)
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
}