package ru.android.pictureoftheday

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.android.pictureoftheday.ui.EarthFragment
import ru.android.pictureoftheday.ui.MainFragment
import ru.android.pictureoftheday.ui.SettingThemeFragment

class MainActivity : AppCompatActivity(R.layout.main_activity) {
//class MainActivity : AppCompatActivity() {

/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        val menuBottom : BottomNavigationMenuView = findViewById(R.id.bottom_app_bar)
        menuBottom.setOnCl
        menuBottom.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.setting -> {
                    requireActivity().supportFragmentManager.apply {
                        beginTransaction()
                            .replace(R.id.container, SettingThemeFragment.newInstance())
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                    }
//                    recreateFragment(this@MainFragment, requireActivity())
                    true
                }
                R.id.action -> {
//                    Toast.makeText(context, "Action", Toast.LENGTH_LONG).show()
                    requireActivity().supportFragmentManager.apply {
                        beginTransaction()
                            .replace(R.id.container, EarthFragment())
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                    }
                    true
                }
                else -> true
            }
        }
    }
    */
}