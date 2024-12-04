package com.example.safeinsurance.base;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.safeinsurance.R;
import com.example.safeinsurance.util.BusProvider;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    int count = 0;

    protected abstract int setLayoutId();

    protected abstract void init();

    protected abstract void initView();

    protected abstract void initListener();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        ButterKnife.bind(this);
        BusProvider.getInstance().register(this);

        if (savedInstanceState == null) {
            init();
            initView();
            initListener();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    protected void addFragment(Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
                .add(R.id.frame_main, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    public void replaceFragment(Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFragment = fm.findFragmentById(R.id.frame_main);

        if (currentFragment.getClass().equals(fragment.getClass())) {
            Log.d("Dev ", " back fragment return : " + tag);
            return;
        }

        Log.d("Dev ", " back fragment start : " + tag);
        fm.beginTransaction()
                .setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
                .replace(R.id.frame_main, fragment, tag)
                .addToBackStack(tag)
                .commitAllowingStateLoss();
    }

    public int getcountFragment() {
        return count;
    }

    /*protected void replaceFragment(Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFragment = fm.findFragmentById(R.id.frame_main);
        if(tag.equals("mypage_fragment"))
        {
            if (currentFragment.getClass().equals(fragment.getClass()))return;
        }
        fm.beginTransaction()
                .setCustomAnimations(android.R.anim.sliding_in_left, android.R.anim.sliding_out_right)
                .replace(R.id.frame_main, fragment, tag)
                .addToBackStack(null)
                .commit();
    }*/

    protected String popFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate();

        return fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1).getName();
    }

    protected void removFragment(Fragment frag) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().remove(frag).commit();
    }

    public BaseFragment getVisibleFragment() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment.isVisible()) {
                return ((BaseFragment) fragment);
            }
        }
        return null;
    }

}
