package bookfair.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import bookfair.android.ui.fragments.MessagesFragment;
import bookfair.android.ui.fragments.ProfileFragment;
import bookfair.android.ui.fragments.ShopFragment;

public class BottomBarAdapter extends SmartFragmentStatePagerAdapter {
    private final List<Fragment> fragments = new ArrayList<>();

    public BottomBarAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void addFragments(Fragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);

    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
