package com.exchangerate.ui.popular

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exchangerate.databinding.ItemListBinding

class AdapterPopular(
    private var listenner: OnClickListener
) :
    RecyclerView.Adapter<AdapterPopular.ViewHolder>() {

    private var rates: ArrayList<Pair<String, String>> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(ratesList: ArrayList<Pair<String, String>>) {
        rates.clear()
        rates.addAll(ratesList)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun sortListPriceAscending() {
        val sortedPrice = rates.sortedWith(compareBy { it.first.toDouble() })
        rates.clear()
        rates.addAll(sortedPrice)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun sortListPriceDescending() {
        val sortedPrice = rates.sortedByDescending { it.first.toDouble() }
        rates.clear()
        rates.addAll(sortedPrice)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(rate: Pair<String, String>) {
            with(binding) {
                textViewPrice.text = rate.first
                textViewName.text = rate.second

                clickWishlist.setOnClickListener {
                    listenner.addWishlist(rate.second, rate.first)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(rates[position])
    }

    override fun getItemCount() = rates.size

    interface OnClickListener {
        fun addWishlist(name: String, currency: String)
    }
}