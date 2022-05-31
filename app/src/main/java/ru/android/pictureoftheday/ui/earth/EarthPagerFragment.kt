package ru.android.pictureoftheday.ui.earth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import coil.load
import ru.android.pictureoftheday.R
import ru.android.pictureoftheday.databinding.EarthPagerItemFragmentBinding
import ru.android.pictureoftheday.util.dateRus
import ru.android.pictureoftheday.viewmodel.earth.EarthViewModel

class EarthPagerFragment : Fragment(R.layout.earth_pager_item_fragment) {

    companion object {
        private const val ARG_NUMBER = "ARG_NUMBER"
        fun newInstance(number: Int) = EarthPagerFragment().apply {
            arguments = bundleOf(ARG_NUMBER to number)
        }
    }

    private val viewModel: EarthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = EarthPagerItemFragmentBinding.bind(view)

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
                    binding.description.text = it[requireArguments().getInt(ARG_NUMBER)].caption
                    binding.earthDatePhoto.text =
                        dateRus(it[requireArguments().getInt(ARG_NUMBER)].date)
                    binding.imageEarthOnTime.load(it[requireArguments().getInt(ARG_NUMBER)].urlImage)
                }
            }
        }
    }
}