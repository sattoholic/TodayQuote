package com.sattoholic.todayquote.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sattoholic.todayquote.R
import com.sattoholic.todayquote.database.RoomHelper
import com.sattoholic.todayquote.model.Quote
import kotlinx.coroutines.*

class QuoteEditViewModels(application: Application): ViewModel() {
    private var roomHelper: RoomHelper? = null

    val editQuoteList
        get()=_editQuoteList
    val dataLoaded
        get()=_dataLoaded

    private var _editQuoteList = mutableListOf<Quote>()
    private var quoteList = listOf<Quote>()
    private var _dataLoaded = false


    init {
        CoroutineScope(Dispatchers.Main).launch {
            roomHelper = RoomHelper.getInstance(application.baseContext)

            withContext(Dispatchers.IO){
                var tempList = async { roomHelper?.quoteDao()?.getQuote() ?: listOf() }
                quoteList = tempList.await()
                Log.d("tempList 디버그", tempList.await().toString())
                Log.d("quoteList 디버그", quoteList.toString())
            }

            launch {
                for(i in 0 until 20){
                    editQuoteList.add(Quote(i, ""))
                }
            }.join()

            Log.d("EDIT 디버그", quoteList.isNotEmpty().toString())
            Log.d("EDIT 디버그", quoteList.toString())

            if (quoteList.isNotEmpty()) {
                Log.d("EDIT 디버그", "quoteList.isNotEmpty() 맞아?")
                for(q in quoteList){
                    editQuoteList[q.idx!!].idx = q.idx
                    editQuoteList[q.idx!!].text = q.text
                    editQuoteList[q.idx!!].from = q.from
                }
            }

            _dataLoaded = true
            Log.d("EDIT 뷰모델 디버그", "dataLoaded : ${_dataLoaded}")
        }
    }
}

class QuoteEditViewModelFactory(private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(QuoteEditViewModels::class.java)){
            QuoteEditViewModels(application) as T
        }
        else{
            throw IllegalAccessException(application.applicationContext.getString(R.string.wrong_viewmodel_message))
        }
    }
}