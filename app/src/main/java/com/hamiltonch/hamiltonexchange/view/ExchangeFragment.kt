package com.hamiltonch.hamiltonexchange.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hamiltonch.hamiltonexchange.R
import com.hamiltonch.hamiltonexchange.databinding.FragmentExchangeBinding
import com.hamiltonch.hamiltonexchange.viewmodel.ExchangeViewModel
import com.hamiltonch.hamiltonexchange.viewmodel.SelectCurrencyViewModel

class ExchangeFragment : Fragment() {
    private lateinit var binding : FragmentExchangeBinding
    lateinit var viewModel : ExchangeViewModel

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

            val builder = AlertDialog.Builder(context)
            builder.setMessage(R.string.convert_msg)
                .setCancelable(false)
                .setPositiveButton(R.string.convert_yes) { dialog, id ->
                    findNavController().navigate(ExchangeFragmentDirections.actionExchangeFragmentToResultFragment())
                }
                .setNegativeButton(R.string.convert_no) { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ExchangeViewModel::class.java)

        arguments?.let {
            viewModel.setConversion(ExchangeFragmentArgs.fromBundle(it).conversion)
            viewModel.startTimer()
        }

        observeLiveData()

    }


    private fun observeLiveData() {
        viewModel.conversion.observe(viewLifecycleOwner, Observer { conversion ->
            conversion?.let {
                binding.conversion = it
            }
        })
        viewModel.duration.observe(viewLifecycleOwner, Observer { duration ->
            duration?.let {
                binding.duration = it.toString()
            }
        })
        viewModel.expired.observe(viewLifecycleOwner, Observer { expired ->
            expired?.let {
                if (expired){
                    Toast.makeText(context, R.string.expiry_message, Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        })

    }

    override fun onStop() {
        super.onStop()
        viewModel.clearExpired()
    }

}