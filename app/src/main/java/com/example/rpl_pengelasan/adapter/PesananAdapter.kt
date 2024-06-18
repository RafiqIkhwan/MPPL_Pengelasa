package com.example.rpl_pengelasan.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.rpl_pengelasan.HalamanKedua
import com.example.rpl_pengelasan.R
import com.example.rpl_pengelasan.data.RoomDB
import com.example.rpl_pengelasan.data.entity.Customer
import com.example.rpl_pengelasan.data.entity.Pesanan
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

public class PesananAdapter (
    private val pesanans: ArrayList<Pesanan>,
    val listener : PesananAdapter.OnAdapterListener,
    private val context: Context

) : RecyclerView.Adapter< PesananAdapter.CustomerViewHolder >() {

    lateinit var dbRoom: RoomDB;

    val db by lazy { RoomDB(context) }

    // tambhakan
    class CustomerViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val id_customer: TextView = view.findViewById(R.id.id_customer)
        val nama_customer: TextView = view.findViewById(R.id.nama_customer)
        val deadline: TextView = view.findViewById(R.id.deadline)
        val wadahPesanan: LinearLayout = view.findViewById(R.id.wadah_pesanan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        return CustomerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_pesanan, parent, false)

        )
    }

    override fun getItemCount(): Int {
        return pesanans.size
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val pesanan = pesanans[position]

        if (pesanan.id != null){
            CoroutineScope(Dispatchers.IO).launch {
                val pelanggan : List<Customer> = db.customerDao().getOne(pesanan.pelangganId)

                withContext(Dispatchers.Main) {
                    for (index in pelanggan.indices ){

                        if (pesanan.pelangganId == pelanggan[index].id){
                            holder.id_customer.text = pesanan.id.toString()
                            holder.nama_customer.text = pelanggan[index].name

                            val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                            val formattedDate = dateFormat.format(pesanan.tgl_deadline)
                            // kita pasang tanggal yang sudah diformat
                            holder.deadline.text = formattedDate
                        }
                    }

                }
            }
        }

        // memasang event untuk icon
        holder.wadahPesanan.setOnClickListener{

            // jika diklik intentType otomastis dilabeli 1 yaitu langsung menuju ke halaman view 1 pesanan
            listener.onClick(pesanan, 1)
        }
    }

    // fun untuk setData
    fun setData(list : List<Pesanan>){
        pesanans.clear()
        pesanans.addAll(list)
        notifyDataSetChanged()
    }
    public interface OnAdapterListener{
        fun onClick(pesanan: Pesanan)
        fun onClick(pesanan: Pesanan, intentType: Int)
    }
}