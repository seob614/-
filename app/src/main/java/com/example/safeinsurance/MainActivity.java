package com.example.safeinsurance;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.safeinsurance.base.BaseActivity;
import com.example.safeinsurance.util.HandleBackStack;
import com.example.safeinsurance.view.car.CarFragment;
import com.example.safeinsurance.view.insurance.InsuranceFragment;
import com.example.safeinsurance.view.map.MapFragment;
import com.example.safeinsurance.view.user.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    //BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;

    Menu menu;
    private final CarFragment carFragment = new CarFragment();
    private final InsuranceFragment insuranceFragment = new InsuranceFragment();
    private final MapFragment mapFragment = new MapFragment();
    private final UserFragment userFragment = new UserFragment();

    MenuItem get_menuitem = null;

    //Active menu item's id
    int activeMenuId;
    boolean nowcar = true;
    boolean nowinsurance = false;
    boolean nowmap = false;
    boolean nowsuser = false;

    Fragment activeFragment;

    private HandleBackStack handleBackStack;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        handleBackStack = new HandleBackStack(getSupportFragmentManager());

        handleBackStack.clearBackStackHistory();
        //Add base fragments when app first start
        activeFragment = handleBackStack.addBaseFragments(
                carFragment,
                insuranceFragment,
                mapFragment,
                userFragment
        );

        //Set active menu id
        activeMenuId = R.id.menu_car;


        //Update menu stack count with main menu item which home
        handleBackStack.updateMenuStackCount(activeMenuId);

        menu = bottomNavigationView.getMenu();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                get_menuitem = menuItem;

                //return TRUE if menu item selected, return FALSE if not
                switch (menuItem.getItemId()) {
                    case R.id.menu_car:
                        switchMenu(activeMenuId, R.id.menu_car);
                        activeMenuId = R.id.menu_car;

                        menuItem.setIcon(R.drawable.bottom_car_on);
                        menu.findItem(R.id.menu_insurance).setIcon(R.drawable.bottom_insurance_off);
                        menu.findItem(R.id.menu_map).setIcon(R.drawable.bottom_map_off);
                        menu.findItem(R.id.menu_user).setIcon(R.drawable.bottom_user_off);

                        nowcar = true;
                        nowinsurance = false;
                        nowmap = false;
                        nowsuser = false;

                        return true;

                    case R.id.menu_insurance:
                        switchMenu(activeMenuId, R.id.menu_insurance);
                        activeMenuId = R.id.menu_insurance;

                        menuItem.setIcon(R.drawable.bottom_insurance_on);
                        menu.findItem(R.id.menu_car).setIcon(R.drawable.bottom_car_off);
                        menu.findItem(R.id.menu_map).setIcon(R.drawable.bottom_map_off);
                        menu.findItem(R.id.menu_user).setIcon(R.drawable.bottom_user_off);

                        nowcar = false;
                        nowinsurance = true;
                        nowmap = false;
                        nowsuser = false;

                        return true;

                    case R.id.menu_map:
                        switchMenu(activeMenuId, R.id.menu_map);
                        activeMenuId = R.id.menu_map;

                        menuItem.setIcon(R.drawable.bottom_map_on);
                        menu.findItem(R.id.menu_car).setIcon(R.drawable.bottom_car_off);
                        menu.findItem(R.id.menu_insurance).setIcon(R.drawable.bottom_insurance_off);
                        menu.findItem(R.id.menu_user).setIcon(R.drawable.bottom_user_off);

                        nowcar = false;
                        nowinsurance = false;
                        nowmap = true;
                        nowsuser = false;

                        return true;

                    case R.id.menu_user:
                        switchMenu(activeMenuId, R.id.menu_user);
                        activeMenuId = R.id.menu_user;

                        menuItem.setIcon(R.drawable.bottom_user_on);
                        menu.findItem(R.id.menu_car).setIcon(R.drawable.bottom_car_off);
                        menu.findItem(R.id.menu_insurance).setIcon(R.drawable.bottom_insurance_off);
                        menu.findItem(R.id.menu_map).setIcon(R.drawable.bottom_map_off);

                        nowcar = false;
                        nowinsurance = false;
                        nowmap = false;
                        nowsuser = true;
                        return true;
                }

                return false;
            }
        });
    }

    // Switch menus
    public void switchMenu(int activeMenuId, int targetMenuId) {
        // handle menu switch with this method
        handleBackStack.handleMenuSwitch(activeMenuId, targetMenuId);
        // update menu stack count when target menu showed
        handleBackStack.updateMenuStackCount(targetMenuId);
    }

}
