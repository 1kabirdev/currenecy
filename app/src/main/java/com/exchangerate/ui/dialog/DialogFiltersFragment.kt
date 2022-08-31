package com.exchangerate.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.exchangerate.databinding.FragmentFiltrsBinding

class DialogFiltersFragment(
    private var listener: OnClickListernner
) : DialogFragment() {

    private lateinit var binding: FragmentFiltrsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFiltrsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            ascending.setOnClickListener {
                listener.onClickAscending()
                dismiss()
            }

            descending.setOnClickListener {
                listener.onClickDescending()
                dismiss()
            }

            cancel.setOnClickListener { dismiss() }
        }
    }

    interface OnClickListernner {
        fun onClickAscending()
        fun onClickDescending()
    }
}