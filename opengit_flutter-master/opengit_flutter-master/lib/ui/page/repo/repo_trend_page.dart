import 'package:flutter/material.dart';
import 'package:flutter_base_ui/bloc/base_list_stateless_widget.dart';
import 'package:flutter_base_ui/bloc/bloc_provider.dart';
import 'package:flutter_base_ui/flutter_base_ui.dart';
import 'package:open_git/bean/repos_bean.dart';
import 'package:open_git/bloc/repo_trend_bloc.dart';
import 'package:open_git/ui/widget/repos_item_widget.dart';

class RepoTrendPage
    extends BaseListStatelessWidget<Repository, RepoTrendBloc> {
  @override
  String getTitle(BuildContext context) {
    RepoTrendBloc bloc = BlocProvider.of<RepoTrendBloc>(context);
    return bloc.language;
  }

  @override
  Widget builderItem(BuildContext context, Repository item) {
    return ReposItemWidget(item);
  }
}
