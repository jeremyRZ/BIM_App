import 'package:flutter/material.dart';
import 'package:flutter_base_ui/flutter_base_ui.dart';
import 'package:flutter_common_util/flutter_common_util.dart';
import 'package:open_git/bean/home_bean.dart';
import 'package:open_git/bean/release_asset_bean.dart';
import 'package:open_git/bean/release_bean.dart';
import 'package:open_git/manager/home_manager.dart';
import 'package:open_git/manager/red_point_manager.dart';
import 'package:open_git/manager/repos_manager.dart';
import 'package:open_git/util/update_util.dart';
import 'package:package_info/package_info.dart';

class HomeBloc extends BaseBloc<LoadingBean<HomeBean>> {
  static final String TAG = "HomeBloc";

  HomeBloc() {
    bean = LoadingBean(isLoading: false, data: HomeBean());
  }

  void initData(BuildContext context) async {
    onReload();

    _checkUpgrade(context);
  }

  @override
  void onReload() async {
    showLoading();
    await getData();
    hideLoading();
  }

  @override
  Future getData() async {
    await _fetchHomeBanner();
    await _fetchHomeList();
  }

  _fetchHomeBanner() async {
    LogUtil.v('_fetchHomeBanner', tag: TAG);
    var result = await HomeManager.instance.getHomeBanner();
    if (result != null) {
      bean.isError = false;
      bean.data.banner = result;
    } else {
      bean.isError = true;
    }
  }

  _fetchHomeList() async {
    LogUtil.v('_fetchHomeList', tag: TAG);
    var result = await HomeManager.instance.getHomeItem();
    bean.data.itemBean = result;
  }

  void _checkUpgrade(BuildContext context) {
    TimerUtil.delay(200, (_) {
      ReposManager.instance
          .getReposReleases('Yuzopro', 'OpenGit_Flutter')
          .then((result) {
        if (result != null && result.length > 0) {
          ReleaseBean bean = result[0];
          if (bean != null) {
            PackageInfo.fromPlatform().then((info) {
              if (info != null) {
                String version = info.version;
                String serverVersion = bean.name;
                int compare = UpdateUtil.compareVersion(version, serverVersion);
                if (compare == -1) {
                  RedPointManager.instance.isUpgrade = true;
                  String url = "";
                  if (bean.assets != null && bean.assets.length > 0) {
                    ReleaseAssetBean assetBean = bean.assets[0];
                    if (assetBean != null) {
                      url = assetBean.downloadUrl;
                    }
                  }
                  UpdateUtil.showUpdateDialog(
                      context, serverVersion, bean.body, url);
                }
              }
            });
          }
        }
      }).catchError((_) {});
    });
  }
}
