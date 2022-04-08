package com.sattoholic.todayquote.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sattoholic.todayquote.R
import com.sattoholic.todayquote.database.RoomHelper
import com.sattoholic.todayquote.model.Quote
import kotlinx.coroutines.*
import java.util.*

class QuoteMainViewModel(application: Application): ViewModel() {
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

    init {
        CoroutineScope(Dispatchers.Default).launch {
            withContext(Dispatchers.Main) {
                helper = RoomHelper.getInstance(application.baseContext)
            }

            initialize()
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

class QuoteMainViewModelFactory(private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(QuoteMainViewModel::class.java)){
            return QuoteMainViewModel(application) as T
        }
        throw IllegalAccessException(application.applicationContext.getString(R.string.wrong_viewmodel_message))
    }
}