package com.androidevlinux.percy.UTXO.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.androidevlinux.percy.UTXO.R;
import com.androidevlinux.percy.UTXO.fragments.BitfinexCandleChartFragment;
import com.androidevlinux.percy.UTXO.fragments.CryptoPricesFragment;
import com.androidevlinux.percy.UTXO.fragments.GraphFragment;


public class MainActivity extends BaseFragmentActivity {
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavDrawer;
    private ActionBarDrawerToggle mDrawerToggle;

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
                R.string.drawer_open,  R.string.drawer_close);
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
        switch(menuItem.getItemId()) {
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
}

