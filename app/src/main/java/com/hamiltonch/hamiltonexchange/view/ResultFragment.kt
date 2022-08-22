package com.hamiltonch.hamiltonexchange.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.hamiltonch.hamiltonexchange.R
import com.hamiltonch.hamiltonexchange.databinding.FragmentResultBinding
import com.hamiltonch.hamiltonexchange.viewmodel.ResultViewModel

class ResultFragment : Fragment() {
    private lateinit var binding : FragmentResultBinding
    private lateinit var viewModel : ResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false)
        binding.listener = View.OnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

}