package dev.agmzcr.weather.util

import android.annotation.SuppressLint
import android.widget.ImageView
import com.bumptech.glide.Glide
import dev.agmzcr.weather.util.Constants.DATE_FORMAT_1
import java.text.SimpleDateFormat
import java.util.*

object AppUtils {

    fun setGlideImage(image: ImageView, url: String) {

        Glide.with(image).load(url)
            .thumbnail(0.5f)
            .into(image)
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDateTime(dateFormat: String): String =
        SimpleDateFormat(dateFormat).format(Date())

    @SuppressLint("SimpleDateFormat")
    fun isTimeExpired(dateTimeSavedWeather: String?): Boolean {
        dateTimeSavedWeather?.let {
            val currentDateTime = Date()
            val savedWeatherDateTime =
                SimpleDateFormat(DATE_FORMAT_1).parse(it)
            val diff: Long = currentDateTime.time - savedWeatherDateTime.time
            val seconds = diff / 1000
            val minutes = seconds / 60
            if (minutes > 10)
                return true
        }
        return false
    }

    @SuppressLint("SimpleDateFormat")
    fun fromTimestap(value: Long): String {
        val date = Date(value*1000)
        val format = SimpleDateFormat("EEE, d MMM yyyy HH:mm")
        return format.format(date)
    }

    fun fromTimestapToDay(value: Long): String {
        val data = Date(value*1000)
        val format = SimpleDateFormat("EEE, d MMM HH:mm")
        return format.format(data)
    }

    fun fromTimestapToDate(value: Long): String {
        val data = Date(value*1000)
        val format = SimpleDateFormat("d MMM")
        return format.format(data)
    }
}