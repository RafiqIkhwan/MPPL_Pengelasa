package com.example.rpl_pengelasan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rpl_pengelasan.R
import com.example.rpl_pengelasan.data.entity.Anggota
import com.example.rpl_pengelasan.data.entity.User

class AnggotaAdapter (
    private val anggotas: ArrayList<Anggota>,
    private val listener: OnAdapterListener

) : RecyclerView.Adapter< AnggotaAdapter.AnggotaViewHolder >() {
    // tambhakan
    class AnggotaViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val textName: TextView = view.findViewById(R.id.text_name)
        val textPhone: TextView = view.findViewById(R.id.text_phone)
        val itemAnggota: LinearLayout = view.findViewById(R.id.item_anggota)
        val iconEdit: ImageView = view.findViewById(R.id.icon_edit)
        val iconDelete: ImageView = view.findViewById(R.id.icon_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnggotaViewHolder {
        return AnggotaViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_anggota, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return anggotas.size
    }

    override fun onBindViewHolder(holder: AnggotaViewHolder, position: Int) {
        val user = anggotas[position]
        holder.textName.text = user.name
        holder.textPhone.text = user.phone_number

        // mensetting yang akan diklik nantinya
        holder.itemAnggota.setOnClickListener{
            listener.onClick( user )
        }
        holder.iconEdit.setOnClickListener{
            listener.onClick( user , 1)
        }
        holder.iconDelete.setOnClickListener{
            listener.onClick( user , 2)
        }
    }

    // fun untuk setData
    fun setData(list : List<Anggota>){
        anggotas.clear()
        anggotas.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun onClick(anggota: Anggota)
        fun onClick(anggota: Anggota, intentType: Int)
    }
}