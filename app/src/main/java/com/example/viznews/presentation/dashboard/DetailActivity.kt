package com.example.viznews.presentation.dashboard

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.data.Set
import com.example.viznews.R
import com.example.viznews.data.model.TextRating
import com.example.viznews.databinding.ActivityDetailBinding
import com.example.viznews.presentation.news.NewsActivity
import com.example.viznews.utils.DataDummy
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.anychart.core.cartesian.series.Line
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import com.anychart.enums.TooltipPositionMode
import com.anychart.graphics.vector.Stroke



class DetailActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var pie: PieChart

    companion object{
        const val CATEGORIES = "categories"
    }

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonClick()

        pie = findViewById(R.id.pie_chart)

        val category = intent.getStringExtra(CATEGORIES)
        binding.tvCategories.text = category

        dataLine()

        val spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_item,
                android.R.layout.simple_spinner_item
        )
        binding.spinner.adapter = spinnerAdapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                if(parent?.selectedItem == "1 Day"){
                    loadDataPie(DataDummy.generateDataPieOneDay())
                    setupAdapterRatingText(0, DataDummy.generateDataPositiveOneDay())
                    setupAdapterRatingText(1, DataDummy.generateDataNegativeOneDay())
                    binding.bar.root.visibility = View.GONE
                }else if (parent?.selectedItem == "7 Day"){
                    loadDataPie(DataDummy.generateDataPieSevenDay())
                    setupAdapterRatingText(0, DataDummy.generateDataPositiveSevenDay())
                    setupAdapterRatingText(1, DataDummy.generateDataNegativeSevenDay())
                    binding.bar.root.visibility = View.VISIBLE
                }else{
                    loadDataPie(DataDummy.generateDataPieOneMonth())
                    setupAdapterRatingText(0, DataDummy.generateDataPositiveOneMonth())
                    setupAdapterRatingText(1, DataDummy.generateDataNegativeOneMonth())
                    binding.bar.root.visibility = View.VISIBLE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        setupPieChart()

    }

    private fun buttonClick() {
        binding.btnBack.setOnClickListener(this)
        binding.btnGoToNews.setOnClickListener(this)
    }

    private fun setupAdapterRatingText(state: Int, data: ArrayList<TextRating>){

        val ratingTextAdapter = RatingTextAdapter()
        if (state == 0){
            ratingTextAdapter.setData(data)
            with(binding.layout3.rvPositive){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = ratingTextAdapter
            }
        }else {
            ratingTextAdapter.setData(data)
            with(binding.layout3.rvNegative){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = ratingTextAdapter
            }
        }

    }

    private fun loadDataPositiveRating(data: ArrayList<TextRating>): List<TextRating>{
        val data: ArrayList<TextRating> = ArrayList()
        data.add(TextRating(0, "Covid", 8))
        data.add(TextRating(1, "Mudik", 7))
        data.add(TextRating(2, "Ujian", 6))
        data.add(TextRating(3, "UTBK", 5))
        return data
    }

    private fun loadDataNegativeRating(): List<TextRating>{
        val data: ArrayList<TextRating> = ArrayList()
        data.add(TextRating(0, "Covid", 8))
        data.add(TextRating(1, "Mudik", 7))
        data.add(TextRating(2, "Ujian", 6))
        data.add(TextRating(3, "UTBK", 5))
        data.add(TextRating(4, "JAMBU", 4))
        return data
    }


    private fun setupPieChart(){
        with(pie){
            isDrawHoleEnabled = true
            setUsePercentValues(true)
            setEntryLabelTextSize(12f)
            setEntryLabelColor(Color.BLACK)
            setCenterText("asasas")
            setCenterTextColor(24)
            pie.description.isEnabled = true

            val l: Legend = pie.legend
            with(l){
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                horizontalAlignment= Legend.LegendHorizontalAlignment.RIGHT
                orientation = Legend.LegendOrientation.VERTICAL
                setDrawInside(false)
                isEnabled = true
            }
        }
    }

    private fun loadDataPie(data: ArrayList<PieEntry>){
//        val data: ArrayList<PieEntry> = ArrayList()
//        data.add(PieEntry(0.2f, "Positive"))
//        data.add(PieEntry(0.5f, "Negative"))
//        data.add(PieEntry(0.3f, "Summary"))

        val dataColor: ArrayList<Int> = ArrayList()
        for (i in ColorTemplate.MATERIAL_COLORS){
            dataColor.add(i)
        }
        for (i in ColorTemplate.VORDIPLOM_COLORS){
            dataColor.add(i)
        }

        val dataSet: PieDataSet = PieDataSet(data, "OVERALL")
        dataSet.colors = dataColor

        val pieData = PieData(dataSet)
        with(pieData){
            setDrawValues(true)
            setValueFormatter(PercentFormatter(pie))
            setValueTextSize(12f)
            setValueTextColor(Color.BLACK)
        }

        pie.data = pieData
        pie.invalidate()
        pie.animateY(1400, Easing.EaseInOutQuad)

    }

    private fun dataLine(){
        val cartesian = AnyChart.line()

        cartesian.animation(true)

        cartesian.padding(10.0, 20.0, 5.0, 20.0)

        cartesian.crosshair().enabled(true)
        cartesian.crosshair()
                .yLabel(true) // TODO ystroke
                .yStroke(null as Stroke?, null, null, null as String?, null as String?)

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)

        cartesian.title("Trend of Lorem ipsum lorem dolor.")

        cartesian.yAxis(0).title("Number of Word")
        cartesian.xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)
        cartesian.xAxis(0).title("Tanggal")

        val seriesData: MutableList<DataEntry> = ArrayList()
        seriesData.add(CustomDataEntry("12 Jan", 3.6, 2.3, 2.8))
        seriesData.add(CustomDataEntry("13 Jan", 7.1, 4.0, 4.1))
        seriesData.add(CustomDataEntry("14 Jan", 8.5, 6.2, 5.1))
        seriesData.add(CustomDataEntry("15 Jan", 9.2, 11.8, 6.5))
        seriesData.add(CustomDataEntry("16 Jan", 10.1, 13.0, 12.5))
        seriesData.add(CustomDataEntry("17 Jan", 11.6, 13.9, 18.0))
        seriesData.add(CustomDataEntry("18 Jan", 16.4, 18.0, 21.0))
        seriesData.add(CustomDataEntry("19 Jan", 18.0, 23.3, 20.3))

        val set = Set.instantiate()
        set.data(seriesData)
        val series1Mapping = set.mapAs("{ x: 'x', value: 'value' }")
        val series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }")
        val series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }")

        val series1: Line = cartesian.line(series1Mapping)
        series1.name("Positif")
        series1.hovered().markers().enabled(true)
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4.0)
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5.0)
                .offsetY(5.0)

        val series2: Line = cartesian.line(series2Mapping)
        series2.name("Negatif")
        series2.hovered().markers().enabled(true)
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4.0)
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5.0)
                .offsetY(5.0)

        val series3: Line = cartesian.line(series3Mapping)
        series3.name("Netral")
        series3.hovered().markers().enabled(true)
        series3.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4.0)
        series3.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5.0)
                .offsetY(5.0)

        cartesian.legend().enabled(true)
        cartesian.legend().fontSize(13.0)
        cartesian.legend().padding(0.0, 0.0, 10.0, 0.0)
        binding.bar.barChart.setChart(cartesian)
    }

    private class CustomDataEntry constructor(x: String?, value: Number?, value2: Number?, value3: Number?) : ValueDataEntry(x, value) {
        init {
            setValue("value2", value2)
            setValue("value3", value3)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_back -> onBackPressed()
            R.id.btn_go_to_news -> {
                Intent(this, NewsActivity::class.java).apply {
                }.also(this::startActivity)
            }
        }
    }
}