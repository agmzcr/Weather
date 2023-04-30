package dev.agmzcr.weather.util

import androidx.lifecycle.Observer

class EventObserver<T>(private val onEventUnconsumedContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(value: Event<T>) {
        value?.consume()?.run(onEventUnconsumedContent)
    }
}