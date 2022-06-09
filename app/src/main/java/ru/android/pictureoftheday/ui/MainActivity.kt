package ru.android.pictureoftheday.ui

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.android.pictureoftheday.R
import ru.android.pictureoftheday.ui.collapseearth.CollapseEarthFragment
import ru.android.pictureoftheday.ui.cosmosgallery.CosmosGalleryFragment
import ru.android.pictureoftheday.ui.day.MainFragment
import ru.android.pictureoftheday.ui.setting.SettingThemeFragment
import ru.android.pictureoftheday.ui.util.DATE_IN_GALLERY
import ru.android.pictureoftheday.ui.util.IS_THEME_STATUS
import ru.android.pictureoftheday.ui.util.THEME_STATUS
import ru.android.pictureoftheday.util.dateInformation

class MainActivity : AppCompatActivity() {

    private val TAG = "MyLogin ${this::class.java.simpleName} : ${this.hashCode()}"

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

                    /*supportFragmentManager.beginTransaction()
                        .replace(R.id.container, ViewEarthPagerFragment())
                        .commit()
                    */
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, CollapseEarthFragment())
                        .commit()
                }

                R.id.gallery -> {
//                    Toast.makeText(this, "Action", Toast.LENGTH_LONG).show()
                    val startDate = dateInformation(DATE_IN_GALLERY)
                    val endDate = dateInformation(0)

                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.container,
                            CosmosGalleryFragment.newInstance(startDate, endDate)
                        )
                        .commit()
                }

            }
            true
        }
        bottomNavigationView.setOnItemReselectedListener {

        }
    }
}