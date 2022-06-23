package space.mel.getusersapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import space.mel.getusersapp.databinding.RvItemBinding
import space.mel.getusersapp.data.Result

class RecyclerViewAdapter(val onClick: (Result) -> Unit) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    var currentList: List<Result> = listOf()

    fun setItems(list: List<Result>) {
        currentList = list
        notifyDataSetChanged()
    }
    inner class MyViewHolder(
        val myBinding :RvItemBinding
    ) : RecyclerView.ViewHolder(myBinding.root) {
        fun setData(result: Result) {
            myBinding.tvName.text = "${result.name?.first} ${result.name?.last} "
            Glide
                .with(myBinding.root.context)
                .load(result.picture?.medium)
                .into(myBinding.imgPhoto)
            myBinding.root.setOnClickListener {
                onClick.invoke(result)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}
