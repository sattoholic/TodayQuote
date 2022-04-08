package com.sattoholic.todayquote.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sattoholic.todayquote.R
import com.sattoholic.todayquote.database.RoomHelper
import com.sattoholic.todayquote.model.Quote
import kotlinx.coroutines.*

class QuoteEditViewModel(application: Application): ViewModel() {
    private var roomHelper: RoomHelper? = null

    val editQuoteList
        get()=_editQuoteList
    val dataLoaded
        get()=_dataLoaded

    private var _editQuoteList = mutableListOf<Quote>()
    private var quoteList = listOf<Quote>()
    private var _dataLoaded = MutableLiveData<Boolean>().apply {
        this.value = false
    }


    init {
        CoroutineScope(Dispatchers.Main).launch {
            roomHelper = RoomHelper.getInstance(application.baseContext)

            withContext(Dispatchers.IO){
                var tempList = async { roomHelper?.quoteDao()?.getQuote() ?: listOf() }
                quoteList = tempList.await()
            }

            launch {
                for(i in 0 until 20){
                    editQuoteList.add(Quote(i, ""))
                }
            }.join()

            if (quoteList.isNotEmpty()) {
                for(q in quoteList){
                    editQuoteList[q.idx!!].idx = q.idx
                    editQuoteList[q.idx!!].text = q.text
                    editQuoteList[q.idx!!].from = q.from
                }
            }
            _dataLoaded.value = true
        }
    }
}

class QuoteEditViewModelFactory(private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(QuoteEditViewModel::class.java)){
            QuoteEditViewModel(application) as T
        }
        else{
            throw IllegalAccessException(application.applicationContext.getString(R.string.wrong_viewmodel_message))
        }
    }
}