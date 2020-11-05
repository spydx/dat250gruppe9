class Login {
  String name;
  String password;

  Login({this.name, this.password});

  Login.fromJson(Map<String, dynamic> json) {
    name = json['name'];
    password = json['password'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['name'] = this.name;
    data['password'] = this.password;
    return data;
  }
}