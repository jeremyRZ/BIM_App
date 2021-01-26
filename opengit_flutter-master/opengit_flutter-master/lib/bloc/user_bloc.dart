import 'package:flutter/widgets.dart';
import 'package:flutter_base_ui/bloc/base_list_bloc.dart';
import 'package:open_git/bean/user_bean.dart';
import 'package:open_git/common/config.dart';
import 'package:flutter_common_util/flutter_common_util.dart';

abstract class UserBloc extends BaseListBloc<UserBean> {
  static final String TAG = "FollowBloc";

  final String userName;

  UserBloc(this.userName);

  fetchList(int page);

  void initData(BuildContext context) async {
    onReload();
  }

  @override
  void onReload() async {
    showLoading();
    await _fetchUserList();
    hideLoading();
  }

  @override
  Future getData() async {
    await _fetchUserList();
  }

  Future _fetchUserList() async {
    LogUtil.v('_fetchUserList', tag: TAG);
    try {
      var result = await fetchList(page);
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
    } catch (_) {
      if (page > 1) {
        page--;
      }
    }
  }
}
