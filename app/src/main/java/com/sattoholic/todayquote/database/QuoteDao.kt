package com.sattoholic.todayquote.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.sattoholic.todayquote.model.Quote

@Dao
interface QuoteDao {
    @Insert (onConflict = REPLACE)
    fun insertQuote(quote: Quote)

    @Update
    fun updateQuote(quote: Quote)

    @Delete
    fun deleteQuote(quote: Quote)

    @Query("SELECT * FROM quote")
    fun getQuote():List<Quote>
}