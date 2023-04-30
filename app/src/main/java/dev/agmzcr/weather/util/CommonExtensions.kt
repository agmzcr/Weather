package dev.agmzcr.weather.util

import android.content.Context
import android.view.View
import android.widget.Toast
import dev.agmzcr.weather.R

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun String.asResourceId(): Int {
    return when (this) {
        "01d" -> R.drawable.ic_01
        "01n" -> R.drawable.ic_01
        "02d" -> R.drawable.ic_02
        "02n" -> R.drawable.ic_02
        "03d" -> R.drawable.ic_03
        "03n" -> R.drawable.ic_03
        "04d" -> R.drawable.ic_04
        "04n" -> R.drawable.ic_04
        "09d" -> R.drawable.ic_09
        "09n" -> R.drawable.ic_09
        "10d" -> R.drawable.ic_10
        "10n" -> R.drawable.ic_10
        "11d" -> R.drawable.ic_11
        "11n" -> R.drawable.ic_11
        "13d" -> R.drawable.ic_13
        "13n" -> R.drawable.ic_13
        "50d" -> R.drawable.ic_50
        "50n" -> R.drawable.ic_50
        else -> R.drawable.ic_01
    }
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.hide() {
    visibility = View.GONE
}