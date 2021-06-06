package com.example.viznews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
            R.id.card_disaster -> intentFunc("Bencana Alam")
            R.id.card_eco -> intentFunc("Ekonomi")
            R.id.card_law -> intentFunc("Hukum & Keamanan")
            R.id.card_culture -> intentFunc("Kebudayaan & Pariwisata")
            R.id.card_health -> intentFunc("Kesehatan")
            R.id.card_education -> intentFunc("Pendidikan")
            R.id.card_politic -> intentFunc("Politik")
            R.id.card_tech -> intentFunc("Teknologi")
            R.id.card_transportation -> intentFunc("Transportasi")
            R.id.card_other -> intentFunc("lain-lain")
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
    }

    private fun intentFunc(categories: String){
        Intent(this, DetailActivity::class.java).apply {
            putExtra(DetailActivity.CATEGORIES, categories)
        }.also(this::startActivity)
    }
}