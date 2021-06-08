package com.example.viznews.presentation.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viznews.R
import com.example.viznews.databinding.ActivityNewsBinding
import com.example.viznews.presentation.Factory
import com.example.viznews.utils.DataMapper
import com.example.viznews.utils.Resource

class NewsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityNewsBinding
    private lateinit var viewModel: NewsViewModel

    companion object{
        const val TIME = "TIME"
        const val ID_CATEGORIES = "1"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val time = intent.getIntExtra(TIME, 1)
        val idCategory = intent.getIntExtra(ID_CATEGORIES, 1)

        val factory = Factory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[NewsViewModel::class.java]

        val ratingTextAdapter = NewsAdapter()

        viewModel.getNews(time, idCategory).observe(this, {
            when(it){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    val newsMapper = it.data?.let { it1 -> DataMapper.mapNewsResponseToNews(it1) }
                    ratingTextAdapter.setData(newsMapper)
                    with(binding.rvNews){
                        layoutManager = LinearLayoutManager(context)
                        setHasFixedSize(true)
                        adapter = ratingTextAdapter
                    }
                }
                is Resource.Error -> {}
            }
        })

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_back -> onBackPressed()
        }
    }
}