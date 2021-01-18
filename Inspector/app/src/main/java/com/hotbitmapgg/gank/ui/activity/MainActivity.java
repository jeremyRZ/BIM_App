package com.hotbitmapgg.gank.ui.activity;

import br.com.mauker.materialsearchview.MaterialSearchView;
import butterknife.Bind;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.hotbitmapgg.gank.base.RxBaseActivity;
import com.hotbitmapgg.gank.config.ConstantUtil;
import com.hotbitmapgg.gank.model.GitHubUserInfo;
import com.hotbitmapgg.gank.rx.RxBus;
import com.hotbitmapgg.gank.ui.fragment.HomeFragment;
import com.hotbitmapgg.gank.ui.fragment.RxjavaAndNotesFragment;
import com.hotbitmapgg.gank.utils.ACache;
import com.hotbitmapgg.gank.utils.LogUtil;
import com.hotbitmapgg.gank.utils.SnackbarUtil;
import com.hotbitmapgg.gank.widget.CircleImageView;
import com.hotbitmapgg.studyproject.R;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import rx.Subscription;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends RxBaseActivity implements View.OnClickListener {

  @Bind(R.id.toolbar)
  Toolbar mToolbar;

  @Bind(R.id.drawer_layout)
  DrawerLayout mDrawerLayout;

  @Bind(R.id.nav_view)
  NavigationView mNavigationView;

  @Bind(R.id.search_view)
  MaterialSearchView mSearchView;

  @Bind(R.id.fab)
  FloatingActionButton mFloatingActionButton;

  @Bind(R.id.coor_layout)
  CoordinatorLayout mCoordinatorLayout;

  CircleImageView mUserAvatar;

  TextView mUserName;

  TextView mUserBio;

  private HomeFragment homeFragment;

  private RxjavaAndNotesFragment rxjavaAndNotesFragment;

  private Fragment[] fragments;

  private int currentTabIndex;

  private int index;

  private boolean isLogin = false;

  private Subscription subscribe;

  private long exitTime;

  // all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
  private List<String> types = Arrays.asList("all", "Android", "iOS", "休息视频", "福利", "拓展资源", "前端",
      "瞎推荐", "App");


  @Override
  public int getLayoutId() {

    return R.layout.activity_main;
  }


  @Override
  public void initViews(Bundle savedInstanceState) {

    if (mNavigationView != null) {
      setUpDrawerContent(mNavigationView);
    }

    homeFragment = HomeFragment.newInstance();
    rxjavaAndNotesFragment = RxjavaAndNotesFragment.newInstance();
    fragments = new Fragment[] { homeFragment, rxjavaAndNotesFragment };
    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.content, homeFragment)
        .commit();
    mFloatingActionButton.setVisibility(View.VISIBLE);

    subscribe = RxBus.getInstance()
        .toObserverable(String.class)
        .subscribe(s -> {

          if (!TextUtils.isEmpty(s)) {
            LogUtil.all(s);
            if (s.equals(ConstantUtil.CODE_SUCCESS)) {
              // 登录成功
              isLogin = true;
              setUserInfo();
            } else if (s.equals(ConstantUtil.CODE_LOGOUT)) {
              //退出登录
              isLogin = false;
              clearUserInfo();
            }
          }
        }, throwable -> {

          LogUtil.all("用户登录更新失败");
        });

    initSearchView();
  }


  private void initSearchView() {

    mSearchView.setTintColor(getResources().getColor(R.color.bg_color));
    mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {

      @Override
      public boolean onQueryTextSubmit(String query) {

        search(query);
        return false;
      }


      @Override
      public boolean onQueryTextChange(String newText) {

        return false;
      }
    });

    mSearchView.setSearchViewListener(new MaterialSearchView.SearchViewListener() {

      @Override
      public void onSearchViewOpened() {

        mFloatingActionButton.setVisibility(View.GONE);
      }


      @Override
      public void onSearchViewClosed() {

        mFloatingActionButton.setVisibility(View.VISIBLE);
      }
    });

    mSearchView.setOnItemClickListener((parent, view, position, id) -> {

      TextView tv = (TextView) view.findViewById(R.id.tv_str);

      if (tv != null) {
        mSearchView.setQuery(tv.getText().toString(), false);
      }
    });
  }


  private void search(final String query) {

    if (!types.contains(query)) {
      SnackbarUtil.showMessage(mCoordinatorLayout, "请输入正确的干货类型");
      return;
    }

    mSearchView.postDelayed(() -> SearchGankActivity.luancher(MainActivity.this, query), 500);
  }


  private void clearUserInfo() {

    mUserAvatar.setImageResource(R.drawable.ic_slide_menu_avatar_no_login);
    mUserName.setText("立即登录");
    mUserBio.setText("使用你的GitHub账号进行登录");
  }


  @Override
  public void initToolBar() {

    mToolbar.setTitle("Gank.io");
    setSupportActionBar(mToolbar);
    ActionBar mActionBar = getSupportActionBar();
    if (mActionBar != null) {
      mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,
        mDrawerLayout,
        mToolbar,
        R.string.app_name,
        R.string.app_name);

    mDrawerToggle.syncState();
    mDrawerLayout.addDrawerListener(mDrawerToggle);
  }


  @Override public void loadData() {

  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    switch (item.getItemId()) {
      case android.R.id.home:
        mDrawerLayout.openDrawer(GravityCompat.START);
        return true;
      case R.id.action_search:
        mSearchView.openSearch();
        return true;

      case R.id.action_today:
        //今日干货
        startTodayGank();
        return true;

      case R.id.action_today_github:
        WebActivity.start(MainActivity.this, "https://github.com/trending",
            "GitHub Today's popular project");
        return true;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }


  private void startTodayGank() {
    // 获取今日的时间
    String timeStr = SimpleDateFormat.getDateInstance().format(new Date());
    String replaceStr = timeStr.replace("年", "/").replace("月", "/").replace("日", "/");
    String[] splitStrs = replaceStr.split("/");
    int year = Integer.parseInt(splitStrs[0]);
    int month = Integer.parseInt(splitStrs[1]);
    int day = Integer.parseInt(splitStrs[2]);

    ToDayGankActivity.launch(MainActivity.this, year, month, day);
  }


  private void setUpDrawerContent(NavigationView navigationView) {

    View headerView = navigationView.getHeaderView(0);
    mUserAvatar = (CircleImageView) headerView.findViewById(R.id.github_user_avatar);
    mUserName = (TextView) headerView.findViewById(R.id.github_user_name);
    mUserBio = (TextView) headerView.findViewById(R.id.github_user_bio);
    mUserAvatar.setOnClickListener(this);

    setUserInfo();

    navigationView.setNavigationItemSelectedListener(
        menuItem -> {

          switch (menuItem.getItemId()) {
            case R.id.nav_home:
              changNavItem(menuItem, 0, "Gank.io", true);
              return true;

            case R.id.nav_my_focus:
              changNavItem(menuItem, 1, "Notes", false);
              return true;

            case R.id.nav_about:
              //关于我
              startActivity(new Intent(MainActivity.this, HotBitmapGGActivity.class));
              break;

            case R.id.nav_about_app:
              // 关于App
              startActivity(new Intent(MainActivity.this, AboutActivity.class));
              break;

            default:
              break;
          }
          return true;
        });
  }


  public void addFragment(Fragment fragment) {

    FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
    trx.hide(fragments[currentTabIndex]);
    if (!fragments[index].isAdded()) {
      trx.add(R.id.content, fragments[index]);
    }
    trx.show(fragments[index]).commit();
    currentTabIndex = index;
  }


  public void changNavItem(MenuItem menuItem, int pos, String itemTitle, boolean isShowFab) {

    index = pos;
    addFragment(fragments[pos]);
    menuItem.setChecked(true);
    mToolbar.setTitle(itemTitle);
    mDrawerLayout.closeDrawers();
    if (isShowFab) {
      mFloatingActionButton.setVisibility(View.VISIBLE);
    } else {
      mFloatingActionButton.setVisibility(View.GONE);
    }
  }


  @OnClick(R.id.fab)
  void startPostGank() {

    startActivity(new Intent(MainActivity.this, SubmitGankActivity.class));
  }


  @Override
  public void onClick(View v) {

    if (v.getId() == R.id.github_user_avatar) {
      if (isLogin) {
        startActivity(new Intent(MainActivity.this, GitHubUserActivity.class));
      } else {
        LoginGitHubActivity.launch(MainActivity.this, ConstantUtil.GITHUB_LOGIN_URL);
      }
    }
  }


  public void setUserInfo() {

    GitHubUserInfo mUserInfo = (GitHubUserInfo) ACache.get(MainActivity.this)
        .getAsObject(ConstantUtil.CACHE_USER_KEY);

    if (mUserInfo != null) {
      isLogin = true;
      Glide.with(MainActivity.this)
          .load(mUserInfo.avatarUrl)
          .asBitmap()
          .placeholder(R.drawable.ic_slide_menu_avatar_no_login)
          .into(new BitmapImageViewTarget(mUserAvatar) {

            @Override
            protected void setResource(Bitmap resource) {

              mUserAvatar.setImageDrawable(RoundedBitmapDrawableFactory.create
                  (MainActivity.this.getResources(), resource));
            }
          });

      mUserName.setText(mUserInfo.name);
      mUserBio.setText(mUserInfo.bio);
    } else {
      isLogin = false;
    }
  }


  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {

    if (keyCode == KeyEvent.KEYCODE_BACK) {
      if (mSearchView != null) {
        if (mSearchView.isOpen()) {
          mSearchView.closeSearch();
        } else {
          logoutApp();
        }
      } else {
        logoutApp();
      }
    }

    return true;
  }


  private void logoutApp() {

    if (System.currentTimeMillis() - exitTime > 2000) {
      SnackbarUtil.showMessage(mCoordinatorLayout, "再按一次退出AndroidRank");
      exitTime = System.currentTimeMillis();
    } else {
      finish();
    }
  }


  @Override
  protected void onDestroy() {

    super.onDestroy();

    // 取消RxBus的订阅事件
    if (subscribe != null && !subscribe.isUnsubscribed()) {
      subscribe.unsubscribe();
    }

    if (mSearchView != null) {
      if (mSearchView.isOpen()) {
        mSearchView.closeSearch();
        mSearchView.clearAll();
      }
    }
  }
}
