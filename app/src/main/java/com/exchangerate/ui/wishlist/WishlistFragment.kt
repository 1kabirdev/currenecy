package com.exchangerate.ui.wishlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.exchangerate.databinding.FragmentWishlistBinding
import com.exchangerate.model.WishlistDao
import com.exchangerate.ui.dialog.DialogFiltersFragment
import com.exchangerate.utils.WishlistDeleteState
import com.exchangerate.utils.WishlistState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishlistFragment : Fragment(), AdapterWishlist.OnClickListenner,
    DialogFiltersFragment.OnClickListernner {

    private val wishlistViewModel: WishlistViewModel by viewModels()
    private lateinit var binding: FragmentWishlistBinding
    private lateinit var adapterWishlist: AdapterWishlist

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wishlistViewModel.getLoadWishlist()
        lifecycleScope.launchWhenStarted {
            wishlistViewModel.wishlistStateFlow.collect { it ->
                when (it) {
                    is WishlistState.Success -> {
                        initAdapterWishlist(it.wishlistDao)
                    }
                    is WishlistState.Empty -> {

                    }
                }
            }
        }

        adapterWishlist = AdapterWishlist(this@WishlistFragment)
        binding.recyclerViewWishlist.adapter = adapterWishlist

        binding.filters.setOnClickListener {
            val fm: FragmentManager = requireActivity().supportFragmentManager
            val filtrsFragment = DialogFiltersFragment(this)
            filtrsFragment.show(fm, "fragment_filtrs")
        }
    }

    private fun initAdapterWishlist(list: ArrayList<WishlistDao>) {
        if (list.size != 0) {
            adapterWishlist.addItemList(list)
            binding.linearNotWishlist.visibility = View.GONE
        } else {
            binding.linearNotWishlist.visibility = View.VISIBLE
        }
    }

    override fun clickRemove(wishlistDao: WishlistDao) {
        lifecycleScope.launchWhenStarted {
            wishlistViewModel.deleteWishlist(wishlistDao)
            wishlistViewModel.wishlistDeleteStateFlow.collect { it ->
                when (it) {
                    is WishlistDeleteState.Success -> {
                        Toast.makeText(
                            requireContext(),
                            "${wishlistDao.currency} ???????????? ???? ??????????????????????",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is WishlistDeleteState.Empty -> {

                    }
                }
            }
        }
    }

    override fun notWishlist() {
        binding.linearNotWishlist.visibility = View.VISIBLE
    }

    override fun onClickAscending() {
        adapterWishlist.sortListPriceAscending()
    }

    override fun onClickDescending() {
        adapterWishlist.sortListPriceDescending()
    }
}