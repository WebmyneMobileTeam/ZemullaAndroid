package com.zemulla.android.app.transaction.topup;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.zemulla.android.app.widgets.SmartFragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raghavthakkar on 19-08-2016.
 */
public class TopupHistoryPageAdapter extends SmartFragmentStatePagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> names = new ArrayList<>();


    public TopupHistoryPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return names.get(position);
    }

    public void addPage(Fragment fragment, String title) {
        fragments.add(fragment);
        names.add(title);
    }
}
