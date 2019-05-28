package bookfair.android.ui.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.FrameLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import javax.inject.Inject;

import bookfair.android.R;
import bookfair.android.api.BookFairApiService;
import bookfair.android.core.PreferenceManager;
import bookfair.android.ui.adapter.BottomBarAdapter;
import bookfair.android.ui.fragments.ProfileFragment;
import bookfair.android.ui.fragments.MessagesFragment;
import bookfair.android.ui.fragments.ShopFragment;
import bookfair.android.ui.views.ViewPagerNoSwipe;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Lazy;

public class MainActivity extends BaseActivity {

    @Inject
    PreferenceManager preferenceManager;
    @Inject
    Lazy<BookFairApiService> bookFairApiServiceLazy;
    @BindView(R.id.bottom_navigation_bar)
    AHBottomNavigation bottomNavigationBar;
    @BindView(R.id.main_coordinator)
    CoordinatorLayout mainCoordinator;
    @BindView(R.id.frame_container)
    FrameLayout frameContainer;
    @BindView(R.id.viewpager_no_swipe)
    ViewPagerNoSwipe viewpagerNoSwipe;

    private BottomBarAdapter bottomBarAdapter;
    private static final int SHOP_FRAGMENT = 0;
    private static final int MESSAGES_FRAGMENT = 1;
    private static final int PROFILE_FRAGMENT = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_main_);
        ButterKnife.bind(this);
        applicationComponent(this).inject(MainActivity.this);

        //disable viewpager swiping (this is a custom view pager)
        viewpagerNoSwipe.setPagingEnabled(false);

        bottomBarAdapter = new BottomBarAdapter(getSupportFragmentManager());
        viewpagerNoSwipe.setAdapter(bottomBarAdapter);

        // Create items
        AHBottomNavigationItem item0 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_book_black_24dp, R.color.primary);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_message_black_24dp, R.color.primary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.ic_person_black_24dp, R.color.primary);

        // Add items
        bottomNavigationBar.addItem(item0);
        bottomNavigationBar.addItem(item1);
        bottomNavigationBar.addItem(item2);

        // Set background color, active and inactive
        bottomNavigationBar.setDefaultBackgroundColor(getResources().getColor(R.color.icons));
        bottomNavigationBar.setAccentColor(getResources().getColor(R.color.primary));
        bottomNavigationBar.setInactiveColor(getResources().getColor(R.color.secondary_text));

        // Disable the translation inside the CoordinatorLayout
        bottomNavigationBar.setBehaviorTranslationEnabled(false);

        // Force to tint the drawable (useful for font with icon for example)
        bottomNavigationBar.setForceTint(true);

        // Manage titles
        bottomNavigationBar.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        // Set listeners
        bottomNavigationBar.setOnTabSelectedListener((position, wasSelected) -> {

            if (position == SHOP_FRAGMENT)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new ShopFragment()).commit();
            }
            else if (position == MESSAGES_FRAGMENT)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new MessagesFragment()).commit();
            }
            else if (position == PROFILE_FRAGMENT)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new ProfileFragment()).commit();
            }
            return true;
        });

        // Set current item programmatically
        bottomNavigationBar.setCurrentItem(0);

}


}
