class Votes {
  int yes;
  int no;

  Votes({this.yes, this.no});

  Votes.fromJson(Map<String, dynamic> json) {
    yes = json['yes'];
    no = json['no'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['yes'] = this.yes;
    data['no'] = this.no;
    return data;
  }
}