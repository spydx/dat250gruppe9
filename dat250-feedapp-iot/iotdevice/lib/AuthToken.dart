class AuthToken {
  String token;
  String tokenType;
  String profile;

  AuthToken({this.token, this.tokenType, this.profile});

  AuthToken.fromJson(Map<String, dynamic> json) {
    token = json['token'];
    tokenType = json['tokenType'];
    profile = json['profile'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['token'] = this.token;
    data['tokenType'] = this.tokenType;
    data['profile'] = this.profile;
    return data;
  }
}