package com.example.neurocast

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.io.IOException

class MainActivity : AppCompatActivity(), DateListAdapter.OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.setBackgroundColor(Color.WHITE)
        init()
    }

    private fun init() {
        dateRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = DateListAdapter(
                getList(),
                context,
                this@MainActivity
            )
        }
    }

    private fun getList(): ArrayList<HashMap<String, String>> {
        var list = ArrayList<HashMap<String, String>>()
        var json: String?

        try {
            val inputStream = assets.open("data_points.json")
            json = inputStream.bufferedReader().use { it.readText() }

            for (i in 0 until JSONArray(json).length()) {
                val map = HashMap<String, String>()

                map["date"] = JSONArray(json).getJSONObject(i).getString("date")
                map["index"] = JSONArray(json).getJSONObject(i).getString("index")

                list.add(map)

                circleView.setData(list[0], list[0])
            }

        } catch (e: IOException) {
        }

        return list
    }

    override fun onItemClick(today: HashMap<String, String>, yesterday: HashMap<String, String>) {
        circleView.setData(today, yesterday)
        circleView.animateArc(today["index"]!!.toFloat(), "sweepAngleToday")
        circleView.animateArc(yesterday["index"]!!.toFloat(), "sweepAngleYesterday")
    }
}
