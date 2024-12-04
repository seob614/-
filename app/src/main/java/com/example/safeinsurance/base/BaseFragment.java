package com.example.safeinsurance.base;

import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.safeinsurance.R;
import com.example.safeinsurance.util.BusProvider;

import butterknife.ButterKnife;

/**
 * Created by USER on 2017-11-30.
 */

public abstract class BaseFragment extends Fragment {

    protected abstract int setLayoutId();

    protected abstract void init();

    protected abstract void initView();

    protected abstract void initListener();

    protected Bundle getsavedInstanceState;

    private static View parent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            parent = inflater.inflate(setLayoutId(), container, false);
        } catch (InflateException e) {
            e.printStackTrace();
        }

        ButterKnife.bind(this, parent);
        BusProvider.getInstance().register(this);

        if (savedInstanceState == null) {
            init();
            initView();
            initListener();
        }

        return parent;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BusProvider.getInstance().unregister(this);
    }

    protected void addFragment(Fragment fragment, String tag) {
        Log.d("dev ", "TAG : " + tag);
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
                .add(R.id.frame_main, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    protected void replaceFragment(Fragment fragment, String tag) {
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
                .replace(R.id.frame_main, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    protected void childFragment(Fragment fragment, String tag) {
        FragmentManager cfm = getChildFragmentManager();
        cfm.beginTransaction()
                .setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
                .add(R.id.frame_main, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

//    protected void replaceFragment2(Fragment fragment, String tag) {
//        FragmentManager fm = getFragmentManager();
//        fm.beginTransaction()
//                .setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
//                .replace(R.id.frame_sub, fragment, tag)
//                .addToBackStack(tag)
//                .commit();
//    }


//    protected void replaceFragmentWithBundle(Fragment fragment,String tag, Bundle bundle){
//        FragmentManager fm = getFragmentManager();
//
//        fm.beginTransaction()
//                .setCustomAnimations(android.R.anim.sliding_in_left, android.R.anim.sliding_out_right)
//                .replace(R.id.frame_main, fragment, tag)
//                .addToBackStack(tag)
//                .commit();
//    }

    protected void refresh() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();
    }

    protected String popFragment() {
        FragmentManager fm = getFragmentManager();
        fm.popBackStackImmediate();

        return fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1).getName();
    }

    public BaseFragment getVisibleFragment() {
        for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {
            if (fragment.isVisible()) {
                return ((BaseFragment) fragment);
            }
        }
        return null;
    }


    protected void initToolbarTitle(String title) {
//        View view = ((MainActivity) getActivity()).getSupportActionBar().getCustomView();
//        TextView textView = view.findViewById(R.id.tv_toolbar_title);
//        textView.setText(title);
    }
}

