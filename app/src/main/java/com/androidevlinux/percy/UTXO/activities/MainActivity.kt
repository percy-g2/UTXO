package com.androidevlinux.percy.UTXO.activities

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.androidevlinux.percy.UTXO.MyApp
import com.androidevlinux.percy.UTXO.R
import com.androidevlinux.percy.UTXO.fragments.*
import com.androidevlinux.percy.UTXO.utils.ConnectionReceiver
import es.dmoral.toasty.Toasty


class MainActivity : BaseFragmentActivity(), ConnectionReceiver.ConnectionReceiverListener {
    private var mDrawerLayout: DrawerLayout? = null
    private var mNavDrawer: NavigationView? = null
    private var mDrawerToggle: ActionBarDrawerToggle? = null
    private var alert: AlertDialog? = null

    private var doubleBackToExitPressedOnce = false
    private var mConnectionReceiver: ConnectionReceiver? = null

    @LayoutRes
    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDrawerLayout = findViewById(R.id.drawer_layout)
        mNavDrawer = findViewById(R.id.nav_drawer)
        mDrawerToggle = setupDrawerToggle()

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawerLayout!!.addDrawerListener(mDrawerToggle!!)

        // Setup drawer view
        setupDrawerContent(mNavDrawer!!)
        mNavDrawer!!.itemIconTintList = null
        // Select ExchangeCryptoPricesFragment on app start by default
        loadCryptoPricesFragment()
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(mConnectionReceiver)
        closeNavigationDrawer()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        return mDrawerToggle!!.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle!!.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Pass any configuration change to the drawer toggle
        mDrawerToggle!!.onConfigurationChanged(newConfig)
    }

    override fun onBackPressed() {
        if (!closeNavigationDrawer()) {
            val currentFragment = supportFragmentManager
                    .findFragmentById(R.id.content_frame)
            if (currentFragment !is ExchangeCryptoPricesFragment) {
                loadCryptoPricesFragment()
            } else {
                // If current fragment is ExchangeCryptoPricesFragment then exit
                if (doubleBackToExitPressedOnce) {
                    finish()
                } else {
                    Toasty.info(this, "Press BACK again to exit!", Toast.LENGTH_SHORT, true).show()
                }
                this.doubleBackToExitPressedOnce = true
                Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
                //super.onBackPressed();
            }
        }
    }

    private fun setupDrawerToggle(): ActionBarDrawerToggle {
        return ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.drawer_open, R.string.drawer_close)
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            selectDrawerItem(menuItem)
            true
        }
    }

    private fun selectDrawerItem(menuItem: MenuItem) {
        closeNavigationDrawer()
        when (menuItem.itemId) {
            R.id.nav_exchange_crypto_prices -> loadFragment(ExchangeCryptoPricesFragment::class.java, menuItem.itemId, resources.getString(R.string.nav_crypto_prices_btc))
            R.id.nav_global_crypto_prices -> loadFragment(GlobalCryptoPricesFragment::class.java, menuItem.itemId, resources.getString(R.string.nav_global_crypto_prices))
            R.id.nav_crypto_graphs -> loadFragment(GraphFragment::class.java, menuItem.itemId, resources.getString(R.string.nav_crypto_graph_btc))
            R.id.nav_bitfinex_candle_chart -> loadFragment(BitfinexCandleChartFragment::class.java, menuItem.itemId, resources.getString(R.string.nav_crypto_candle_chart_btc))
            R.id.nav_about -> startActivity(Intent(this, AboutTheDevActivity::class.java))
            R.id.nav_get_min_amount -> loadFragment(MinimumAmountFragment::class.java, menuItem.itemId, resources.getString(R.string.min_amount_check))
            R.id.nav_exchange_amount -> loadFragment(ExchangeAmountFragment::class.java, menuItem.itemId, resources.getString(R.string.exchange_amount))
            R.id.nav_get_status -> loadFragment(GetStatusFragment::class.java, menuItem.itemId, resources.getString(R.string.get_status))
            R.id.nav_news -> loadFragment(NewsFragment::class.java, menuItem.itemId, resources.getString(R.string.nav_news))
            else -> loadFragment(ExchangeCryptoPricesFragment::class.java, menuItem.itemId, resources.getString(R.string.nav_crypto_prices_btc))
        }
    }

    private fun closeNavigationDrawer(): Boolean {
        val drawerIsOpen = mDrawerLayout!!.isDrawerOpen(GravityCompat.START)
        if (drawerIsOpen) {
            mDrawerLayout!!.closeDrawer(GravityCompat.START)
        }
        return drawerIsOpen
    }

    fun hideNavigationBar() {
        closeNavigationDrawer()
    }

    private fun loadFragment(fragmentClass: Class<*>, @IdRes navDrawerCheckedItemId: Int,
                             toolbarTitle: CharSequence) {
        var fragment: Fragment? = null
        try {
            fragment = fragmentClass.newInstance() as Fragment
        } catch (e: Exception) {
            e.printStackTrace()
        }

        insertFragment(fragment!!)

        // Highlight the selected item
        mNavDrawer!!.setCheckedItem(navDrawerCheckedItemId)
        // Set action bar title
        title = toolbarTitle
    }

    private fun loadCryptoPricesFragment() {
        loadFragment(ExchangeCryptoPricesFragment::class.java, R.id.nav_exchange_crypto_prices,
                resources.getString(R.string.nav_crypto_prices_btc))
    }

    override fun onResume() {
        super.onResume()
        this.mConnectionReceiver = ConnectionReceiver()
        registerReceiver(this.mConnectionReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        MyApp.instance!!.setConnectionListener(this)
    }

    private fun showToast(context: Activity, text: String) {
        val Y = supportActionBar!!.height
        val inflater = context.layoutInflater
        val layout = inflater.inflate(R.layout.popup, LinearLayout(context), false)
        val tv = layout.findViewById<TextView>(R.id.popup)
        tv.text = text
        val toast = Toast(context)
        toast.setGravity(Gravity.TOP or Gravity.START or Gravity.FILL_HORIZONTAL, 0, Y)
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mConnectionReceiver)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (!isConnected) {
            showAlert()
        } else {
            dismissAlertAndLoadDefaultFragment()
        }
    }

    private fun showAlert() {
        val netNotAvailable = AlertDialog.Builder(this)
        netNotAvailable.setMessage("Could not find an Internet connection. Please check your settings and try again.")
        netNotAvailable.setTitle("Attention")
        netNotAvailable.setIcon(R.drawable.ic_error_black_24dp)
        netNotAvailable.setCancelable(false)
        alert = netNotAvailable.create()
        if (!alert!!.isShowing) {
            alert!!.show()
            showToast(this, "No Internet")
        }
    }

    private fun dismissAlertAndLoadDefaultFragment() {
        if (alert != null && alert!!.isShowing) {
            alert!!.dismiss()
            loadCryptoPricesFragment()
        }
    }
}

