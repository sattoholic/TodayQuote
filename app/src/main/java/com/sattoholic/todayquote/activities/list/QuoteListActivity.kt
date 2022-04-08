package com.sattoholic.todayquote.activities.list

import android.os.Bundle
import android.widget.Toast
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
        Toast.makeText(this, "현재 ${viewModel.currentQuoteSize}개의 명언이 저장되어 있습니다.", Toast.LENGTH_SHORT).show()

        val adapter = QuoteListAdapter()
        adapter.updateList(viewModel.quoteList)

        binding.quoteList.adapter = adapter
        binding.quoteList.layoutManager = LinearLayoutManager(this)
    }
}