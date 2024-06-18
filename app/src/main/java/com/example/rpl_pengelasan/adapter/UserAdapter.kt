package com.example.rpl_pengelasan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rpl_pengelasan.R
import com.example.rpl_pengelasan.data.entity.User

class UserAdapter (private val users: ArrayList<User>) : RecyclerView.Adapter< UserAdapter.UserViewHolder >() {
    // tambhakan
    class UserViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val textView: TextView = view.findViewById(R.id.text_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_user, parent, false)

        )
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.textView.text = user.name
    }

    // fun untuk setData
    fun setData(list : List<User>){
        users.clear()
        users.addAll(list)
        notifyDataSetChanged()
    }
}