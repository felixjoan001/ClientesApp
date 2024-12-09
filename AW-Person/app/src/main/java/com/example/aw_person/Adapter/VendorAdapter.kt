package com.example.aw_person.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aw_person.databinding.ItemVendorBinding
import com.example.aw_person.model.Vendor

class VendorAdapter(private val onItemClicked: (Vendor) -> Unit) :
    RecyclerView.Adapter<VendorAdapter.VendorViewHolder>() {

    private var vendors: List<Vendor> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateVendors(vendors: List<Vendor>) {
        this.vendors = vendors
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendorViewHolder {
        val binding = ItemVendorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VendorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VendorViewHolder, position: Int) {
        holder.bind(vendors[position])
    }

    override fun getItemCount(): Int = vendors.size

    inner class VendorViewHolder(private val binding: ItemVendorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var vendor: Vendor

        init {
            binding.root.setOnClickListener {
                Log.d("VendorAdapter", "Clic detectado en: ${vendor.Name}")
                onItemClicked(vendor)
            }
        }
//        Datos del reciclerView
        fun bind(vendor: Vendor) {
            this.vendor = vendor
            binding.tvVendorName.text = vendor.Name
            binding.tvAccountNumber.text = "Account Number: ${vendor.AccountNumber}"
            binding.tvPreferredStatus.text = if (vendor.PreferredVendorStatus) {
                "Preferred Vendor: Yes"
            } else {
                "Preferred Vendor: No"
            }
        }
    }
}