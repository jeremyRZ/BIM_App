import 'package:flutter/widgets.dart';
import 'package:flutter_base_ui/flutter_base_ui.dart';
import 'package:open_git/bloc/user_bloc.dart';
import 'package:open_git/route/navigator_util.dart';
import 'package:open_git/ui/page/profile/user_page.dart';

class FollowingPage extends UserPage {

 @override
  bool isNeedScaffold() {
    return false;
  }

  @override
  String getTitle(BuildContext context) {
    return '我关注的';
  }

  @override
  void openWebView(BuildContext context) {
    UserBloc bloc = BlocProvider.of<UserBloc>(context);
    String url = 'https://github.com/${bloc.userName}?tab=following';
    NavigatorUtil.goWebView(context, bloc.userName, url);
  }

  @override
  String getShareText(BuildContext context) {
    UserBloc bloc = BlocProvider.of<UserBloc>(context);
    String url = 'https://github.com/${bloc.userName}?tab=following';
    return url;
  }
}
