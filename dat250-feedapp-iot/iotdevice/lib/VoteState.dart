import 'package:flutter/material.dart';
import 'package:iotdevice/webservice.dart';

import 'Vote.dart';

class VoteState with ChangeNotifier {
  List<Vote> _votes = new List<Vote>();
  WebServices api;

  VoteState() {
    api = new WebServices();
  }

  void voteYes() {
    var v = new Vote(true);
    _votes.add(v);
    print("voting y");
    notifyListeners();
  }

  void voteNo() {
    var v = new Vote(false);
    _votes.add(v);
    print("voting y");
    notifyListeners();
  }

  void reset() {
    _votes.clear();
    print("Cleard");
    notifyListeners();
  }

  void send() {
    print("sending votes");
    reset();
  }

  _generateJson() {

  }

  String getStatus() {
    if(_votes.isEmpty) {
      return "No one has votes";
    }

    var t = _votes.length;
    var y = 0;
    var n = 0;
    for(Vote v in _votes) {
      if(v.answer)
        y++;
      else
        n++;
    }

    return "Total:\t ${t} \n Yes:\t ${y} \n No: \t ${n}";

  }

}