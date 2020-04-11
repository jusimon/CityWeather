package com.example.weatherapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class CustomAdapter(private val context: Context,
                    private val dataList: ArrayList<HashMap<String, String>>) : BaseAdapter() {

    private val inflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int { return dataList.size }
    override fun getItem(position: Int): Any { return dataList[position] }
    override fun getItemId(position: Int): Long { return position.toLong() }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
       var dataItem = dataList[position]
        val rowView = inflater.inflate(R.layout.list_row, parent, false)
        val updatedTextView =rowView.findViewById<TextView>(R.id.updated_at) as TextView
        val descTextView =rowView.findViewById<TextView>(R.id.desc) as TextView
        val tempTextView =rowView.findViewById<TextView>(R.id.temp) as TextView
        updatedTextView.text  = dataItem.get("updatedAt")
        descTextView.text= dataItem.get("desc")
        tempTextView.text = dataItem.get("temp")
       /* rowView.findViewById<TextView>(R.id.updated_at).text = dataItem.get("dt_txt")
        rowView.findViewById<TextView>(R.id.status).text = dataItem.get("main")
        rowView.findViewById<TextView>(R.id.temp).text = dataItem.get("temp") */
        rowView.tag = position
        return rowView
    }
}