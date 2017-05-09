package io.excitinglab.xtasker;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.excitinglab.xtasker.R;
import io.excitinglab.xtasker.DatabaseHelper;
import io.excitinglab.xtasker.TodayFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;

    private ArrayAdapter<String> mAdapter;

    private DrawerLayout drawer;
    private View navHeader;
//    private ImageView imgNavHeaderBg, imgProfile;
//    private TextView txtName, txtWebsite;
//    private Toolbar toolbar;
    private FloatingActionButton fab;

    // urls to load navigation header background image
    // and profile image
//    private static final String urlNavHeaderBg = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";
//    private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_TODAY = "home";
    private static final String TAG_WEEK = "photos";
    private static final String TAG_INBOX = "movies";
//    private static final String TAG_NOTIFICATIONS = "notifications";
//    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_TODAY;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;







    Intent intent, intent2;
    private NavigationView navigationView;
    DatabaseHelper mDatabaseHelper;

    protected ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
//        txtName = (TextView) navHeader.findViewById(R.id.name);
//        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
//        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
//        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // load nav menu header data


        // initializing navigation menu
//        setUpNavigationView();

//        if (savedInstanceState == null) {
//            navItemIndex = 0;
//            CURRENT_TAG = TAG_HOME;
//            loadHomeFragment();
//        }












        mDatabaseHelper = new DatabaseHelper(this);


//        Task task1 = new Task("test1.2");
//        Task task2 = new Task("test1.2");
//        Task task3 = new Task("test2.1");
//        Task task4 = new Task("test2.2");
//        mDatabaseHelper.createTask(task1, mDatabaseHelper.getList("sport").getId());
//        mDatabaseHelper.createTask(task2, mDatabaseHelper.getList("sport").getId());
//        mDatabaseHelper.createTask(task3, mDatabaseHelper.getList("job").getId());
//        mDatabaseHelper.createTask(task4, mDatabaseHelper.getList("job").getId());

//        Lists list = new Lists("sport");
//        mDatabaseHelper.deleteList(mDatabaseHelper.getList("sport"));
//        mDatabaseHelper.createList(list);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Today");
        setSupportActionBar(toolbar);


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        addItemsRunTime(navigationView);



//        intent2 = new Intent(this, AddTaskActivity.class);
        intent2 = new Intent(this, AddTaskNew.class);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent2);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


//        loadNavHeader();

//        setUpNavigationView();
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_TODAY;
            loadHomeFragment();
        }


//        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        mDatabaseHelper.closeDB();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_add) {
//            intent = new Intent(this, AddListActivity.class);
            intent = new Intent(this, AddListNewActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_inbox) {
            navItemIndex = 2;
            CURRENT_TAG = TAG_INBOX;
        } else if (id == R.id.nav_today) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_TODAY;

        } else if (id == R.id.nav_week) {
            navItemIndex = 1;
            CURRENT_TAG = TAG_WEEK;
        } else if (id == R.id.nav_setting) {
            intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_rate) {
//            Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
            Uri uri = Uri.parse("market://details?id=com.wunderkinder.wunderlistandroid");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
//                        Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
                        Uri.parse("http://play.google.com/store/apps/details?id=com.wunderkinder.wunderlistandroid")));
            }
        } else if (id == R.id.nav_share) {
            intent = new Intent(this, ShareActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_feedback) {
            intent = new Intent(this, FeedbackActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_help) {
            intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
        }
        else {

            navItemIndex = 0;

            mDatabaseHelper = new DatabaseHelper(this);

            String s = item.getTitle().toString();
            Lists list = mDatabaseHelper.getList(s);
            int i = list.getId();

            Intent editScreenIntent = new Intent(MainActivity.this, ListViewActivity.class);
            editScreenIntent.putExtra("id", i);
            startActivity(editScreenIntent);
        }

        loadHomeFragment();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        item.setCheckable(true);

        return true;
    }

//    private void populateListView() {
//
//        mDatabaseHelper = new DatabaseHelper(this);
//
//        ArrayList<Lists> lists = new ArrayList<>();
//        lists.addAll(mDatabaseHelper.getAllLists());
//
//        ArrayList<String> Slists = new ArrayList<>();
//        for (Lists S: lists) {
//            Slists.add(S.getName());
//        }
//
////        final Menu menu = navigationView.getMenu();
////
////        for (String S: Slists) {
////            menu.add(S);
////        }
////        for (int i = 0, count = navigationView.getChildCount(); i < count; i++) {
////            final View child = navigationView.getChildAt(i);
////            if (child != null && child instanceof ListView) {
////                final ListView menuView = (ListView) child;
////                final HeaderViewListAdapter adapter = (HeaderViewListAdapter) menuView.getAdapter();
////                final BaseAdapter wrapped = (BaseAdapter) adapter.getWrappedAdapter();
////                wrapped.notifyDataSetChanged();
////            }
////        }
//
////        NavigationMenu adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Slists);
////        navigationView.setAdapter(adapter);
//    }



//    private void initNavigationDrawer() {
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
//
//        if (mNavigationView != null) {
//            setupDrawerContent(mNavigationView);
//        }
//    }

//    /**
//     * In case if you require to handle drawer open and close states
//     */
//    private void setupActionBarDrawerToogle() {
//
//        mDrawerToggle = new ActionBarDrawerToggle(
//                this,                  /* host Activity */
//                mDrawerLayout,         /* DrawerLayout object */
//                R.string.drawer_open,  /* "open drawer" description */
//                R.string.drawer_close  /* "close drawer" description */
//        ) {
//
//            /**
//             * Called when a drawer has settled in a completely closed state.
//             */
//            public void onDrawerClosed(View view) {
//                Snackbar.make(view, R.string.drawer_close, Snackbar.LENGTH_SHORT).show();
//            }
//
//            /**
//             * Called when a drawer has settled in a completely open state.
//             */
//            public void onDrawerOpened(View drawerView) {
//                Snackbar.make(drawerView, R.string.drawer_open, Snackbar.LENGTH_SHORT).show();
//            }
//        };
//        mDrawerLayout.setDrawerListener(mDrawerToggle);
//
//    }

//    private void setupDrawerContent(NavigationView navigationView) {
//
//        addItemsRunTime(navigationView);
//
//        navigationView.setNavigationItemSelectedListener(
//                new NavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(MenuItem menuItem) {
//                        menuItem.setChecked(true);
//                        mDrawerLayout.closeDrawers();
//                        return true;
//                    }
//                });
//    }

    private void addItemsRunTime(NavigationView navigationView) {
        mDatabaseHelper = new DatabaseHelper(this);

        ArrayList<Lists> lists = new ArrayList<>();
        lists.addAll(mDatabaseHelper.getAllLists());

        final Menu menu = navigationView.getMenu();


//        ListView listView = (ListView)findViewById(R.id.listView);
//
//
//        ArrayList<String> listData = new ArrayList<>();
//        for (int i = 0; i < lists.size(); i++) {
//            listData.add(lists.get(i).getName());
//        }
//        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
//        mAdapter.setNotifyOnChange(true);
//        listView.setAdapter(mAdapter);



        for (int i = 0; i < lists.size(); i++) {
            menu.add(R.id.group2,Menu.NONE,4,lists.get(i).getName()).setIcon(R.drawable.ic_list_black_24dp);
        }

//        for (int i = 0, count = navigationView.getChildCount(); i < count; i++) {
//            final View child = navigationView.getChildAt(i);
//            if (child != null && child instanceof ListView) {
//                final ListView menuView = (ListView) child;
//                final HeaderViewListAdapter adapter = (HeaderViewListAdapter) menuView.getAdapter();
//                final BaseAdapter wrapped = (BaseAdapter) adapter.getWrappedAdapter();
//                wrapped.notifyDataSetChanged();
//            }
//        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

//        TodayFragment todayFragment = (TodayFragment) getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
//        setListener(todayFragment);
//        listener.populateListView(1);
//        todayFragment.populateListView(1);
//        ((TodayFragment) todayFragment).populateListView(1);


        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                android.support.v4.app.Fragment fragment = getHomeFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();




//                TodayFragment todayFragment = (TodayFragment) getSupportFragmentManager().findFragmentById(R.id.listView);
//                todayFragment.populateListView(mDatabaseHelper.getList("sport").getId());
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private android.support.v4.app.Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                TodayFragment todayFragment = new TodayFragment();
                return todayFragment;
            case 1:
                WeekFragment weekFragment = new WeekFragment();
                return weekFragment;
            case 2:
                InboxFragment inboxFragment = new InboxFragment();
                return inboxFragment;
            default:
                return new TodayFragment();
        }
    }


    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }


    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

//    private void setUpNavigationView() {
//        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//
//            // This method will trigger on item Click of navigation menu
//            @Override
//            public boolean onNavigationItemSelected(MenuItem menuItem) {
//
//                //Check to see which item was being clicked and perform appropriate action
//                switch (menuItem.getItemId()) {
//                    //Replacing the main content with ContentFragment Which is our Inbox View;
//                    case R.id.nav_today:
//                        navItemIndex = 0;
//                        CURRENT_TAG = TAG_TODAY;
//                        break;
////                    case R.id.nav_today:
////                        navItemIndex = 1;
////                        CURRENT_TAG = TAG_PHOTOS;
////                        break;
//                    case R.id.nav_week:
//                        navItemIndex = 1;
//                        CURRENT_TAG = TAG_WEEK;
//                        break;
//                    case R.id.nav_inbox:
//                        navItemIndex = 2;
//                        CURRENT_TAG = TAG_INBOX;
//                        break;
////                    case R.id.nav_settings:
////                        navItemIndex = 4;
////                        CURRENT_TAG = TAG_SETTINGS;
////                        break;
////                    case R.id.nav_about_us:
//                        // launch new intent instead of loading fragment
////                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
////                        drawer.closeDrawers();
////                        return true;
////                    case R.id.nav_privacy_policy:
//                        // launch new intent instead of loading fragment
////                        startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
////                        drawer.closeDrawers();
////                        return true;
//                    default:
//                        navItemIndex = 0;
//                }
//
//                //Checking if the item is in checked state or not, if not make it in checked state
//                if (menuItem.isChecked()) {
//                    menuItem.setChecked(false);
//                } else {
//                    menuItem.setChecked(true);
//                }
//                menuItem.setChecked(true);
//
//                loadHomeFragment();
//
//                return true;
//            }
//        });
//
//
////        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {
////
////            @Override
////            public void onDrawerClosed(View drawerView) {
////                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
////                super.onDrawerClosed(drawerView);
////            }
////
////            @Override
////            public void onDrawerOpened(View drawerView) {
////                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
////                super.onDrawerOpened(drawerView);
////            }
////        };
//
//        //Setting the actionbarToggle to drawer layout
////        drawer.setDrawerListener(actionBarDrawerToggle);
//
//        //calling sync state is necessary or else your hamburger icon wont show up
////        actionBarDrawerToggle.syncState();
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_logout) {
//            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
//            return true;
//        }
//
//        // user is in notifications fragment
//        // and selected 'Mark all as Read'
//        if (id == R.id.action_mark_all_read) {
//            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
//        }
//
//        // user is in notifications fragment
//        // and selected 'Clear All'
//        if (id == R.id.action_clear_notifications) {
//            Toast.makeText(getApplicationContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    private void toggleFab() {
        if (navItemIndex==0 || navItemIndex==1 || navItemIndex==2)
            fab.show();
        else
            fab.hide();
    }



//    private void loadNavHeader() {
        // name, website
//        txtName.setText("Ravi Tamada");
//        txtWebsite.setText("www.androidhive.info");

        // loading header background image
//        Glide.with(this).load(urlNavHeaderBg)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imgNavHeaderBg);

        // Loading profile image
//        Glide.with(this).load(urlProfileImg)
//                .crossFade()
//                .thumbnail(0.5f)
//                .bitmapTransform(new CircleTransform(this))
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imgProfile);

        // showing dot next to notifications label
//        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
//    }


//    @Override
    protected void onResume() {
        super.onResume();

        final Menu menu = navigationView.getMenu();

        menu.removeGroup(R.id.group2);


//        for (int i = 0, count = navigationView.getChildCount(); i < count; i++) {
//            final View child = navigationView.getChildAt(i);
//            if (child != null && child instanceof ListView) {
//                final ListView menuView = (ListView) child;
//                final HeaderViewListAdapter adapter = (HeaderViewListAdapter) menuView.getAdapter();
//                final BaseAdapter wrapped = (BaseAdapter) adapter.getWrappedAdapter();
//                wrapped.notifyDataSetChanged();
//
//            }
//        }


        addItemsRunTime(navigationView);

        loadHomeFragment();
    }
}



