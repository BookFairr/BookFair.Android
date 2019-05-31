package bookfair.android.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;


import java.util.ArrayList;

import bookfair.android.R;
import bookfair.android.ui.fragments.ChangePassword;
import bookfair.android.ui.fragments.EditProfileFragment;
import bookfair.android.ui.fragments.LogOutFragment;
import bookfair.android.util.SettingsStatePagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountSettingsActivity extends BaseActivity {

    @BindView(R.id.optionslist)
    ListView optionsListView;
    @BindView(R.id.back_arrow)
    ImageView backArrow;
    @BindView(R.id.viewpagercontainer)
    ViewPager viewPager;
    @BindView(R.id.rel_layout1)
    RelativeLayout relativeLayout;

    private Context context;
    private SettingsStatePagerAdapter settingsStatePagerAdapter;

    private static final String TAG = "AccountSettingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountsettings);
        ButterKnife.bind(this);
        context = AccountSettingsActivity.this;
        Log.d(TAG, "onCreate Started.");
        applicationComponent(this).inject(AccountSettingsActivity.this);

        setupSettingsList();
        setupFragments();
        //setup back arrow navigation to "Profile Fragment"
        backArrow.setOnClickListener(v -> finish());

    }
    //setup fragment using the functionality from our state pager adapter util class
    private void setupFragments(){
        settingsStatePagerAdapter = new SettingsStatePagerAdapter(getSupportFragmentManager());
        settingsStatePagerAdapter.addFragment(new EditProfileFragment(), getString(R.string.edit_profile_fragment)); //fragment 0
        settingsStatePagerAdapter.addFragment(new ChangePassword(), getString(R.string.change_password_fragment));
        settingsStatePagerAdapter.addFragment(new LogOutFragment(), getString(R.string.log_out_frag));

    }
    //responsible for navigating to the fragment
    private void setViewPager(int fragmentNumber){
        relativeLayout.setVisibility(View.GONE);
        viewPager.setAdapter(settingsStatePagerAdapter);
        viewPager.setCurrentItem(fragmentNumber);

    }

    //set up listview for settings option
    private void setupSettingsList(){
        ArrayList<String> options =  new ArrayList<>();
        options.add(getString(R.string.edit_profile_fragment)); //fragment 0
        options.add(getString(R.string.change_password_fragment));
        options.add(getString(R.string.log_out_frag));

        ArrayAdapter <String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, options);
        optionsListView.setAdapter(adapter);

        optionsListView.setOnItemClickListener((parent, view, position, id) -> setViewPager(position));
    }

}
