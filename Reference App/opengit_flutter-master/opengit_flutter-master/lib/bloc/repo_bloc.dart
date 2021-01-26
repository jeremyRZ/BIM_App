import 'package:flutter/widgets.dart';
import 'package:flutter_base_ui/bloc/base_list_bloc.dart';
import 'package:flutter_common_util/flutter_common_util.dart';
import 'package:open_git/bean/repos_bean.dart';
import 'package:open_git/common/config.dart';

abstract class RepoBloc extends BaseListBloc<Repository> {
  static final String TAG = "ReposBloc";

  final String userName;

  RepoBloc(this.userName);

  fetchRepos(int page);

  void initData(BuildContext context) {
    onReload();
  }

  @override
  void onReload() async {
    showLoading();
    await _fetchReposList();
    hideLoading();
  }

  @override
  Future getData() async {
    await _fetchReposList();
  }

  Future _fetchReposList() async {
    LogUtil.v('_fetchReposList', tag: TAG);
    try {
      var result = await fetchRepos(page);
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
