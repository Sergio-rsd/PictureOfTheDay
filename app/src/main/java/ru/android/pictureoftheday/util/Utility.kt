package ru.android.pictureoftheday.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.provider.Settings.System.getString
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import ru.android.pictureoftheday.R
import ru.android.pictureoftheday.R.*
import java.text.SimpleDateFormat
import java.util.*

// Расширяем функционал вью для скрытия клавиатуры
fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
    }
    return false
}

// Расширяем функционал вью для отображения клавиатуры
fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as
            InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

/*

// Пример использования
editTextName.showKeyboard()
buttonSubmit.hideKeyboard()
*/
// Пересоздание fragment
fun recreateFragment(fragment: Fragment, currentActivity: FragmentActivity) {

    currentActivity.supportFragmentManager
        .beginTransaction()
        .detach(fragment)
        .commit()

    currentActivity.supportFragmentManager
        .executePendingTransactions()

    currentActivity.supportFragmentManager
        .beginTransaction()
        .attach(fragment)
        .commitNow()
}
// текущая дата, вчера, позавчера в разных форматах
@SuppressLint("SimpleDateFormat")
fun dateInformation(minusDate: Int): String {
    TimeZone.setDefault(TimeZone.getTimeZone(string.google_time.toString()))
    val currentDate =
        Calendar.getInstance(TimeZone.getTimeZone(string.google_time.toString()))
    currentDate.add(Calendar.DATE, -minusDate)
    val dateResult = currentDate.time
    return SimpleDateFormat("yyyy-MM-dd").format(dateResult)
}
fun dateChoice(minusDate: Int): Date {
    TimeZone.setDefault(TimeZone.getTimeZone(string.google_time.toString()))
    val currentDate = Calendar.getInstance(TimeZone.getTimeZone(string.google_time.toString()))
    currentDate.add(Calendar.DATE, -minusDate)
    return currentDate.time
}

@SuppressLint("SimpleDateFormat")
fun yearDate(minusDate: Int): String {
    return SimpleDateFormat("yyyy").format(dateChoice(minusDate))
}

@SuppressLint("SimpleDateFormat")
fun monthDate(minusDate: Int): String {
    return SimpleDateFormat("MM").format(dateChoice(minusDate))
}

@SuppressLint("SimpleDateFormat")
fun dayDate(minusDate: Int): String {
    return SimpleDateFormat("dd").format(dateChoice(minusDate))
}