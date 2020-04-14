package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class ListActivity : AppCompatActivity() {
    var detailList = ArrayList<HashMap<String, String>>()
    lateinit var searchDate: String
    lateinit var tv_place: String
    lateinit var dt_cityname: String
   // lateinit var tv_sunset: String
   // lateinit var tv_sunrise:String

    //= intent.getStringExtra("updatedate")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        searchDate = intent.getStringExtra("updatedate").toString()
        dt_cityname = intent.getStringExtra("cityname").toString()

        ListTask().execute(dt_cityname, searchDate)


        val lsView=findViewById<ListView>(R.id.ls_listView)

        val context= lsView.setOnItemClickListener {parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as HashMap<String, String>
            val detailintent = Intent(this, DetailActivity::class.java)
            //val detailintent = Intent(this,ListActivity::class.java)
            val updateDate=selectedItem.get("updatedAt")
            //findViewById<TextView>(R.id.updated_at).text.toString()
            detailintent.putExtra("updatedtime", updateDate.toString())
            detailintent.putExtra("updatedate", searchDate)
            // detailintent.putExtra("updatedate", updateDate)
            detailintent.putExtra("cityname", dt_cityname)//findViewById<EditText>(R.id.address).text.toString())

            startActivity(detailintent)
        }

    }

    inner class ListTask() : MyAsyncTask() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)

            val jsonObj = JSONObject(result)

            //val weatherArr = jsonObj.getJSONArray("city")
            val weatherArr = jsonObj.getJSONArray("list")

            for (i in 0 until 40) {
                val singleCity = weatherArr.getJSONObject(i)
                val updated = singleCity.getString("dt_txt")
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US)
                val date = LocalDate.parse(updated, formatter)
                val mformatter = DateTimeFormatter.ofPattern("MMM dd")
                val formatted = date.format(mformatter)
                val updatedTime = updated.split(" ");


                if (formatted == searchDate) {

                    val main = singleCity.getJSONObject("main")
                    val temp = "Temp: " + main.getString("temp") + "°C"
                    val weather = singleCity.getJSONArray("weather")
                    // val weatherDescription = weather.getString(0)
                    val weatherdesc = weather.getJSONObject(0).getString("description")
                    // val tempMin = "Min Temp: " + main.getString("temp_min") + "°C"
                    // val tempMax = "Max Temp: " + main.getString("temp_max") + "°C"
                    val pressure = "Pressure: " + main.getString("pressure")
                    val humidity = "Humidity: " + main.getString("humidity")

                    val winddetails = singleCity.getJSONObject("wind")
                    val windSpeed = "Wind: "+winddetails.getString("speed")

                    val cityinfo = jsonObj.getJSONObject("city")
                    val placename = cityinfo.getString("name") + ", " + cityinfo.getString("country")
                    val sunrise: Long = cityinfo.getLong("sunrise")
                    val sunset: Long = cityinfo.getLong("sunset")

                    val map = HashMap<String, String>()
                    map["updatedAt"] = updatedTime[1]
                    map["desc"] = weatherdesc
                    map["temp"] = temp
                    map["wind"] = windSpeed
                    map["humidity"] = humidity
                    map["pressure"] = pressure
                    detailList.add(map)
                    tv_place = placename

                  /* textview_place  = findViewById<TextView>(R.id.ls_place)
                   textview_place.text = placename
                  tv_date = findViewById<TextView>(R.id.ls_date)
                    tv_date.text = formatted.toString()
                    findViewById<TextView>(R.id.dt_status).text = weatherdesc
                    findViewById<TextView>(R.id.dt_temp).text = temp
                   findViewById<TextView>(R.id.dt_temp_min).text = tempMin
                  //  findViewById<TextView>(R.id.dt_temp_max).text = tempMax

                    findViewById<TextView>(R.id.dt_sunrise).text =
                        SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise * 1000))
                    findViewById<TextView>(R.id.dt_sunset).text =
                        SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset * 1000))
                    findViewById<TextView>(R.id.dt_wind).text = windSpeed
                    findViewById<TextView>(R.id.dt_pressure).text = pressure
                    findViewById<TextView>(R.id.dt_humidity).text = humidity */

                }
            }
            findViewById<TextView>(R.id.ls_place).text =tv_place
            findViewById<TextView>(R.id.ls_date).text =searchDate
            findViewById<ListView>(R.id.ls_listView).adapter = ListAdapter(this@ListActivity, detailList)

        }
    }
}