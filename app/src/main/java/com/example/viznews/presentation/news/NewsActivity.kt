package com.example.viznews.presentation.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viznews.R
import com.example.viznews.data.model.News
import com.example.viznews.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ratingTextAdapter = NewsAdapter()
        ratingTextAdapter.setData(loadDataNews())
        with(binding.rvNews){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = ratingTextAdapter
        }
    }


    private fun loadDataNews(): List<News>{
        val arr: ArrayList<News> = ArrayList()
        arr.add(News(1, "Detik.com", "3 hari lalu",
            R.drawable.image, "Naruto lagi berantem dengan siapa", "lorem ipsum dolor ametlorem ipsum dolor ametlorem ipsum dolor ametlorem ipsum dolor ametlorem ipsum dolor ametlorem ipsum dolor ametlorem ipsum dolor amet", "Negatif"))
        arr.add(News(2, "Detik.com", "1 bulan lalu", R.drawable.image, "Sasuke lagi chidori", "lorem ipsum dolor ametlorem ipsum dolor ametlorem ipsum dolor ametlorem ipsum dolor ametlorem ipsum dolor ametlorem ipsum dolor ametlorem ipsum dolor amet", "Negatif"))
        arr.add(News(3, "Detik.com", "8 hari lalu",
            R.drawable.image, "Corona di jakarta kambuh lagi", "lorem ipsum dolor ametlorem ipsum dolor ametlorem ipsum dolor ametlorem ipsum dolor ametlorem ipsum dolor ametlorem ipsum dolor ametlorem ipsum dolor amet", "Positif"))
        arr.add(News(4, "Detik.com", "7 hari lalu",
            R.drawable.image, "Corona di wuhan sudah membaik", "lorem ipsum dolor ametlorem ipsum dolor ametlorem ipsum dolor ametlorem ipsum dolor ametlorem ipsum dolor ametlorem ipsum dolor ametlorem ipsum dolor amet", "Netral"))
        return arr
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_back -> onBackPressed()
        }
    }
}