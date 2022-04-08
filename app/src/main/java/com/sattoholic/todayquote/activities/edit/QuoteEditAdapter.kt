package com.sattoholic.todayquote.activities.edit

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sattoholic.todayquote.R
import com.sattoholic.todayquote.database.RoomHelper
import com.sattoholic.todayquote.databinding.QuoteEditLilstItemBinding
import com.sattoholic.todayquote.model.Quote
import kotlinx.coroutines.*

class QuoteEditAdapter: RecyclerView.Adapter<QuoteEditAdapter.QuoteEditViewHolder>() {
    inner class QuoteEditViewHolder(private val binding: QuoteEditLilstItemBinding, private val context: Context, private val quoteList: MutableList<Quote>): RecyclerView.ViewHolder(binding.root){
        private var roomHelper: RoomHelper? = null

        init {
            CoroutineScope(Dispatchers.Default).launch {
                roomHelper = RoomHelper.getInstance(this@QuoteEditViewHolder.context)
            }
            binding.viewHolder = this

            Log.d("뷰홀더 디버그", quoteList.toString())
        }

        fun setQuote(quote: Quote){
            binding.quote = quote
            if(quote.text.isBlank()){
                setButtonQuoteNotExist()
            }
        }

        private fun setButtonQuoteNotExist(){
            binding.quoteDeleteBtn.visibility = View.GONE
            binding.quoteModifyBtn.visibility = View.GONE
            binding.quoteAddBtn.visibility = View.VISIBLE
        }

        private fun setButtonQuoteExist(){
            binding.quoteAddBtn.visibility = View.GONE
            binding.quoteDeleteBtn.visibility = View.VISIBLE
            binding.quoteModifyBtn.visibility = View.VISIBLE
        }

        fun addQuote(view: View){
            val position = adapterPosition
            CoroutineScope(Dispatchers.IO).launch {
                val newQuote = async {
                    Quote(position, binding.quoteTextEdit.text.toString(), binding.quoteFromEdit.text.toString())
                }

                launch {
                    roomHelper?.quoteDao()?.insertQuote(newQuote.await())
                    quoteList[position] = newQuote.await()
                }.join()

                withContext(Dispatchers.Main){
                    Log.d("INSERT 디버그", quoteList.toString())
                    notifyDataSetChanged()
                    setButtonQuoteExist()
                }
                withContext(Dispatchers.Default){
                    Log.d("INSERT DB 디버그", roomHelper?.quoteDao()?.getQuote()?.toString()!!)
                }
            }

            Toast.makeText(context, context.getString(R.string.quote_add), Toast.LENGTH_SHORT).show()

        }

        fun modifyQuote(view: View){
            CoroutineScope(Dispatchers.IO).launch {
                val modifiedQuote = async {
                    Quote(text = binding.quoteTextEdit.text.toString(), from = binding.quoteFromEdit.text.toString())
                }
                roomHelper?.quoteDao()?.updateQuote(modifiedQuote.await())

                quoteList[adapterPosition] = modifiedQuote.await()

                withContext(Dispatchers.Main){
                    notifyDataSetChanged()
                }
            }
            Toast.makeText(context, context.getString(R.string.quote_modify), Toast.LENGTH_SHORT).show()
        }

        fun deleteQuote(view: View){
            CoroutineScope(Dispatchers.IO).launch {
                roomHelper?.quoteDao()?.deleteQuote(quoteList[adapterPosition])

                quoteList[adapterPosition] = Quote(adapterPosition, text = "", from = "")

                withContext(Dispatchers.Main){
                    notifyDataSetChanged()
                    setButtonQuoteNotExist()
                }

            }
            Toast.makeText(context, context.getString(R.string.quote_delete), Toast.LENGTH_SHORT).show()
        }
    }
    private val quoteList = mutableListOf<Quote>()

    fun updateList(quoteList: List<Quote>){
        this.quoteList.clear()
        this.quoteList.addAll(quoteList)
        Log.d("디버그", "quoteList Size : ${quoteList.size}")
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuoteEditAdapter.QuoteEditViewHolder {
        val binding = QuoteEditLilstItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return QuoteEditViewHolder(binding, parent.context, quoteList)
    }

    override fun onBindViewHolder(holder: QuoteEditAdapter.QuoteEditViewHolder, position: Int) {
        holder.setQuote(quoteList[position])
    }

    override fun getItemCount(): Int = quoteList.size
}