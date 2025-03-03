package i.com.TrillionaireBill.bookkeeping;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ListPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList;
    private List<String> titleList;

    public ListPagerAdapter(FragmentManager fm , List<Fragment> list , List<String> titleList) {
        super(fm);
        mList = list;
        this.titleList = titleList;
    }

    @Override public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override public int getCount() {
        return mList.size();
    }

    @Nullable @Override public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
