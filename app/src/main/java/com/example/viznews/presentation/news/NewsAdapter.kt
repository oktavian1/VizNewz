package com.example.viznews.presentation.news

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.viznews.R
import com.example.viznews.data.model.News
import com.example.viznews.databinding.ItemListNewsBinding

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ListViewHolder>() {

    private var listNews = ArrayList<News>()

    fun setData(newData: List<News>?){
        if (newData == null) return
        listNews.clear()
        listNews.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_news, parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listNews[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listNews.size

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListNewsBinding.bind(itemView)

        fun bind(data: News){
            with(binding){
                tvRating.text = data.rating
                when (data.rating) {
                    "Positif" -> {
                        tvRating.setTextColor(Color.parseColor("#109B2E"))
                    }
                    "Negatif" -> {
                        tvRating.setTextColor(Color.parseColor("#B1040F"))
                    }
                    else -> {
                        tvRating.setTextColor(Color.parseColor("#606060"))
                    }
                }
                tvTitle.text = data.title
                tvDate.text = data.date
                tvDescription.text = data.description
                cvNews.setOnClickListener{
                    Intent(itemView.context, DetailNewsActivity::class.java).apply {
//                        putExtra(DetailNewsActivity.DATA, listNews)
                    }.also(itemView.context::startActivity)
                }
                Glide.with(itemView.context)
                    .load(data.image)
                    .into(imgNews)
            }
        }
    }
}