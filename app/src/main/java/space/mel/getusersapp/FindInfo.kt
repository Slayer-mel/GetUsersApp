package space.mel.getusersapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import space.mel.getusersapp.data.Result
import space.mel.getusersapp.databinding.FindInfoBinding

lateinit var findInfoBinding: FindInfoBinding

class FindInfo: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findInfoBinding=FindInfoBinding.inflate(layoutInflater)
        setContentView(findInfoBinding.root)
        intent.getParcelableExtra<Result>("FindInfo")
    }
}