// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'issue_bean.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

IssueBean _$IssueBeanFromJson(Map<String, dynamic> json) {
  return IssueBean(
    json['id'] as int,
    json['number'] as int,
    json['title'] as String,
    json['state'] as String,
    json['locked'] as bool,
    json['comments'] as int,
    json['created_at'] == null
        ? null
        : DateTime.parse(json['created_at'] as String),
    json['updated_at'] == null
        ? null
        : DateTime.parse(json['updated_at'] as String),
    json['closed_at'] == null
        ? null
        : DateTime.parse(json['closed_at'] as String),
    json['body'] as String,
    json['body_html'] as String,
    json['user'] == null
        ? null
        : UserBean.fromJson(json['user'] as Map<String, dynamic>),
    json['repository_url'] as String,
    json['html_url'] as String,
    json['closed_by'] == null
        ? null
        : UserBean.fromJson(json['closed_by'] as Map<String, dynamic>),
    json['reactions'] == null
        ? null
        : ReactionBean.fromJson(json['reactions'] as Map<String, dynamic>),
    (json['labels'] as List)
        ?.map((e) =>
            e == null ? null : Labels.fromJson(e as Map<String, dynamic>))
        ?.toList(),
  );
}

Map<String, dynamic> _$IssueBeanToJson(IssueBean instance) => <String, dynamic>{
      'id': instance.id,
      'number': instance.number,
      'title': instance.title,
      'state': instance.state,
      'locked': instance.locked,
      'comments': instance.commentNum,
      'created_at': instance.createdAt?.toIso8601String(),
      'updated_at': instance.updatedAt?.toIso8601String(),
      'closed_at': instance.closedAt?.toIso8601String(),
      'body': instance.body,
      'body_html': instance.bodyHtml,
      'user': instance.user,
      'repository_url': instance.repoUrl,
      'html_url': instance.htmlUrl,
      'closed_by': instance.closeBy,
      'reactions': instance.reaction,
      'labels': instance.labels,
    };
