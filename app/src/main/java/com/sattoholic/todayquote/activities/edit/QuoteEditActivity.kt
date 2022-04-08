package com.sattoholic.todayquote.activities.edit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sattoholic.todayquote.R
import com.sattoholic.todayquote.databinding.ActivityQuoteEditBinding
import com.sattoholic.todayquote.viewmodels.QuoteEditViewModel
import com.sattoholic.todayquote.viewmodels.QuoteEditViewModelFactory


class QuoteEditActivity : AppCompatActivity() {
    lateinit var binding: ActivityQuoteEditBinding
    lateinit var viewModel: QuoteEditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quote_edit)

        viewModel = ViewModelProvider(this, QuoteEditViewModelFactory(application)).get(QuoteEditViewModel::class.java)

        val adapter = QuoteEditAdapter()

        viewModel.dataLoaded.observe(this){
            if(it){
                adapter.updateList(viewModel.editQuoteList)
            }
        }

        binding.quoteEditList.layoutManager = LinearLayoutManager(this)
        binding.quoteEditList.adapter = adapter
    }

    override fun onBackPressed() {
        setResult(RESULT_OK)
        super.onBackPressed()
    }

}