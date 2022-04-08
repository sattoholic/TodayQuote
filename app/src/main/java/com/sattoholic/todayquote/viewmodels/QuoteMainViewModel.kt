package com.sattoholic.todayquote.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sattoholic.todayquote.R
import com.sattoholic.todayquote.database.RoomHelper
import com.sattoholic.todayquote.model.Quote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class QuoteMainViewModel(application: Application): ViewModel() {
    private var quoteList = listOf<Quote>()

    private var _quoteText = ""
    private var _quoteFrom = ""

    val quoteText
        get() = _quoteText
    val quoteFrom
        get() = _quoteFrom

    private var helper: RoomHelper? = null

    init {
        CoroutineScope(Dispatchers.Default).launch {
            withContext(Dispatchers.Main) {
                helper = RoomHelper.getInstance(application.baseContext)
            }

            quoteList = helper?.quoteDao()?.getQuote() ?: listOf()
        }
        if(quoteList.isEmpty()){
            _quoteText = "저장된 명언이 없습니다."
        }
        else{
            val randomIdx = Random().nextInt(quoteList.size)
            val randomQuote = quoteList[randomIdx]
            _quoteText = randomQuote.text
            _quoteFrom = randomQuote.from
        }
    }
}

class QuoteMainViewModelFactory(private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(QuoteMainViewModel::class.java)){
            return QuoteMainViewModel(application) as T
        }
        throw IllegalAccessException(application.applicationContext.getString(R.string.wrong_viewmodel_message))
    }
}