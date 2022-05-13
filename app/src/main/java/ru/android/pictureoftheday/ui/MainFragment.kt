package ru.android.pictureoftheday.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.android.pictureoftheday.R
import ru.android.pictureoftheday.databinding.MainFragmentBinding
import ru.android.pictureoftheday.domain.NasaRepositoryImpl
import ru.android.pictureoftheday.util.hideKeyboard
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment(R.layout.main_fragment) {
    private lateinit var behavior: BottomSheetBehavior<ConstraintLayout>

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(NasaRepositoryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            checkDataFromRemoteSource(0)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = MainFragmentBinding.bind(view)

        val myWebView: WebView = binding.webView
        myWebView.settings.javaScriptEnabled = true
        myWebView.webViewClient = WebViewClient()
        binding.webBlock.visibility = View.GONE

        setBottomSheetBehavior(binding.bottomSheetContainer)

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == 5) behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset < 0) behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        })

        binding.clearTextButton.setEndIconOnClickListener {
//            binding.bottomSheetContainer.visibility=View.GONE

            binding.webBlock.visibility = View.VISIBLE

            val searchText = binding.editSearch.text.toString()
            val mainUriWiki = getString(R.string.url_wiki)
            val searchUri = mainUriWiki.plus(searchText)

            binding.clearTextButton.hideKeyboard()
            myWebView.loadUrl(searchUri)

        }
        binding.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.choice_now -> {
                    checkDataFromRemoteSource(0)
                }
                R.id.choice_yesterday -> {
                    checkDataFromRemoteSource(1)
                }
                R.id.choice_before_yesterday -> {
                    checkDataFromRemoteSource(2)
                }
            }
        }

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
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenCreated {
            viewModel.dataResponseSource.collect { dataArray ->
                dataArray?.let {
                    myWebView.loadUrl(getString(R.string.clear_web_view))
                    binding.webBlock.visibility = View.GONE

//                    binding.bottomSheetContainer.visibility=View.VISIBLE

                    myWebView.clearCache(true)
                    myWebView.clearHistory()

                    binding.bottomSheetDescriptionHeader.text = it.title
                    binding.bottomSheetDescription.text = it.explanation

                    when {
                        it.url.isEmpty() -> {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.empty_foto),
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                        it.url.contains(getString(R.string.search_your_tube)) -> {
                            AlertDialog.Builder(requireContext())
                                .setTitle(getString(R.string.dialog_titile_see_on_youtube))
                                .setMessage(getString(R.string.dialog_message_to_see))
                                .setPositiveButton(getString(R.string.dialog_yes)) { _, _ ->
                                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.url)))
                                }
                                .setNegativeButton(getString(R.string.dialog_no)) { dialog, _ ->
                                    dialog.dismiss()
                                    binding.img.load(it.url)
                                }
                                .create()
                                .show()
                        }
                        else -> {
                            binding.img.load(it.url)
                        }
                    }
                }
            }
        }

        binding.bottomAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.setting -> {
                    Toast.makeText(requireContext(), "Setting", Toast.LENGTH_LONG).show()
                    true
                }
                R.id.action -> {
                    Toast.makeText(context, "Action", Toast.LENGTH_LONG).show()
                    true
                }
                else -> true
            }
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    @SuppressLint("SimpleDateFormat")
    private fun dateInformation(minusDate: Int): String {
        TimeZone.setDefault(TimeZone.getTimeZone(getString(R.string.google_time)))
        val currentDate =
            Calendar.getInstance(TimeZone.getTimeZone(getString(R.string.google_time)))
        currentDate.add(Calendar.DATE, -minusDate)
        val dateResult = currentDate.time
        return SimpleDateFormat("yyyy-MM-dd").format(dateResult)
    }

    private fun checkDataFromRemoteSource(dateMinus: Int) {
        viewModel.requestPictureOfTheDay(dateInformation(dateMinus))
    }
}