package com.sattoholic.todayquote.activities.list

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sattoholic.todayquote.databinding.QuoteListItemBinding
import com.sattoholic.todayquote.model.Quote

class QuoteListAdapter: RecyclerView.Adapter<QuoteListAdapter.QuoteListViewHolder>() {
    class QuoteListViewHolder(private val binding: QuoteListItemBinding, private val context: Context): RecyclerView.ViewHolder(binding.root){

        fun setQuote(quote: Quote){
            binding.viewHolder = this

            if(quote.from.isBlank()){
                binding.quoteFromSearchBtn.visibility = View.GONE
            }

            binding.quote = quote
        }

        fun shareQuote(view: View){
            val intent = Intent(Intent.ACTION_SEND)

            intent.putExtra(Intent.EXTRA_TITLE, "힘이 되는 명언")
            intent.putExtra(Intent.EXTRA_SUBJECT, "힘이 되는 명언")
            intent.putExtra(Intent.EXTRA_TEXT, "${binding.quote?.text}\n출처 : ${binding.quote?.from}")
            intent.type = "text/plain"

            val chooser = Intent.createChooser(intent, "명언 공유")

            context.startActivity(chooser)
        }

        fun searchQuote(view: View){
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=${binding.quote?.from}"))

            context.startActivity(intent)
        }
    }
    private var quoteList = mutableListOf<Quote>()

    fun updateList(quoteList: List<Quote>){
        this.quoteList.clear()
        this.quoteList.addAll(quoteList)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteListViewHolder {
        val binding = QuoteListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return QuoteListViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: QuoteListViewHolder, position: Int) {
        holder.setQuote(quoteList[position])
    }

    override fun getItemCount(): Int = quoteList.size
}