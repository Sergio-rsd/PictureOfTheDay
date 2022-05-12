package ru.android.pictureoftheday.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

// Расширяем функционал вью для скрытия клавиатуры
fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) { }
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