package com.sattoholic.todayquote.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sattoholic.todayquote.database.RoomHelper
import com.sattoholic.todayquote.model.Quote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuoteListViewModel(): ViewModel() {
    private var currentQuoteSize = 0
    private var _quoteList = mutableListOf<Quote>()
    private var roomHelper: RoomHelper? = null
    private var _dataLoaded = MutableLiveData<Boolean>().apply {
        this.value = false
    }

    val quoteList
        get() = _quoteList.toList()
    val dataLoaded: LiveData<Boolean>
        get() = _dataLoaded

    fun loadQuoteList(context: Context){
        CoroutineScope(Dispatchers.Default).launch {
            withContext(Dispatchers.Main){
                roomHelper = RoomHelper.getInstance(context)
            }

            _quoteList = roomHelper?.quoteDao()?.getQuote()?.toMutableList()!!
            currentQuoteSize = _quoteList.size

            withContext(Dispatchers.Main){
                Toast.makeText(context, "현재 ${currentQuoteSize}개의 명언이 저장되어 있습니다.", Toast.LENGTH_SHORT).show()
                _dataLoaded.value = true
            }
        }
    }
}