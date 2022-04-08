package com.sattoholic.todayquote.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sattoholic.todayquote.R
import com.sattoholic.todayquote.database.RoomHelper
import com.sattoholic.todayquote.model.Quote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuoteListViewModel(application: Application): ViewModel() {
    private var _currentQuoteSize = 0
    private var _quoteList = mutableListOf<Quote>()

    val currentQuoteSize
        get() = _currentQuoteSize
    val quoteList
        get() = _quoteList.toList()

    private var roomHelper: RoomHelper? = null

    init {
        CoroutineScope(Dispatchers.Default).launch {
            withContext(Dispatchers.Main){
                roomHelper = RoomHelper.getInstance(application.baseContext)
            }

            _quoteList = roomHelper?.quoteDao()?.getQuote()?.toMutableList()!!
            _currentQuoteSize = _quoteList.size
        }
    }
}

class QuoteListViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(QuoteListViewModel::class.java)){
            return QuoteListViewModel(application) as T
        }
        throw IllegalAccessException(application.applicationContext.getString(R.string.wrong_viewmodel_message))
    }
}