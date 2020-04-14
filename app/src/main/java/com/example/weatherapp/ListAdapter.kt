package com.example.weatherapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class ListAdapter(private val ls_context: Context,
                    private val detailList: ArrayList<HashMap<String, String>>) : BaseAdapter() {

    private val inflater: LayoutInflater = this.ls_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int { return detailList.size }
    override fun getItem(position: Int): Any { return detailList[position] }
    override fun getItemId(position: Int): Long { return position.toLong() }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var detailItem = detailList[position]
        val ls_rowView = inflater.inflate(R.layout.list_detail, parent, false)
        val tv_time=ls_rowView.findViewById<TextView>(R.id.ls_time) as TextView
        val tv_status =ls_rowView.findViewById<TextView>(R.id.ls_status) as TextView
        val tv_temp =ls_rowView.findViewById<TextView>(R.id.ls_temp) as TextView
        val tv_wind =ls_rowView.findViewById<TextView>(R.id.ls_wind) as TextView
        val tv_humidity =ls_rowView.findViewById<TextView>(R.id.ls_humidity) as TextView
        val tv_pressure =ls_rowView.findViewById<TextView>(R.id.ls_pressure) as TextView
        tv_time.text  = detailItem.get("updatedAt")
        tv_status.text= detailItem.get("status")
        tv_temp.text = detailItem.get("temp")
        tv_wind.text =detailItem.get("wind")
        tv_humidity.text =detailItem.get("humidity")
        tv_pressure.text =detailItem.get("pressure")
        ls_rowView.tag = position
        return ls_rowView
    }

}