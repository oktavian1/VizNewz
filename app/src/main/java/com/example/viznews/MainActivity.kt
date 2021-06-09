package com.example.viznews

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.viznews.databinding.ActivityMainBinding
import com.example.viznews.presentation.dashboard.DetailActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        buttonClick()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.card_transportation -> intentFunc("Transportasi", 0)
            R.id.card_health -> intentFunc("Kesehatan", 1)
            R.id.card_eco -> intentFunc("Ekonomi", 2)
            R.id.card_culture -> intentFunc("Kebudayaan", 3)
            R.id.card_education -> intentFunc("Pendidikan", 4)
            R.id.card_politic -> intentFunc("Politik", 5)
            R.id.card_law -> intentFunc("Hukum",  6)
            R.id.card_disaster -> intentFunc("Bencana Alam", 7)
            R.id.card_other -> intentFunc("Lain Lain", 8)
            R.id.card_overall -> intentFunc("Overall")
        }
    }

    private fun buttonClick() {
        binding.cardTransportation.setOnClickListener(this)
        binding.cardHealth.setOnClickListener(this)
        binding.cardEco.setOnClickListener(this)
        binding.cardCulture.setOnClickListener(this)
        binding.cardEducation.setOnClickListener(this)
        binding.cardPolitic.setOnClickListener(this)
        binding.cardLaw.setOnClickListener(this)
        binding.cardDisaster.setOnClickListener(this)
        binding.cardOther.setOnClickListener(this)
        binding.cardOverall.setOnClickListener(this)
    }

    private fun intentFunc(categories: String, idCategory: Int? = null){
        Intent(this, DetailActivity::class.java).apply {
            putExtra(DetailActivity.CATEGORIES, categories)
            putExtra(DetailActivity.ID_CATEGORIES, idCategory)
        }.also(this::startActivity)
    }
}