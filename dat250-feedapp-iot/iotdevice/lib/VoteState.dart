import 'package:flutter/material.dart';
import 'package:iotdevice/Login.dart';
import 'package:iotdevice/webservice.dart';

import 'Device.dart';
import 'Votes.dart';

class VoteState with ChangeNotifier {
  int _voteyes = 0;
  int _voteno = 0;
  WebServices api;
  Device _device = new Device();

  TextEditingController textEditingController = TextEditingController();
  bool isAuthenticated = false;

  VoteState() {
    api = new WebServices();
    _device.name = "iot-test-device";
  }

  Future<void> login() async {
    var pin = textEditingController.text;
    Login l = new Login(name: _device.name, password: pin);
    var auth = await api.auth(l);
    if (auth != null) {
      _device.profile = auth.profile;
      _device.token = auth.token;
      this.isAuthenticated = true;
      textEditingController.text = null;
    } else {
      textEditingController.text = null;
      this.isAuthenticated = false;
    }
    notifyListeners();
  }

  void voteYes() {
    _voteyes++;
    notifyListeners();
  }

  void voteNo() {
    _voteno++;
    notifyListeners();
  }

  void reset() {
    this.isAuthenticated = false;
    _voteno = 0;
    _voteyes = 0;
    _device.token = null;
    _device.profile = null;
    print("[X] Cleard device");
    notifyListeners();
  }

  Future<bool> send() async {
    Votes v = new Votes(yes: _voteyes, no: _voteno);
    var res = await api.postVotes(v, _device);
    print("[> sending votes");
    if (res) {
      reset();
      return true;
    }
    print("[X failed to vote");
    return false;
  }


  String getStatus() {
    if(_voteyes == 0 && _voteno == 0) {
      return "No one has votes";
    }
    var t = _voteyes + _voteno;
    return "Total:\t ${t} \n Yes:\t ${_voteyes} \n No: \t ${_voteno}";

  }

}