package com.exchangerate.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.exchangerate.databinding.FragmentPopularBinding
import com.exchangerate.model.WishlistDao
import com.exchangerate.ui.dialog.DialogFiltersFragment
import com.exchangerate.utils.AddWishlistPopularState
import com.exchangerate.utils.Constants
import com.exchangerate.utils.PopularState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularFragment : Fragment(), AdapterPopular.OnClickListener,
    DialogFiltersFragment.OnClickListernner {

    private val mainViewModel: PopularViewModel by viewModels()
    private lateinit var binding: FragmentPopularBinding
    private lateinit var adapterPopular: AdapterPopular

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.getLoadRateList(Constants.APIKEY, "USD")
        lifecycleScope.launchWhenStarted {
            mainViewModel.popularStateFlow.collect { it ->
                when (it) {
                    is PopularState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.linearConnection.visibility = View.GONE
                    }
                    is PopularState.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        binding.linearConnection.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), "${it.msg}", Toast.LENGTH_SHORT).show()
                    }
                    is PopularState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.linearConnection.visibility = View.GONE
                        initAdapter(it.data.getMapRates())
                    }
                    is PopularState.Empty -> {

                    }
                }
            }
        }

        adapterPopular = AdapterPopular(this)
        binding.recyclerView.adapter = adapterPopular

        binding.btnClickReply.setOnClickListener {
            mainViewModel.getLoadRateList(Constants.APIKEY, "USD")
        }

        binding.filters.setOnClickListener {
            val fm: FragmentManager = requireActivity().supportFragmentManager
            val filtrsFragment = DialogFiltersFragment(this)
            filtrsFragment.show(fm, "fragment_filtrs")
        }
    }

    private fun initAdapter(rates: ArrayList<Pair<String, String>>) {
        adapterPopular.addItem(rates)
    }

    override fun addWishlist(name: String, currency: String) {
        val wishlistDao = WishlistDao(0, name, currency.toDouble())
        mainViewModel.addWishlistCurrency(wishlistDao)
        lifecycleScope.launchWhenStarted {
            mainViewModel.popularAddWishlistStateFlow.collect { it ->
                when (it) {
                    is AddWishlistPopularState.Success -> {
                        Toast.makeText(
                            requireContext(),
                            "$name добавлен в избранное",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    is AddWishlistPopularState.Empty -> {

                    }
                }
            }
        }
    }

    override fun onClickAscending() {
        adapterPopular.sortListPriceAscending()
    }

    override fun onClickDescending() {
        adapterPopular.sortListPriceDescending()
    }
}