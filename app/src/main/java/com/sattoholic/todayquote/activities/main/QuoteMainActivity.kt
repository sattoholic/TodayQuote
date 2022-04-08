package com.sattoholic.todayquote.activities.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sattoholic.todayquote.R
import com.sattoholic.todayquote.activities.edit.QuoteEditActivity
import com.sattoholic.todayquote.activities.list.QuoteListActivity
import com.sattoholic.todayquote.databinding.ActivityQuoteMainBinding
import com.sattoholic.todayquote.viewmodels.QuoteMainViewModel
import com.sattoholic.todayquote.viewmodels.QuoteMainViewModelFactory

class QuoteMainActivity : AppCompatActivity() {
    lateinit var binding: ActivityQuoteMainBinding
    lateinit var viewModel: QuoteMainViewModel
    lateinit var editActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quote_main)
        viewModel = ViewModelProvider(this, QuoteMainViewModelFactory(application)).get(QuoteMainViewModel::class.java)

        binding.viewModel = viewModel
        binding.main = this

        viewModel.dataLoaded.observe(this){
            if(it){
                binding.invalidateAll()
            }
        }
        editActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                viewModel.initialize()
                binding.invalidateAll()
            }
        }
    }

    fun toEditActivity(view: View){
        editActivityLauncher.launch(Intent(this, QuoteEditActivity::class.java))
    }
    fun toListActivity(view: View){
        val intent = Intent(this, QuoteListActivity::class.java)
        startActivity(intent)
    }
}