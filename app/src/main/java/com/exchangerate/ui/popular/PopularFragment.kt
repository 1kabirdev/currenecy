package com.exchangerate.ui.popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.exchangerate.databinding.FragmentPopularBinding
import com.exchangerate.model.WishlistDao
import com.exchangerate.room.AppDao
import com.exchangerate.utils.AddWishlistPopularState
import com.exchangerate.utils.PopularState
import com.exchangerate.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class PopularFragment : Fragment(), AdapterPopular.OnClickListener {

    private val mainViewModel: PopularViewModel by viewModels()
    private lateinit var binding: FragmentPopularBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.getLoadRateList(Constants.APIKEY)
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

        binding.btnClickReply.setOnClickListener {
            mainViewModel.getLoadRateList(Constants.APIKEY)
        }
    }

    private fun initAdapter(rates: ArrayList<Pair<String, String>>) {
        val adapterMain = AdapterPopular(rates, this)
        binding.recyclerView.adapter = adapterMain
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
}