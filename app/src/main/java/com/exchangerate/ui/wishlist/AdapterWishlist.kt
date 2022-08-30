package com.exchangerate.ui.wishlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exchangerate.databinding.ItemListWishlistBinding
import com.exchangerate.model.WishlistDao

class AdapterWishlist(
    private var listener: OnClickListenner,
    private var list: ArrayList<WishlistDao>
) : RecyclerView.Adapter<AdapterWishlist.ViewHolder>() {

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
            list.remove(wishlistDao)
            notifyDataSetChanged()
            if (list.size == 0) {
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
        holder.bindView(list[position])

    override fun getItemCount(): Int = list.size

    interface OnClickListenner {
        fun clickRemove(wishlistDao: WishlistDao)
        fun notWishlist()
    }
}