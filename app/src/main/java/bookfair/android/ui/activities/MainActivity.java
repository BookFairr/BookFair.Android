package bookfair.android.ui.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatDelegate;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import javax.inject.Inject;

import bookfair.android.R;
import bookfair.android.api.BookFairApiService;
import bookfair.android.core.PreferenceManager;
import bookfair.android.db.BookFairRepository;
import bookfair.android.ui.views.ViewPagerNoSwipe;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Lazy;

public class MainActivity extends BaseActivity {

    @Inject
    PreferenceManager preferenceManager;
    @Inject
    Lazy<BookFairApiService> bookFairApiServiceLazy;
    @Inject
    BookFairRepository bookFairRepository;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    @BindView(R.id.main_coordinator)
    CoordinatorLayout mainCoordinator;
    @BindView(R.id.fab_home)
    FloatingActionButton fabHome;
    @BindView(R.id.frame_container)
    FrameLayout frameContainer;
    @BindView(R.id.viewpager_no_swipe)
    ViewPagerNoSwipe viewpagerNoSwipe;
    @BindView(R.id.nested_linear)
    LinearLayout nestedLinear;
    @BindView(R.id.nested_scrollview)
    NestedScrollView nestedScrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_main_);
        ButterKnife.bind(this);
        applicationComponent(this).inject(MainActivity.this);

        //disable viewpager swiping (this is a custom view pager)
        viewpagerNoSwipe.setPagingEnabled(false);

        bottomNavigationBar
                //add items to bottom nav bar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_black_24dp, "Home"))
                .addItem(new BottomNavigationItem(R.drawable.ic_message_black_24dp, "Messages"))
                .addItem(new BottomNavigationItem(R.drawable.ic_notifications_black_24dp, "Notifications"))
                .addItem(new BottomNavigationItem(R.drawable.ic_person_black_24dp, "Me"))

                //bottom nav bar customization
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .setActiveColor(R.color.primary)
                .setInActiveColor(R.color.secondary_text)
                .setBarBackgroundColor(R.color.icons)
                .initialise();

        //auto hide
        bottomNavigationBar.setAutoHideEnabled(true);
        //set bnb with fab
        bottomNavigationBar.setFab(fabHome);

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {

            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });

    }
}
