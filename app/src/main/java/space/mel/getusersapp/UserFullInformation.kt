package space.mel.getusersapp

import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import space.mel.getusersapp.data.Result
import space.mel.getusersapp.databinding.UserFullInformationBinding

lateinit var userFullInformationBinding: UserFullInformationBinding

class UserFullInformation : AppCompatActivity() {
    var radioGroup: RadioGroup? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userFullInformationBinding = UserFullInformationBinding.inflate(layoutInflater)
        setContentView(userFullInformationBinding.root)


        radioGroup = userFullInformationBinding.radioGroup
        val radioRed = userFullInformationBinding.radioRed
        radioRed.setOnClickListener {
            userFullInformationBinding.userFullInformation.setBackgroundColor(resources.getColor(R.color.red))
        }
        val radioBlue = userFullInformationBinding.radioBlue
        radioBlue.setOnClickListener {
            userFullInformationBinding.userFullInformation.setBackgroundColor(resources.getColor(R.color.blue))
        }
        val radioYellow = userFullInformationBinding.radioYellow
        radioYellow.setOnClickListener {
            userFullInformationBinding.userFullInformation.setBackgroundColor(resources.getColor(R.color.yellow))
        }
        val radioGreen = userFullInformationBinding.radioGreen
        radioGreen.setOnClickListener {
            userFullInformationBinding.userFullInformation.setBackgroundColor(resources.getColor(R.color.green))
        }



        val data = intent.getParcelableExtra<Result>("UserFullInformation")
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
}