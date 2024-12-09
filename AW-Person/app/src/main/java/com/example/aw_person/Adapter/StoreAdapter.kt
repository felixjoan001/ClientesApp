package com.example.aw_person.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aw_person.databinding.ItemStoreBinding
import com.example.aw_person.model.Store

class StoreAdapter(private val onItemClicked: (Store) -> Unit) :
    RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {

    private var stores: List<Store> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateStores(stores: List<Store>) {
        this.stores = stores
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val binding = ItemStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        holder.bind(stores[position])
    }

    override fun getItemCount(): Int = stores.size

    inner class StoreViewHolder(private val binding: ItemStoreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var store: Store

        init {
            binding.root.setOnClickListener {
                Log.d("StoreAdapter", "Clic detectado en: ${store.Name}")
                onItemClicked(store)
            }
        }

        fun bind(store: Store) {
            this.store = store
            binding.tvStoreName.text = store.Name
            binding.tvSalesPersonId.text = "Sales Person ID: ${store.SalesPersonID}"
        }
    }
}