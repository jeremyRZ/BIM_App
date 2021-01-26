import 'package:flutter/widgets.dart';
import 'package:flutter_base_ui/flutter_base_ui.dart';
import 'package:open_git/bean/org_bean.dart';
import 'package:open_git/common/config.dart';
import 'package:open_git/manager/user_manager.dart';

class OrgBloc extends BaseListBloc<OrgBean> {
  final String name;

  OrgBloc(this.name);

  @override
  void initData(BuildContext context) {
    onReload();
  }

  @override
  Future getData() async {
    await _fetchProfile();
  }

  @override
  void onReload() async {
    showLoading();
    await _fetchProfile();
    hideLoading();
  }

  Future _fetchProfile() async {
    final result = await UserManager.instance.getOrgs(name, page);
    if (bean.data == null) {
      bean.data = List();
    }
    if (page == 1) {
      bean.data.clear();
    }

    noMore = true;
    if (result != null) {
      bean.isError = false;
      noMore = result.length != Config.PAGE_SIZE;
      bean.data.addAll(result);
    } else {
      if (bean.data.length > 0) {
        bean.isError = false;
        noMore = false;
      } else {
        bean.isError = true;
      }
      if (page > 1) {
        page--;
      }
    }
  }
}
