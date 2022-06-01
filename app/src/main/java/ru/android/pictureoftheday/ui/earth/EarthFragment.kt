package ru.android.pictureoftheday.ui.earth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import coil.load
import ru.android.pictureoftheday.R
import ru.android.pictureoftheday.databinding.EarthFragmentBinding
import ru.android.pictureoftheday.util.IS_THEME_STATUS
import ru.android.pictureoftheday.util.THEME_STATUS
import ru.android.pictureoftheday.util.dateInformation
import ru.android.pictureoftheday.viewmodel.earth.EarthViewModel

class EarthFragment : Fragment(R.layout.earth_fragment) {

    companion object {
        fun newInstance() = EarthFragment()
    }

    private val viewModel: EarthViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
//            checkDataFromRemoteSourceEarth(0)
            checkDataFromRemoteSourceEarth(2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val getTheme = activity?.getSharedPreferences(THEME_STATUS, Context.MODE_PRIVATE)
            ?.getInt(IS_THEME_STATUS, 0)

        if (getTheme != null) {
            activity?.setTheme(getTheme)
        }

        val themedCurrent = getTheme?.let { ContextThemeWrapper(context, it) }
        return LayoutInflater.from(themedCurrent).inflate(R.layout.earth_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = EarthFragmentBinding.bind(view)

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.loading.collect {
                binding.progress.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.error.collect {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.dataResponseSourceOfEarth.collect { dataEarthArray ->
                dataEarthArray?.let {

                    binding.captionText.text = it[0].caption
                    binding.imageEarth.load(it[0].urlImage)
                }
            }
        }
    }

    private fun checkDataFromRemoteSourceEarth(dateMinus: Int) {
        viewModel.requestPictureOfEarth(dateInformation(dateMinus))
    }
}