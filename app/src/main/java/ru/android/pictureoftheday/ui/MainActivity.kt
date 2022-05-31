package ru.android.pictureoftheday.ui

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.android.pictureoftheday.R
import ru.android.pictureoftheday.ui.day.MainFragment
import ru.android.pictureoftheday.ui.earth.EarthFragment
import ru.android.pictureoftheday.ui.earth.ViewEarthPagerFragment
import ru.android.pictureoftheday.ui.setting.SettingThemeFragment
import ru.android.pictureoftheday.util.IS_THEME_STATUS
import ru.android.pictureoftheday.util.THEME_STATUS

//class MainActivity : AppCompatActivity(R.layout.main_activity) {
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val getThemeSaved = getSharedPreferences(THEME_STATUS, Context.MODE_PRIVATE)
            ?.getInt(IS_THEME_STATUS, 0)

        if (getThemeSaved != null) {
            setTheme(getThemeSaved)
        }
        setContentView(R.layout.main_activity)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        val menuTop: MaterialToolbar = findViewById(R.id.content_app_bar)
        menuTop.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.setting -> {
                    supportFragmentManager.apply {
                        beginTransaction()
                            .replace(R.id.container, SettingThemeFragment.newInstance())
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                    }
                    true
                }
                R.id.action -> {
                    Toast.makeText(this, "Action", Toast.LENGTH_LONG).show()
                    true
                }
                else -> true
            }
        }
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.day_photo -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MainFragment.newInstance())
                        .commitNow()
                }
                R.id.earth -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, ViewEarthPagerFragment())
                        .commit()
/*
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, EarthFragment())
                        .commit()
                    */
                }
                R.id.mars -> {
                    Toast.makeText(this, "Action", Toast.LENGTH_LONG).show()
/*
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container,EarthFragment())
                        .commit()
 */
                }
            }
            true
        }
/*
        val menuBottom : BottomNavigationMenuView = findViewById(R.id.bottom_app_bar)
//        menuBottom.setOnCl
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
    */
    }
}