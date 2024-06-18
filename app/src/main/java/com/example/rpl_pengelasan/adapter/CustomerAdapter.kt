package com.example.rpl_pengelasan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rpl_pengelasan.R
import com.example.rpl_pengelasan.data.entity.Anggota
import com.example.rpl_pengelasan.data.entity.Customer

class CustomerAdapter (
    private val customers: ArrayList<Customer>,
    val listener : CustomerAdapter.OnAdapterListener
) : RecyclerView.Adapter< CustomerAdapter.CustomerViewHolder >() {
    // tambhakan
    class CustomerViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val textName: TextView = view.findViewById(R.id.text_name_cus)
        val textPhone: TextView = view.findViewById(R.id.text_phone_cus)
        val textAddress: TextView = view.findViewById(R.id.text_address)
        val iconUpdate: ImageView = view.findViewById(R.id.icon_edit)
        val iconDelete: ImageView = view.findViewById(R.id.icon_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        return CustomerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_customer, parent, false)

        )
    }

    override fun getItemCount(): Int {
        return customers.size
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val user = customers[position]
        holder.textName.text = user.name
        holder.textPhone.text = user.phone_number
        holder.textAddress.text = user.address
        // memasang event untuk icon
        holder.iconUpdate.setOnClickListener{
            listener.onClick(user, 1)
        }
        holder.iconDelete.setOnClickListener{
            listener.onClick(user, 2)
        }
    }

    // fun untuk setData
    fun setData(list : List<Customer>){
        customers.clear()
        customers.addAll(list)
        notifyDataSetChanged()
    }
    interface OnAdapterListener{
        fun onClick(customer: Customer)
        fun onClick(customer: Customer, intentType: Int)
    }
}