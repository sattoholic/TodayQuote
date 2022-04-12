package com.sattoholic.todayquote.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sattoholic.todayquote.database.RoomHelper
import com.sattoholic.todayquote.model.Quote
import kotlinx.coroutines.*
import java.util.*

class QuoteMainViewModel(): ViewModel() {
    private var quoteList = listOf<Quote>()

    private var _quoteText = ""
    private var _quoteFrom = ""
    private var _dataLoaded = MutableLiveData<Boolean>().apply {
        this.value = false
    }

    val quoteText
        get() = _quoteText
    val quoteFrom
        get() = _quoteFrom
    val dataLoaded: LiveData<Boolean>
        get() = _dataLoaded

    private var helper: RoomHelper? = null

    fun loadHelper(context: Context){
        CoroutineScope(Dispatchers.Default).launch {
            withContext(Dispatchers.Main) {
                helper = RoomHelper.getInstance(context)
            }
        }
    }


    fun initialize(){
        CoroutineScope(Dispatchers.Default).launch{
            quoteList = async {
                helper?.quoteDao()?.getQuote() ?: listOf()
            }.await()

            if(quoteList.isEmpty()){
                _quoteText = "저장된 명언이 없습니다."
                _quoteFrom = ""
            }
            else{
                val randomIdx = Random().nextInt(quoteList.size)
                val randomQuote = quoteList[randomIdx]
                _quoteText = randomQuote.text
                _quoteFrom = randomQuote.from
            }
            withContext(Dispatchers.Main){
                _dataLoaded.value = true
            }
        }
    }
}