package com.sattoholic.todayquote.activities.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sattoholic.todayquote.R
import com.sattoholic.todayquote.databinding.ActivityQuoteListBinding
import com.sattoholic.todayquote.viewmodels.QuoteListViewModel
import com.sattoholic.todayquote.viewmodels.QuoteListViewModelFactory

class QuoteListActivity : AppCompatActivity() {
    lateinit var binding: ActivityQuoteListBinding
    lateinit var viewModel: QuoteListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quote_list)

        viewModel = QuoteListViewModelFactory(application).create(QuoteListViewModel::class.java)


        val adapter = QuoteListAdapter()
        adapter.updateList(viewModel.quoteList)
        viewModel.dataLoaded.observe(this){
            if(it){
                adapter.updateList(viewModel.quoteList)
            }
        }

        binding.quoteList.adapter = adapter
        binding.quoteList.layoutManager = LinearLayoutManager(this)
    }
}