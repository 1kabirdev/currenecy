package com.exchangerate.ui.wishlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exchangerate.databinding.ItemListWishlistBinding
import com.exchangerate.model.WishlistDao

class AdapterWishlist(
    private var listener: OnClickListenner,
) : RecyclerView.Adapter<AdapterWishlist.ViewHolder>() {

    private var wishlist: ArrayList<WishlistDao> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun addItemList(list: ArrayList<WishlistDao>) {
        wishlist.clear()
        wishlist.addAll(list)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun sortListPriceAscending() {
        val sortedPrice = wishlist.sortedWith(compareBy { it.price })
        wishlist.clear()
        wishlist.addAll(sortedPrice)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun sortListPriceDescending() {
        val sortedPrice = wishlist.sortedByDescending { it.price }
        wishlist.clear()
        wishlist.addAll(sortedPrice)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemListWishlistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bindView(wishlistDao: WishlistDao) {
            with(binding) {
                textViewPrice.text = wishlistDao.price.toString()
                textViewName.text = wishlistDao.currency

                clickRemoveWishlist.setOnClickListener {
                    listener.clickRemove(wishlistDao)
                    removeWishlist(wishlistDao)
                }
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        private fun removeWishlist(wishlistDao: WishlistDao) {
            wishlist.remove(wishlistDao)
            notifyDataSetChanged()
            if (wishlist.size == 0) {
                listener.notWishlist()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListWishlistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindView(wishlist[position])

    override fun getItemCount(): Int = wishlist.size

    interface OnClickListenner {
        fun clickRemove(wishlistDao: WishlistDao)
        fun notWishlist()
    }
}