import 'package:open_git/redux/about/about_actions.dart';
import 'package:open_git/redux/about/about_state.dart';
import 'package:open_git/status/status.dart';
import 'package:redux/redux.dart';

const String TAG = "aboutReducer";

final aboutReducer = combineReducers<AboutState>([
  TypedReducer<AboutState, RequestingUpdateAction>(_requestingEvents),
  TypedReducer<AboutState, ReceivedVersionAction>(_receivedVersionEvents),
  TypedReducer<AboutState, ReceivedUpdateAction>(_receivedUpdateEvents),
  TypedReducer<AboutState, ErrorLoadingUpdateAction>(_errorLoadingEvents),
]);

AboutState _requestingEvents(AboutState state, action) {
  return state.copyWith(status: LoadingStatus.loading, version: '');
}

AboutState _receivedVersionEvents(AboutState state, action) {
  return state.copyWith(
    status: LoadingStatus.success,
    version: action.version,
  );
}

AboutState _receivedUpdateEvents(AboutState state, action) {
  return state.copyWith(
    status: LoadingStatus.success,
  );
}

AboutState _errorLoadingEvents(AboutState state, action) {
  return state.copyWith(status: LoadingStatus.error);
}
