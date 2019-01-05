package com.androidevlinux.percy.UTXO.activities

import android.graphics.Color
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import com.afollestad.aesthetic.*
import com.androidevlinux.percy.UTXO.R


abstract class BaseFragmentActivity : AestheticActivity() {
    protected var mToolbar: Toolbar? = null

    @LayoutRes
    protected open fun getLayoutResId(): Int {
        return R.layout.activity_base
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())

        // Set a Toolbar to replace the ActionBar
        mToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolbar)

        // If we haven't set any defaults, do that now
        if (Aesthetic.isFirstTime) {
            Aesthetic.config {
                activityTheme(R.style.AppCompatDemoTheme)
                textColorPrimary(res = R.color.text_color_primary)
                textColorSecondary(res = R.color.text_color_secondary)
                textColorPrimaryInverse(res = R.color.text_color_primary)
                textColorSecondaryInverse(res = R.color.text_color_secondary)
                colorPrimary(res = R.color.md_blue)
                colorPrimaryDark(res = R.color.md_blueDark)
                colorWindowBackground(res = android.R.color.white)
                colorAccent(res = R.color.md_pink)
                colorStatusBarAuto()
                colorNavigationBarAuto()
                textColorPrimary(Color.BLACK)
                navigationViewMode(NavigationViewMode.NONE)
                bottomNavigationBackgroundMode(BottomNavBgMode.PRIMARY)
                bottomNavigationIconTextMode(BottomNavIconTextMode.SELECTED_ACCENT)
                swipeRefreshLayoutColorsRes(
                        R.color.md_blue,
                        R.color.md_pink
                )
                attribute(R.attr.my_custom_attr, res = R.color.md_purple)
            }
        }
    }

    protected fun insertFragment(fragment: androidx.fragment.app.Fragment) {
        // Insert the fragment by replacing any existing fragment
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.content_frame, fragment)
                .commit()
    }
}
