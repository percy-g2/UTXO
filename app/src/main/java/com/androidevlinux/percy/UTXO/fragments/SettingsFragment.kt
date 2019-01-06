package com.androidevlinux.percy.UTXO.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.preference.SwitchPreference
import com.afollestad.aesthetic.Aesthetic
import com.androidevlinux.percy.UTXO.R
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat() {

    private var mActivity: Activity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    override fun onDetach() {
        super.onDetach()
        this.mActivity = null
    }


    override fun onCreatePreferencesFix(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val darkModePref = findPreference(resources.getString(R.string.dark_mode_pref_key)) as SwitchPreference

        darkModePref.setOnPreferenceChangeListener { _, newValue ->
            if (newValue as Boolean) {
                Aesthetic.config {
                    activityTheme(R.style.AppCompatDemoThemeDark)
                    isDark(true)
                    textColorPrimary(res = R.color.text_color_primary_dark)
                    textColorSecondary(res = R.color.text_color_secondary_dark)
                    colorWindowBackground(res = R.color.text_color_secondary)
                    colorPrimary(res = R.color.md_grey_900)
                    colorPrimaryDark(res = R.color.md_black_1000)
                }
            } else {
                Aesthetic.config {
                    isDark(false)
                    activityTheme(R.style.AppCompatDemoTheme)
                    textColorPrimary(res = R.color.text_color_primary)
                    textColorSecondary(res = R.color.text_color_secondary)
                    colorWindowBackground(res = android.R.color.white)
                    colorPrimary(res = R.color.md_blue)
                    colorPrimaryDark(res = R.color.md_blueDark)
                }
            }
            true
        }
    }
}