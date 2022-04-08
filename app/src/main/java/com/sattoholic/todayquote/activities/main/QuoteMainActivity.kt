package com.sattoholic.todayquote.activities.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sattoholic.todayquote.R
import com.sattoholic.todayquote.activities.edit.QuoteEditActivity
import com.sattoholic.todayquote.activities.list.QuoteListActivity
import com.sattoholic.todayquote.databinding.ActivityQuoteMainBinding
import com.sattoholic.todayquote.viewmodels.QuoteMainViewModel
import com.sattoholic.todayquote.viewmodels.QuoteMainViewModelFactory

class QuoteMainActivity : AppCompatActivity() {
    lateinit var binding: ActivityQuoteMainBinding
    lateinit var viewModel: QuoteMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quote_main)
        viewModel = QuoteMainViewModelFactory(application).create(QuoteMainViewModel::class.java)
        binding.viewModel = viewModel
        binding.main = this
    }

    fun toEditActivity(view: View){
        val intent = Intent(this, QuoteEditActivity::class.java)
        startActivity(intent)
    }
    fun toListActivity(view: View){
        val intent = Intent(this, QuoteListActivity::class.java)
        startActivity(intent)
    }
}