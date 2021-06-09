package com.example.viznews.presentation.dashboard

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.example.viznews.R
import com.example.viznews.data.model.OverallSentiment
import com.example.viznews.data.model.TextRating
import com.example.viznews.data.network.response.DataOverallChart
import com.example.viznews.databinding.ActivityDetailBinding
import com.example.viznews.presentation.Factory
import com.example.viznews.presentation.news.NewsActivity
import com.example.viznews.utils.DataMapper
import com.example.viznews.utils.Resource
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class DetailActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var pie: PieChart
    private lateinit var mChart: BarChart
    private lateinit var viewModel: DetailViewModel

    companion object{
        const val CATEGORIES = "categories"
        const val ID_CATEGORIES = "1"
    }

    private lateinit var binding: ActivityDetailBinding
    private var time: Int? = 1
    private var idCategoryGlobal: Int? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pie = findViewById(R.id.pie_chart)
        mChart = findViewById(R.id.bar_chart)

        val category = intent.getStringExtra(CATEGORIES)
        val idCategory = intent.getIntExtra(ID_CATEGORIES, 1)
        idCategoryGlobal =  idCategory

        val factory = Factory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
//        time?.let { viewModel.queryOverallSentiment(it, idCategory) }

//        if (category == "Overall"){
//            viewModel.queryOverallSentiment(1, state = 1)
//            binding.bar.root.visibility = View.VISIBLE
//            binding.btnGoToNews.visibility = View.GONE
////            binding.layout3.root.visibility = View.GONE
//
//            observeData()
//        }else{
//        }

        val spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_item,
                android.R.layout.simple_spinner_item
        )
        binding.spinner.adapter = spinnerAdapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                when (parent?.selectedItem) {
                    "1 Hari" -> {
                        time = 1
                        if (category == "Overall") {
                            viewModel.queryOverallSentiment(1, state = 1)
                            binding.bar.root.visibility = View.VISIBLE
                            binding.btnGoToNews.visibility = View.GONE
                        }else{
                            viewModel.queryOverallSentiment(1, idCategory, 0)
                            binding.bar.root.visibility = View.GONE
                        }
                    }
                    "7 Hari" -> {
                        time = 7
                        if (category == "Overall") {
                            Log.d("ASS", "masuk 2 if")
                            viewModel.queryOverallSentiment(7, state = 1)
                            binding.bar.root.visibility = View.VISIBLE
                            binding.btnGoToNews.visibility = View.GONE
                        }else{
                            viewModel.queryOverallSentiment(7, idCategory, 0)
                            binding.bar.root.visibility = View.VISIBLE
                        }
                    }
                    else -> {
                        time = 30
                        if (category == "Overall") {
                            viewModel.queryOverallSentiment(30, state = 1)
                            binding.bar.root.visibility = View.VISIBLE
                            binding.btnGoToNews.visibility = View.GONE
                        }else{
                            viewModel.queryOverallSentiment(30, idCategory, 0)
                            binding.bar.root.visibility = View.VISIBLE
                        }
                    }
                }
                observeData()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                time?.let { viewModel.queryOverallSentiment(it, idCategory, 0) }
            }
        }

        buttonClick()



        binding.tvCategories.text = category

        setupPieChart()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeData(param: Int? = null) {
        viewModel.getOverallSentiment().observe(this@DetailActivity, {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    it.data?.let { it1 -> loadDataPie(it1) }
                }
                is Resource.Error -> {
                }
            }
        })

        viewModel.getOverallChart().observe(this@DetailActivity, {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    it.data?.let { it1 -> groupBarChart(it1) }
                }
                is Resource.Error -> {
                }
            }
        })

        viewModel.getWords().observe(this@DetailActivity, {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    val mapperPositive = it.data?.positive?.let { it1 ->
                        DataMapper.mapToPositiveTextRating(
                                it1
                        )
                    }
                    val mapperNegative = it.data?.negative?.let { it1 ->
                        DataMapper.mapToNegativeTextRating(
                                it1
                        )
                    }
                    setupAdapterRatingText(0, mapperPositive as ArrayList<TextRating>)
                    setupAdapterRatingText(1, mapperNegative as ArrayList<TextRating>)
                }
                else -> {
                }
            }
        })
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

    private fun setupPieChart(){
        with(pie){
            isDrawHoleEnabled = true
            setUsePercentValues(true)
            setEntryLabelTextSize(12f)
            setEntryLabelColor(Color.BLACK)
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

    private fun loadDataPie(data: OverallSentiment){
        val pieEntry: ArrayList<PieEntry> = ArrayList()
        with(pieEntry){
            add(PieEntry(data.positive.toFloat(), "Positif"))
            add(PieEntry(data.neutral.toFloat(), "Netral"))
            add(PieEntry(data.negative.toFloat(), "Negatif"))
        }

        val dataColor: ArrayList<Int> = ArrayList()
        for (i in ColorTemplate.MATERIAL_COLORS){
            dataColor.add(i)
        }
        for (i in ColorTemplate.VORDIPLOM_COLORS){
            dataColor.add(i)
        }

        val dataSet = PieDataSet(pieEntry, "OVERALL")
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun minusDayHelper(day: Long): String{
        val date = LocalDateTime.now().minusDays(day)
        val formatter = DateTimeFormatter.ofPattern("MMMdd")
        return date.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun groupBarChart(dataParam: List<DataOverallChart>){
        with(mChart){
            findViewById<View>(R.id.bar_chart) as BarChart
            setDrawBarShadow(false)
            description.isEnabled = false
            setPinchZoom(false)
            setDrawGridBackground(true)
        }
        // empty labels so that the names are spread evenly
        // empty labels so that the names are spread evenly
        Log.d("dataParam", dataParam.toString())
        val labels: ArrayList<String> = arrayListOf()
        for (i in dataParam.indices){
            labels.add(minusDayHelper(i.toLong()))
        }
        labels.add(0,"")
        labels.add(labels.size, "")
//        val labels = arrayOf("", "Name1", "Name2", "Name3", "Name4", "Name5", "")
        val xAxis: XAxis = mChart.xAxis
        with(xAxis){
            setCenterAxisLabels(true)
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(true)
            granularity = 1f // only intervals of 1 day

            textColor = Color.BLACK
            textSize = 9f
            axisLineColor = Color.WHITE
            axisMinimum = 1f
            valueFormatter = IndexAxisValueFormatter(labels)
        }

        val leftAxis: YAxis = mChart.axisLeft
        with(leftAxis){
            textColor = Color.BLACK
            textSize = 12f
            axisLineColor = Color.WHITE
            setDrawGridLines(true)
            granularity = 2f
            setLabelCount(8, true)
            setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        }

        mChart.axisRight.isEnabled = false
        mChart.legend.isEnabled = false

        val dataPositive: ArrayList<Float> = arrayListOf()
        val dataNegative: ArrayList<Float> = arrayListOf()
        val dataNeutral: ArrayList<Float> = arrayListOf()
        for (i in dataParam.indices){
            dataPositive.add(dataParam[i].positive.toFloat())
            dataNegative.add(dataParam[i].negative.toFloat())
            dataNeutral.add(dataParam[i].neutral.toFloat())
        }

        val barOne: ArrayList<BarEntry> = ArrayList()
        val barTwo: ArrayList<BarEntry> = ArrayList()
        val barThree: ArrayList<BarEntry> = ArrayList()
        for (i in dataPositive.indices) {
            barOne.add(BarEntry(i.toFloat(), dataPositive[i]))
            barTwo.add(BarEntry(i.toFloat(), dataNegative[i]))
            barThree.add(BarEntry(i.toFloat(), dataNeutral[i]))
        }

        val set1 = BarDataSet(barOne, "barOne")
        set1.color = Color.GREEN
        val set2 = BarDataSet(barTwo, "barTwo")
        set2.color = Color.RED
        val set3 = BarDataSet(barThree, "barTwo")
        set3.color = Color.YELLOW

        set1.isHighlightEnabled = false
        set2.isHighlightEnabled = false
        set3.isHighlightEnabled = false
        set1.setDrawValues(false)
        set2.setDrawValues(false)
        set3.setDrawValues(false)

        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(set1)
        dataSets.add(set2)
        dataSets.add(set3)
        val data = BarData(dataSets)
        val groupSpace = 0.4f
        val barSpace = 0f
        val barWidth = 0.2f
        // (barSpace + barWidth) * 2 + groupSpace = 1
        // (barSpace + barWidth) * 2 + groupSpace = 1
        data.barWidth = barWidth
        // so that the entire chart is shown when scrolled from right to left
        // so that the entire chart is shown when scrolled from right to left
        xAxis.axisMaximum = labels.size - 1.1f

        mChart.data = data
        mChart.setScaleEnabled(false)
        mChart.setVisibleXRangeMaximum(6f)
        mChart.groupBars(1f, groupSpace, barSpace)
        mChart.invalidate()
    }



    class CustomDataEntry constructor(x: String?, value: Number?, value2: Number?, value3: Number?) : ValueDataEntry(
        x,
        value
    ) {
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
                    putExtra(NewsActivity.TIME, time)
                    putExtra(NewsActivity.ID_CATEGORIES, idCategoryGlobal)
                }.also(this::startActivity)
            }
        }
    }
}