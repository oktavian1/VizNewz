package com.example.viznews.presentation.news

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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
                when (data.rating) {
                    1 -> {
                        tvRating.text = "Positif"
                        tvRating.setTextColor(Color.parseColor("#109B2E"))
                    }
                    -1 -> {
                        tvRating.text = "Negatif"
                        tvRating.setTextColor(Color.parseColor("#B1040F"))
                    }
                    else -> {
                        tvRating.text = "Netral"
                        tvRating.setTextColor(Color.parseColor("#606060"))
                    }
                }
                tvSource.text = data.source
                tvTitle.text = data.title
                val subString = data.date.substring(0, 17)
                tvDate.text = subString
                tvDescription.text = data.description
                cvNews.setOnClickListener{
                    Intent(itemView.context, DetailNewsActivity::class.java).apply {
                        putExtra(DetailNewsActivity.DATA, data)
                    }.also(itemView.context::startActivity)
                }

                if(data.image != ""){
                    Glide.with(itemView.context)
                            .load(data.image)
                            .apply(RequestOptions().override(600, 600))
                            .placeholder(R.drawable.image)
                            .into(imgNews)
                }else{
                    Glide.with(itemView.context)
                            .load(R.drawable.image)
                            .apply(RequestOptions().override(600, 600))
                            .placeholder(R.drawable.image)
                            .into(imgNews)
                }

            }
        }
    }
}