package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.text.SimpleDateFormat


class DetailActivity : AppCompatActivity() {

    val searchDate = intent.getStringExtra("updatedate")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val searchDate = intent.getStringExtra("updatedate")
        val cityname = intent.getStringExtra("cityname")

        DetailTask().execute(cityname, searchDate)
    }

    inner class DetailTask() : MyAsyncTask() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            //  Log.d("Fetched Data", result)
            //findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
            val jsonObj = JSONObject(result)

            //val weatherArr = jsonObj.getJSONArray("city")
            val weatherArr = jsonObj.getJSONArray("list")

            for (i in 0 until 40 ) {
                val singleCity = weatherArr.getJSONObject(i)
                val updated = singleCity.getString("dt_txt")

                if (updated == searchDate) {

                    val main = singleCity.getJSONObject("main")
                    val temp = main.getString("temp") + "°C"
                    val weather = singleCity.getJSONArray("weather")
                    val weatherMain = weather.getJSONObject(0).getString("main")

                    val sys = jsonObj.getJSONObject("sys")
                    val address = jsonObj.getString("name") + ", " + sys.getString("country")
                    val wind = jsonObj.getJSONObject("wind")
                    // Date format
                    val updated = singleCity.getString("dt_txt")
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US)
                    val date = LocalDate.parse(updated, formatter)
                    val mformatter = DateTimeFormatter.ofPattern("MMM dd")
                    val formatted = date.format(mformatter)

                    val tempMin = "Min Temp: " + main.getString("temp_min") + "°C"
                    val tempMax = "Max Temp: " + main.getString("temp_max") + "°C"
                    val pressure = main.getString("pressure")
                    val humidity = main.getString("humidity")
                    val sunrise: Long = sys.getLong("sunrise")
                    val sunset: Long = sys.getLong("sunset")
                    val windSpeed = wind.getString("speed")
                    val weatherDescription = weather.getString(0)

                    findViewById<TextView>(R.id.address).text = address
                    findViewById<TextView>(R.id.updated_at).text = formatted
                    findViewById<TextView>(R.id.status).text = weatherDescription
                    findViewById<TextView>(R.id.temp).text = temp
                    findViewById<TextView>(R.id.temp_min).text = tempMin
                    findViewById<TextView>(R.id.temp_max).text = tempMax

                    findViewById<TextView>(R.id.sunrise).text =
                        SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise * 1000))
                    findViewById<TextView>(R.id.sunset).text =
                        SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset * 1000))
                    findViewById<TextView>(R.id.wind).text = windSpeed
                    findViewById<TextView>(R.id.pressure).text = pressure
                    findViewById<TextView>(R.id.humidity).text = humidity
                    break
                }

    /* val map = HashMap<String, String>()
     /* map["updatedAt"] = singleCity.getString("dt_txt")
      map["desc"]=weather.getJSONObject(0).getString("main")
     // map["desc"] = weather.getString("main") */
     map["updatedAt"] = formatted
     map["desc"] = weatherMain
     map["temp"] = temp
     // dataList.add(map) */

}
/*  try {
/* Extracting JSON returns from the API */

val jsonObj = JSONObject(result)
val main = jsonObj.getJSONObject("main")
val sys = jsonObj.getJSONObject("sys")
val wind = jsonObj.getJSONObject("wind")
val weather = jsonObj.getJSONArray("weather").getJSONObject(0)

val updatedAt: Long = jsonObj.getLong("dt")
val updatedAtText =
    "Updated at: " + SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
        Date(updatedAt * 1000)
    )
val temp = main.getString("temp") + "°C"
val tempMin = "Min Temp: " + main.getString("temp_min") + "°C"
val tempMax = "Max Temp: " + main.getString("temp_max") + "°C"
val pressure = main.getString("pressure")
val humidity = main.getString("humidity")

val sunrise: Long = sys.getLong("sunrise")
val sunset: Long = sys.getLong("sunset")
val windSpeed = wind.getString("speed")
val weatherDescription = weather.getString("description")

val address = jsonObj.getString("name") + ", " + sys.getString("country")

/* Populating extracted data into our views */
findViewById<TextView>(R.id.address).text = address
findViewById<TextView>(R.id.updated_at).text = updatedAtText
findViewById<TextView>(R.id.status).text = weatherDescription
findViewById<TextView>(R.id.temp).text = temp
findViewById<TextView>(R.id.temp_min).text = tempMin
findViewById<TextView>(R.id.temp_max).text = tempMax
/* findViewById<TextView>(R.id.sunrise).text =
        SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise * 1000))
findViewById<TextView>(R.id.sunset).text =
        SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset * 1000))
findViewById<TextView>(R.id.wind).text = windSpeed
findViewById<TextView>(R.id.pressure).text = pressure
findViewById<TextView>(R.id.humidity).text = humidity */

/* Views populated, Hiding the loader, Showing the main design */
// findViewById<ProgressBar>(R.id.loader).visibility = View.GONE

} catch (e: Exception) {
// findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
// findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE
}
findViewById<ListView>(R.id.listView).adapter = CustomAdapter(this@MainActivity, dataList) */

}
}
}

