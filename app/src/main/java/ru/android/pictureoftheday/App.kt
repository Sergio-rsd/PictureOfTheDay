package ru.android.pictureoftheday

import android.app.Application

class App : Application() {
//    private val TAG = "MyLogin ${this::class.java.simpleName} : ${this.hashCode()}"

    override fun onCreate() {
        super.onCreate()
        appInstance = this
//        TAG = appInstance.TAG
        TAG = "Happy ${this::class.java.simpleName} : ${this.hashCode()}"
    }

    companion object {
        private var appInstance: App? = null

        //        private val TAG = "MyLogin ${this::class.java.simpleName} : ${this.hashCode()}"
//        val TAG = "MyLogin ${this::class.java.simpleName} : ${this.hashCode()}"
        var TAG: String = ""
    }
}