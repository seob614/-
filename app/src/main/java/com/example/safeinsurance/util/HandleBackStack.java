package com.example.safeinsurance.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.safeinsurance.R;

import java.util.Stack;

public class HandleBackStack {
    // fragment manager for fragment handling
    private FragmentManager fragmentManager;
    // BackStack class instance
    private BackStack backStack = BackStack.INSTANCE;

    // constructor with parameter FragmentManager
    public HandleBackStack(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    /*Add's new fragment and updates stack count for active menu
     * @param menuId - active menu id for stack update
     * @param fragment - fragment that will be add
     * */

    public void loadFragmentAndAddToBackStack(int menuId, Fragment fragment) {
        switch (menuId) {
            case R.id.menu_car:
                addFragmentAndBackStack(fragment, menuId);
                break;

            case R.id.menu_insurance:
                addFragmentAndBackStack(fragment, menuId);
                break;

            case R.id.menu_map:
                addFragmentAndBackStack(fragment, menuId);
                break;

            case R.id.menu_user:
                addFragmentAndBackStack(fragment, menuId);
                break;

        }
    }

    // adds fragment and pushes fragment to stack
    private void addFragmentAndBackStack(Fragment fragment, int menuId) {
        addFragment(fragment);
        backStack.pushFragmentToStack(menuId, fragment);
    }

    // adds fragment
    private void addFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .add(R.id.frame_main, fragment)
                .commit();
    }

    // adds base fragments for every menu item and pushes fragments to menu's fragment stack
    public Fragment addBaseFragments(
            Fragment carFragment,
            Fragment insuranceFragment,
            Fragment mapFragment,
            Fragment userFragment
    ) {
        fragmentManager.beginTransaction().add(R.id.frame_main, carFragment, "car").commit();
        fragmentManager.beginTransaction().add(R.id.frame_main, insuranceFragment, "insurance").hide(insuranceFragment).commit();
        fragmentManager.beginTransaction().add(R.id.frame_main, mapFragment, "map").hide(mapFragment).commit();
        fragmentManager.beginTransaction().add(R.id.frame_main, userFragment, "user").hide(userFragment).commit();

        backStack.pushFragmentToStack(R.id.menu_car, carFragment);
        backStack.pushFragmentToStack(R.id.menu_insurance, insuranceFragment);
        backStack.pushFragmentToStack(R.id.menu_map, mapFragment);
        backStack.pushFragmentToStack(R.id.menu_user, userFragment);

        return carFragment;
    }

    // returns stack size for given menu item
    public int getFragmentStackCount(int menuId) {
        return backStack.getFragmentStackCount(menuId);
    }

    // removes last fragment for given menu item
    public boolean popFragmentBackStack(int menuId) {
        return backStack.popFragmentBackStack(menuId, fragmentManager);
    }

    // updates menu stack count
    public void updateMenuStackCount(int menuId) {
        backStack.updateMenuStack(menuId);
    }

    // returns back previous menu item
    public int popMenuStack() {
        return backStack.popMenuBackStack();
    }

    // returns menu stack size
    public int getMenuStackCount() {
        return backStack.getMenuStackCount();
    }

    // when different menu item selected, this method hides previous menu item's fragments and shows target menu item's fragments
    public void handleMenuSwitch(int activeMenuId, int targetMenuId) {
        if (activeMenuId != targetMenuId) {
            Stack<Fragment> targetFragments = backStack.getFragmentStack(targetMenuId);

            if (targetFragments != null) {
                for (Fragment fragment : targetFragments) {
                    fragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();
                }
            }

            Stack<Fragment> activeFragments = backStack.getFragmentStack(activeMenuId);

            if (activeFragments != null) {
                for (Fragment fragment : activeFragments) {
                    fragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss();
                }
            }

        }
    }

    // resets stacks
    public void clearBackStackHistory() {
        backStack.clearBackStackHistories();
    }
}