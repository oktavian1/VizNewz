package com.example.viznews.presentation.dashboard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.viznews.R
import com.example.viznews.data.model.TextRating
import com.example.viznews.databinding.ItemListRatingTextBinding

class RatingTextAdapter: RecyclerView.Adapter<RatingTextAdapter.ListViewHolder>() {

    private var listRating = ArrayList<TextRating>()

    fun setData(newData: List<TextRating>?){
        if (newData == null) return
        listRating.clear()
        listRating.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_rating_text, parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listRating[position]
        holder.bind(data, position+1)
    }

    override fun getItemCount(): Int = listRating.size

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListRatingTextBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bind(data: TextRating, position: Int){
            with(binding){
                tvRating.text = data.rating.toString()
                tvText.text = "${position}. ${data.text}"
            }
        }
    }
}