package ru.android.pictureoftheday.ui.earth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ru.android.pictureoftheday.R
import ru.android.pictureoftheday.util.dateInformation
import ru.android.pictureoftheday.viewmodel.earth.EarthViewModel
import java.text.SimpleDateFormat
import java.util.*


class ViewEarthPagerFragment : Fragment(R.layout.earth_pager_fragment) {

    companion object {
        private const val ARG_DATE = "ARG_DATE"
        fun newInstance(date: String) = ViewEarthPagerFragment().apply {
            arguments = bundleOf(ARG_DATE to date)
        }
    }

    private val viewModel: EarthViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dateChoiceInput = arguments?.getString(ARG_DATE)

/*
        if (savedInstanceState == null) {
            checkDataFromRemoteSourceEarth(0)
        } else checkDataFromEarthOnDateChoice(dateChoiceInput!!)
*/

        if (savedInstanceState == null) {
            checkDataFromEarthOnDateChoice(dateChoiceInput!!)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pager: ViewPager2 = view.findViewById(R.id.earth_view_pager)
        pager.adapter = PagerAdapter(this)
//        pager.setPageTransformer(ZoomOutPageTransformer())
        pager.setPageTransformer(DepthPageTransformer())

        val tabLayout: TabLayout = view.findViewById(R.id.earth_tab_layout)
        TabLayoutMediator(tabLayout, pager) { tab, position ->

            viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
                viewModel.error.collect {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                }
            }
            viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
                viewModel.dataResponseSourceOfEarth.collect { dataEarthArray ->
                    dataEarthArray?.let {
                        tab.text = timeOfPictureEarth(it[position].date)
                    }
                }
            }
        }.attach()

    }

    class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return 10
        }

        override fun createFragment(position: Int): Fragment =
            EarthPagerFragment.newInstance(position)

    }

    private fun checkDataFromRemoteSourceEarth(dateMinus: Int) {
        viewModel.requestPictureOfEarth(dateInformation(dateMinus))
    }

    private fun checkDataFromEarthOnDateChoice(date: String) {
        viewModel.requestPictureOfEarth(date)
    }

    private fun timeOfPictureEarth(dataTime: String): String {
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        val formatterHourSecond = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return formatter.format(formatterHourSecond.parse(dataTime) as Date).toString()
    }
}