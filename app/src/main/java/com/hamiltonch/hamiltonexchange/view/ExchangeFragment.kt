package com.hamiltonch.hamiltonexchange.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.hamiltonch.hamiltonexchange.R
import com.hamiltonch.hamiltonexchange.databinding.FragmentExchangeBinding

class ExchangeFragment : Fragment() {
    private lateinit var binding : FragmentExchangeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_exchange, container, false)
        binding.listener = View.OnClickListener {
            findNavController().navigate(ExchangeFragmentDirections.actionExchangeFragmentToResultFragment())
        }

        return binding.root
    }

}