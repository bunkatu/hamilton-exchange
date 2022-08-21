package com.hamiltonch.hamiltonexchange.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.hamiltonch.hamiltonexchange.R
import com.hamiltonch.hamiltonexchange.databinding.CurrencySelectRowBinding
import javax.inject.Inject

class CurrencyRecyclerAdapter @Inject constructor() :
    RecyclerView.Adapter<CurrencyRecyclerAdapter.CurrencyViewHolder>(), CurrencyClickListener {
    private var onItemClickListener: ((String)-> Unit) ? = null

    var currencyList : ArrayList<String> = arrayListOf()


    class CurrencyViewHolder(var view: CurrencySelectRowBinding): RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater  = LayoutInflater.from(parent.context)

        val view = DataBindingUtil.inflate<CurrencySelectRowBinding>(inflater, R.layout.currency_select_row,parent,false)

        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.view.currency = currencyList[position]
        holder.view.clickListener = this

    }

    override fun getItemCount(): Int {
        return currencyList.size
    }


    fun setCurrencyList(newCurrencyList: List<String>){
        currencyList.clear()
        currencyList.addAll(newCurrencyList)
        notifyDataSetChanged()
    }

    override fun onCurrencyClicked(view: View, currency: String) {
        onItemClickListener?.let {
            it(currency)
        }
    }


    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }


}