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
            R.id.card_transportation -> intentFunc("Transportasi", 1)
            R.id.card_health -> intentFunc("Kesehatan", 2)
            R.id.card_eco -> intentFunc("Ekonomi", 3)
            R.id.card_law -> intentFunc("Hukum",  5)
            R.id.card_education -> intentFunc("Pendidikan", 6)
            R.id.card_tech -> intentFunc("Teknologi", 7)
            R.id.card_other -> intentFunc("Kebudayaan", 4)
            R.id.card_politic -> intentFunc("Politik", 9)
            R.id.card_culture -> intentFunc("Keamanan", 10)
            R.id.card_disaster -> intentFunc("Bencana Alam", 11)
            R.id.card_tourism -> intentFunc("Pariwisata", 8)
        }
    }

    private fun buttonClick() {
        binding.cardDisaster.setOnClickListener(this)
        binding.cardEco.setOnClickListener(this)
        binding.cardLaw.setOnClickListener(this)
        binding.cardEducation.setOnClickListener(this)
        binding.cardHealth.setOnClickListener(this)
        binding.cardTransportation.setOnClickListener(this)
        binding.cardOther.setOnClickListener(this)
        binding.cardPolitic.setOnClickListener(this)
        binding.cardTech.setOnClickListener(this)
        binding.cardCulture.setOnClickListener(this)
        binding.cardTourism.setOnClickListener(this)
    }

    private fun intentFunc(categories: String, idCategory: Int){
        Intent(this, DetailActivity::class.java).apply {
            putExtra(DetailActivity.CATEGORIES, categories)
            putExtra(DetailActivity.ID_CATEGORIES, idCategory)
        }.also(this::startActivity)
    }
}