package ru.android.pictureoftheday.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.android.pictureoftheday.R
import ru.android.pictureoftheday.databinding.SettingThemeFragmentBinding
import ru.android.pictureoftheday.util.IS_THEME_STATUS
import ru.android.pictureoftheday.util.THEME_STATUS
import ru.android.pictureoftheday.util.recreateFragment

private const val BOTTOM_CHOICE = "BOTTOM_CHOICE"
private const val IS_BOTTOM_CHOICE = "IS_BOTTOM_CHOICE"

class SettingThemeFragment : Fragment(R.layout.setting_theme_fragment) {

    companion object {
        const val KEY_THEME = "KEY_THEME"
        fun newInstance() = SettingThemeFragment()
    }

    private var savedTheme: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.setTheme(getThemeApp()!!)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = SettingThemeFragmentBinding.bind(view)

        when (getBottomChoice()) {
            R.id.switch_default -> binding.switchDefault.isChecked = true
            R.id.switch_universe -> binding.switchUniverse.isChecked = true
            R.id.switch_mars -> binding.switchMars.isChecked = true
            R.id.switch_moon -> binding.switchMoon.isChecked = true
        }

        binding.radioSwitchMainGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.switch_default -> {
                    savedTheme = R.style.Theme_PictureOfTheDay
                    saveBottomChoice(checkedId)
                    saveThemeApp(savedTheme!!)
                    recreateFragment(this@SettingThemeFragment, requireActivity())

                }
                R.id.switch_universe -> {
                    savedTheme = R.style.Theme_PictureOfTheDay_Universe
                    saveBottomChoice(checkedId)
                    saveThemeApp(savedTheme!!)
                    recreateFragment(this@SettingThemeFragment, requireActivity())

                }
                R.id.switch_mars -> {
                    savedTheme = R.style.Theme_PictureOfTheDay_Mars
                    saveBottomChoice(checkedId)
                    saveThemeApp(savedTheme!!)
                    recreateFragment(this@SettingThemeFragment, requireActivity())
                }
                R.id.switch_moon -> {
                    savedTheme = R.style.Theme_PictureOfTheDay_Moon
                    saveBottomChoice(checkedId)
                    saveThemeApp(savedTheme!!)
                    recreateFragment(this@SettingThemeFragment, requireActivity())
                }
            }
        }
    }

    private fun saveThemeApp(theme: Int) {
        activity?.let {
            with(
                it.getSharedPreferences(THEME_STATUS, Context.MODE_PRIVATE)
                    .edit()
            ) {
                putInt(IS_THEME_STATUS, theme)
                apply()
            }
        }
    }

    private fun getThemeApp(): Int? {
        return activity?.getSharedPreferences(THEME_STATUS, Context.MODE_PRIVATE)
            ?.getInt(IS_THEME_STATUS, 0)
    }

    private fun saveBottomChoice(radioBottom: Int) {
        activity?.let {
            with(
                it.getSharedPreferences(BOTTOM_CHOICE, Context.MODE_PRIVATE)
                    .edit()
            ) {
                putInt(IS_BOTTOM_CHOICE, radioBottom)
                apply()
            }
        }
    }

    private fun getBottomChoice(): Int? {
        return activity?.getSharedPreferences(BOTTOM_CHOICE, Context.MODE_PRIVATE)?.getInt(
            IS_BOTTOM_CHOICE, 0
        )
    }
}