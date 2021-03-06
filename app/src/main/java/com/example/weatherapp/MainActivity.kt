package com.example.weatherapp
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import org.json.JSONObject
import java.util.*
import android.widget.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    var dataList = ArrayList<HashMap<String, String>>()
    lateinit var address:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        weatherTask().execute("San Jose")
        val showButton=findViewById<Button>(R.id.button_show)
        showButton.setOnClickListener() {
            val edText=findViewById<EditText>(R.id.address)
            val cityname=edText.text.toString()

            weatherTask().execute(cityname)
        }

        val lsView=findViewById<ListView>(R.id.listViewDetail)

            val context= lsView.setOnItemClickListener {parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as HashMap<String, String>
            //val detailintent = Intent(this, DetailActivity::class.java)
                val detailintent = Intent(this,ListActivity::class.java)
                val updateDate=selectedItem.get("updatedAt")
                    //findViewById<TextView>(R.id.updated_at).text.toString()
                detailintent.putExtra("updatedate", updateDate.toString())
               // detailintent.putExtra("updatedate", updateDate)
              detailintent.putExtra("cityname", findViewById<EditText>(R.id.address).text.toString())

            startActivity(detailintent)
        }
    }
    //inner class weatherTask() : MyAsyncTask<String, Void, String>() {
    inner class weatherTask() : MyAsyncTask() {
        override fun onPreExecute() {
            super.onPreExecute()
            /* Showing the ProgressBar, Making the main design GONE */
             findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            // findViewById<TextView>(R.id.errorText)?.visibility = View.GONE
        }

        /*override fun doInBackground(vararg params: String?): String? {
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
        } */

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
          //  Log.d("Fetched Data", result)
            findViewById<ProgressBar>(R.id.loader).visibility = View.GONE

            val jsonObj = JSONObject(result)

            //val weatherArr = jsonObj.getJSONArray("city")
            val weatherArr=jsonObj.getJSONArray("list")
            dataList.clear()
            for (i in 0 until 40 step 8) {
                val singleCity = weatherArr.getJSONObject(i)
                val main=singleCity.getJSONObject("main")
                val temp =main.getString("temp")+ "°C"
                val weather = singleCity.getJSONArray("weather")
                val weatherMain = weather.getJSONObject(0).getString("main")
                val updated = singleCity.getString("dt_txt")
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US)
                val date = LocalDate.parse(updated, formatter)
                val mformatter = DateTimeFormatter.ofPattern("MMM dd")
                val formatted = date.format(mformatter)

                val map = HashMap<String, String>()
               /* map["updatedAt"] = singleCity.getString("dt_txt")
                map["desc"]=weather.getJSONObject(0).getString("main")
               // map["desc"] = weather.getString("main") */
                map["updatedAt"]=formatted
                map["desc"] = weatherMain
                map["temp"] = temp
                dataList.add(map)

            }

            findViewById<ListView>(R.id.listViewDetail).adapter = CustomAdapter(this@MainActivity, dataList)

        }
    }
}

