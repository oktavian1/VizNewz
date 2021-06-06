package com.example.viznews.utils

import com.example.viznews.data.model.TextRating
import com.github.mikephil.charting.data.PieEntry

object DataDummy {

    private val dataPositiveOneDay = arrayOf(
        "Selamat", "Korban"
    )
    private val dataNegativeOneDay = arrayOf(
        "Meninggal", "Meletus", "Tsunami", "Bom"
    )

    private val dataPositiveSevenDay = arrayOf(
        "Selamat", "Korban", ""
    )
    private val dataNegativeSevenDay = arrayOf(
        "Meninggal", "Meletus", "Tsunami", "Bom"
    )

    fun generateDataPositiveOneDay(): ArrayList<TextRating>{
        val data = ArrayList<TextRating>()
        with(data){
            add(TextRating(0, "Selamatasasasasasasas", 82))
            add(TextRating(1, "Evakuasi", 7))
        }
        return data
    }

    fun generateDataNegativeOneDay(): ArrayList<TextRating>{
        val data = ArrayList<TextRating>()
        with(data){
            add(TextRating(0, "Meninggal", 8))
            add(TextRating(1, "Hancur", 7))
        }
        return data
    }

    fun generateDataPositiveSevenDay(): ArrayList<TextRating>{
        val data = ArrayList<TextRating>()
        with(data){
            add(TextRating(0, "Selamat", 9))
            add(TextRating(1, "Evakuasi", 7))
            add(TextRating(2, "Tim Sar", 7))
            add(TextRating(3, "Tim Sar", 7))
        }
        return data
    }

    fun generateDataNegativeOneMonth(): ArrayList<TextRating>{
        val data = ArrayList<TextRating>()
        with(data){
            add(TextRating(0, "Meninggal", 8))
            add(TextRating(1, "Hancur", 7))
            add(TextRating(2, "Meninggal", 8))
            add(TextRating(3, "Hancur", 7))
            add(TextRating(4, "Hancur", 7))
            add(TextRating(5, "Hancur", 7))
        }
        return data
    }

    fun generateDataPositiveOneMonth(): ArrayList<TextRating>{
        val data = ArrayList<TextRating>()
        with(data){
            add(TextRating(0, "Selamat", 9))
            add(TextRating(1, "Evakuasi", 7))
            add(TextRating(2, "Tim Sar", 7))
            add(TextRating(3, "Tim Sar", 7))
            add(TextRating(4, "Tim Sar", 7))
            add(TextRating(5, "Tim Sar", 7))
        }
        return data
    }

    fun generateDataNegativeSevenDay(): ArrayList<TextRating>{
        val data = ArrayList<TextRating>()
        with(data){
            add(TextRating(0, "Meninggal", 8))
            add(TextRating(1, "Hancur", 7))
            add(TextRating(0, "Meninggal", 8))
            add(TextRating(1, "Hancur", 7))
        }
        return data
    }

    fun generateDataPieOneDay(): ArrayList<PieEntry>{
        val data = ArrayList<PieEntry>()

        data.add(PieEntry(0.2f, "Positif"))
        data.add(PieEntry(0.5f, "Negatif"))
        data.add(PieEntry(0.3f, "Netral"))

        return data
    }

    fun generateDataPieSevenDay(): ArrayList<PieEntry>{
        val data = ArrayList<PieEntry>()

        data.add(PieEntry(0.7f, "Positif"))
        data.add(PieEntry(0.1f, "Negatif"))
        data.add(PieEntry(0.2f, "Netral"))

        return data
    }

    fun generateDataPieOneMonth(): ArrayList<PieEntry>{
        val data = ArrayList<PieEntry>()

        data.add(PieEntry(0.2f, "Positif"))
        data.add(PieEntry(0.6f, "Negatif"))
        data.add(PieEntry(0.2f, "Netral"))

        return data
    }

}