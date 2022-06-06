package ru.android.pictureoftheday.ui.collapseearth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import ru.android.pictureoftheday.R
import ru.android.pictureoftheday.databinding.CollapseEarthFragmentBinding
import ru.android.pictureoftheday.ui.earth.ViewEarthPagerFragment
import ru.android.pictureoftheday.util.dateFromCalendarView

private var dateChoice = ""

class CollapseEarthFragment : Fragment(R.layout.collapse_earth_fragment) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = CollapseEarthFragmentBinding.bind(view)

        val choiceData = binding.calendarDate

        val collapseAppBar = binding.appbar

        val bottomFab = binding.choiceDateFab
        bottomFab.visibility = View.GONE

        bottomFab.setOnClickListener {

            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ViewEarthPagerFragment.newInstance(dateChoice))
                .addToBackStack("")
                .commit()
        }
        collapseAppBar.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                val toolBarHeight = appBarLayout.totalScrollRange
                if (toolBarHeight + verticalOffset == 0)
                    bottomFab.visibility = View.VISIBLE
                else bottomFab.visibility = View.GONE
            }
        )

        choiceData.setOnDateChangeListener { _, year, month, dayOfMonth ->

            dateChoice = dateFromCalendarView(year, month, dayOfMonth)

        }
    }
}