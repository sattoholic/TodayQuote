package com.sattoholic.todayquote.activities.edit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sattoholic.todayquote.R
import com.sattoholic.todayquote.databinding.ActivityQuoteEditBinding
import com.sattoholic.todayquote.viewmodels.QuoteEditViewModelFactory
import com.sattoholic.todayquote.viewmodels.QuoteEditViewModels


class QuoteEditActivity : AppCompatActivity() {
    lateinit var binding: ActivityQuoteEditBinding
    lateinit var viewModel: QuoteEditViewModels

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quote_edit)

        viewModel = QuoteEditViewModelFactory(application).create(QuoteEditViewModels::class.java)

        val adapter = QuoteEditAdapter()

        Log.d("EDIT 엑티비티 디버그", viewModel.editQuoteList.toString())

        adapter.updateList(viewModel.editQuoteList)
        binding.quoteEditList.layoutManager = LinearLayoutManager(this)
        binding.quoteEditList.adapter = adapter
    }
}