package com.androidevlinux.percy.UTXO.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidevlinux.percy.UTXO.MyApp;
import com.androidevlinux.percy.UTXO.R;
import com.androidevlinux.percy.UTXO.fragments.BitfinexCandleChartFragment;
import com.androidevlinux.percy.UTXO.fragments.CryptoPricesFragment;
import com.androidevlinux.percy.UTXO.fragments.GraphFragment;
import com.androidevlinux.percy.UTXO.utils.ConnectionReceiver;

import java.util.Objects;


public class MainActivity extends BaseFragmentActivity implements ConnectionReceiver.ConnectionReceiverListener {
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    AlertDialog alert;

    @Override
    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavDrawer = findViewById(R.id.nav_drawer);
        mDrawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        // Setup drawer view
        setupDrawerContent(mNavDrawer);
        mNavDrawer.setItemIconTintList(null);
        // Select CryptoPricesFragment on app start by default
        loadCryptoPricesFragment();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mConnectionReceiver);
        closeNavigationDrawer();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggle
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (!closeNavigationDrawer()) {
            Fragment currentFragment = getSupportFragmentManager()
                    .findFragmentById(R.id.content_frame);
            if (!(currentFragment instanceof CryptoPricesFragment)) {
                loadCryptoPricesFragment();
            } else {
                // If current fragment is CryptoPricesFragment then exit
                super.onBackPressed();
            }
        }
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.drawer_open, R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem(menuItem);
                    return true;
                });
    }

    private void selectDrawerItem(MenuItem menuItem) {
        closeNavigationDrawer();
        switch (menuItem.getItemId()) {
            case R.id.nav_crypto_prices:
                loadFragment(CryptoPricesFragment.class, menuItem.getItemId(), getResources().getString(R.string.nav_crypto_prices_btc));
                break;
            case R.id.nav_crypto_graphs:
                loadFragment(GraphFragment.class, menuItem.getItemId(), getResources().getString(R.string.nav_crypto_graph_btc));
                break;
            case R.id.nav_bitfinex_candle_chart:
                loadFragment(BitfinexCandleChartFragment.class, menuItem.getItemId(), getResources().getString(R.string.nav_crypto_candle_chart_btc));
                break;
            case R.id.nav_about:
                startActivity(new Intent(this, AboutTheDevActivity.class));
                break;
            default:
                loadFragment(CryptoPricesFragment.class, menuItem.getItemId(), getResources().getString(R.string.nav_crypto_prices_btc));
        }
    }

    private boolean closeNavigationDrawer() {
        boolean drawerIsOpen = mDrawerLayout.isDrawerOpen(GravityCompat.START);
        if (drawerIsOpen) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        return drawerIsOpen;
    }

    public void hideNavigationBar() {
        closeNavigationDrawer();
    }

    private void loadFragment(Class fragmentClass, @IdRes int navDrawerCheckedItemId,
                              CharSequence toolbarTitle) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        insertFragment(fragment);

        // Highlight the selected item
        mNavDrawer.setCheckedItem(navDrawerCheckedItemId);
        // Set action bar title
        setTitle(toolbarTitle);
    }

    private void loadCryptoPricesFragment() {
        loadFragment(CryptoPricesFragment.class, R.id.nav_crypto_prices,
                getResources().getString(R.string.nav_crypto_prices_btc));
    }
    ConnectionReceiver mConnectionReceiver;
    @Override
    protected void onResume() {
        super.onResume();
        this.mConnectionReceiver = new ConnectionReceiver();
        registerReceiver(this.mConnectionReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        MyApp.getInstance().setConnectionListener(this);
    }

    public void showToast(Activity context, String text) {
        int Y = Objects.requireNonNull(getSupportActionBar()).getHeight();
        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.popup, new LinearLayout(context), false);
        TextView tv = layout.findViewById(R.id.popup);
        tv.setText(text);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP | Gravity.START | Gravity.FILL_HORIZONTAL, 0, Y);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mConnectionReceiver);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected) {
            showAlert();
        } else {
           dismissAlertAndLoadDefaultFragment();
        }
    }

    void showAlert() {
        AlertDialog.Builder netNotAvailable = new AlertDialog.Builder(this);
        netNotAvailable.setMessage("Could not find an Internet connection. Please check your settings and try again.");
        netNotAvailable.setTitle("Attention");
        netNotAvailable.setIcon(R.drawable.ic_error_black_24dp);
        netNotAvailable.setCancelable(false);
        alert = netNotAvailable.create();
        if (!alert.isShowing()) {
            alert.show();
            showToast(this, "No Internet");
        }
    }

    void dismissAlertAndLoadDefaultFragment() {
        if (alert != null && alert.isShowing()) {
            alert.dismiss();
            loadCryptoPricesFragment();
        }
    }
}

