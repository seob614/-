package com.example.safeinsurance.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.safeinsurance.R;

import java.util.Stack;

public enum BackStack {
    INSTANCE;

    // 모든 메뉴 항목에 대한 백업 기록 스택 조각
    private Stack<Fragment> CarStack = new Stack<>();
    private Stack<Fragment> insuranceStack = new Stack<>();
    private Stack<Fragment> mapStack = new Stack<>();
    private Stack<Fragment> userStack = new Stack<>();

    // 백 히스토리에 대한 메뉴 스택
    private Stack<Integer> menuStack = new Stack<>();

    // 지정된 메뉴 항목 ID에 대한 조각 스택 반환
    public Stack<Fragment> getFragmentStack(int menuId) {
        switch (menuId) {
            case R.id.menu_car:
                return CarStack;

            case R.id.menu_insurance:
                return insuranceStack;

            case R.id.menu_map:
                return mapStack;

            case R.id.menu_user:
                return userStack;

            default:
                return null;
        }
    }

    // 지정된 메뉴 항목 ID에 대한 조각 스택 크기 반환
    public int getFragmentStackCount(int menuId) {
        return getFragmentStack(menuId).size();
    }

    // 주어진 메뉴 항목의 조각 스택 위로 조각을 밀어 넣는다.
    public void pushFragmentToStack(int menuId, Fragment fragment) {
        if (getFragmentStack(menuId) != null) {
            getFragmentStack(menuId).push(fragment);
        }
    }

    // 지정된 메뉴 항목 ID에 대한 조각 스택의 마지막 항목 제거
    public boolean popFragmentBackStack(int menuId, FragmentManager fragmentManager) {
        if (getFragmentStack(menuId) != null) {
            if (getFragmentStack(menuId).size() > 1) {
                fragmentManager.beginTransaction()
                        .remove(getFragmentStack(menuId).pop())
                        .commit();

                return true;
            }
        }

        return false;
    }

    // 메뉴 스택 크기 반환
    public int getMenuStackCount() {
        return menuStack.size();
    }

    // 지정된 메뉴 ID가 이미 메뉴 스택에 있는지 확인 TRUE가 있으면 TRUE, 없으면 FALSE
    private boolean isHaveSameItem(int menuId) {
        return menuStack.contains(menuId);
    }

    // menuid를 메뉴 스택으로 푸시
    public void updateMenuStack(int menuId) {
        if (!isHaveSameItem(menuId)) {
            menuStack.push(menuId);
        }
    }

    // 메뉴 스택에서 마지막 메뉴 제거
    public int popMenuBackStack() {
        int stackSize = menuStack.size();

        if (stackSize > 1) {
            menuStack.pop();
            return menuStack.peek();
        }

        return menuStack.peek();
    }

    public void clearBackStackHistories() {
        CarStack.clear();
        insuranceStack.clear();
        mapStack.clear();
        userStack.clear();
        menuStack.clear();
    }
}
