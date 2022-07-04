package space.mel.getusersapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import space.mel.getusersapp.data.Result
import space.mel.getusersapp.databinding.FindInfoBinding

lateinit var findInfoBinding: FindInfoBinding
var resultList:List<Result>?=null
var findInfoAdapter:RecyclerViewAdapter?=null

class FindInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findInfoBinding = FindInfoBinding.inflate(layoutInflater)
        setContentView(findInfoBinding.root)
        resultList = intent.getParcelableArrayExtra("FindInfo")?.map {
            it as Result
        }?.toList()
        initAdapter()
        resultList?.let { findInfoAdapter?.setItems(it) }

        findInfoBinding.btnGet.setOnClickListener{
            findInfoAdapter?.setItems(comparison())
        }

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "Точный поиск"
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    fun comparison():List<Result> {
        val s = findInfoBinding.edtInput.text.toString().lowercase()
        val filtredResult = resultList?.filter { person ->
            when {
                person.name?.first?.lowercase()?.contains(s) == true ->true
                person.name?.last?.lowercase()?.contains(s) == true ->true
                else -> false
            }
        } ?: emptyList()
        return filtredResult
    }

    fun initAdapter() {
        findInfoAdapter=RecyclerViewAdapter (
           onClick =::startFoundUserFullInformation
                )
        findInfoBinding.rvUsers.adapter = findInfoAdapter
    }

    fun startFoundUserFullInformation(result: Result) {
        val intent = Intent()
        intent.putExtra(
            "UserFullInformation",
            result
        )
        intent.setClass(this, UserFullInformation::class.java)
        startActivity(intent)
    }

}
