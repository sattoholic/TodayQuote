package com.sattoholic.todayquote.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sattoholic.todayquote.model.Quote

@Database(entities = [Quote::class], exportSchema = false, version = 1)
abstract class RoomHelper: RoomDatabase() {
    abstract fun quoteDao(): QuoteDao

    companion object{
        private var INSTANCE: RoomHelper? = null

        fun getInstance(context: Context): RoomHelper{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.applicationContext, RoomHelper::class.java, "quote.db").build()
            }
            return INSTANCE!!
        }
    }
}