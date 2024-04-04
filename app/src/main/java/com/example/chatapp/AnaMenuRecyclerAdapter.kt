package com.example.chatapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_row.view.*

class AnaMenuRecyclerAdapter: RecyclerView.Adapter<AnaMenuRecyclerAdapter.PostHolder>() {
    class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    private val  diffUtil = object :DiffUtil.ItemCallback<Post>(){
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }


        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }


    }


    private val recycleListDiffer = AsyncListDiffer(this,diffUtil)

    var chats : List<Post>
        get() = recycleListDiffer.currentList
            set(value) = recycleListDiffer.submitList(value)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,parent,false)
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val textView_yorum = holder.itemView.findViewById<TextView>(R.id.recycler_row_kullanici_yorum)
        val textView_kullanıcı = holder.itemView.findViewById<TextView>(R.id.recycler_row_kullanici_email)
        textView_kullanıcı.text = "${chats.get(position).user}"
        textView_yorum.text = "${chats.get(position).text}"


    }

    override fun getItemCount(): Int {
        return chats.size
    }

}
