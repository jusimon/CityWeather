package com.example.weatherapp

import android.os.AsyncTask
import android.view.View
import android.widget.ProgressBar
import java.net.URL

abstract class MyAsyncTask(): AsyncTask<String, Void, String>() {

    override fun doInBackground(vararg params: String?): String? {
        var response: String?
        val API: String = "a1e255769aa8bcdc3e77af00352330c9"
        val city: String? = params[0]
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
}