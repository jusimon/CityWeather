package com.example.weatherapp
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import org.json.JSONObject
import java.net.URL
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.transform.Result
import android.os.AsyncTask
import android.util.Log
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    var dataList = ArrayList<HashMap<String, String>>()
    lateinit var address:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val edText=findViewById<EditText>(R.id.address)
        val showButton=findViewById<Button>(R.id.button_show)
        showButton.setOnClickListener() {
            val edText=findViewById<EditText>(R.id.address)
            var cityname=edText.text.toString()

            weatherTask().execute(cityname)
        }
       val lsView=findViewById<ListView>(R.id.listView)
       val context= lsView.setOnItemClickListener {parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            val detailintent = Intent(this, DetailActivity::class.java)
            val updateDate=findViewById<TextView>(R.id.updated_at)
            intent.putExtra("updatedate", updateDate.text.toString())
            startActivity(detailintent)
        }
    }
    inner class weatherTask() : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            /* Showing the ProgressBar, Making the main design GONE */
             findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            // findViewById<TextView>(R.id.errorText)?.visibility = View.GONE
        }

        override fun doInBackground(vararg params: String?): String? {
            var response: String?
            val API: String = "a1e255769aa8bcdc3e77af00352330c9"
            val city:String? = params[0]
            try {
                response =
                    URL("https://api.openweathermap.org/data/2.5/forecast?q=$city&units=metric&appid=$API").readText(
                        Charsets.UTF_8

                    )

            } catch (e: Exception) {
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
          //  Log.d("Fetched Data", result)
            findViewById<ProgressBar>(R.id.loader).visibility = View.GONE

            val jsonObj = JSONObject(result)

            //val weatherArr = jsonObj.getJSONArray("city")
            val weatherArr=jsonObj.getJSONArray("list")
            for (i in 0 until 40 step 8) {
                val singleCity = weatherArr.getJSONObject(i)
                val main=singleCity.getJSONObject("main")
                val temp =main.getString("temp")+ "°C"
                val weather = singleCity.getJSONArray("weather")
                val weatherMain = weather.getJSONObject(0).getString("main")
                val updated = singleCity.getString("dt_txt")
                val map = HashMap<String, String>()
               /* map["updatedAt"] = singleCity.getString("dt_txt")
                map["desc"]=weather.getJSONObject(0).getString("main")
               // map["desc"] = weather.getString("main") */
               map["updatedAt"]=updated
               map["desc"] = weatherMain
                map["temp"] = temp

                //map["image"] = singleCity.getString("image")
                dataList.add(map)
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
            } */
            findViewById<ListView>(R.id.listView).adapter = CustomAdapter(this@MainActivity, dataList)
        }
    }
}

