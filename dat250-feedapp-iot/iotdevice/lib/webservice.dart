import 'dart:convert';
import 'dart:io';

import 'package:http/http.dart' as http;
import 'package:iotdevice/AuthToken.dart';
import 'package:iotdevice/Statics.dart';

import 'Device.dart';
import 'Login.dart';
import 'Votes.dart';


class WebServices {

  Future<AuthToken> auth(Login login) async {
    var auth = Statics.DEVICE_AUTH;
    final response = await http.post(
        auth,
        headers: { HttpHeaders.contentTypeHeader: "application/json"},
        body: jsonEncode(login)
    );
    if(response.statusCode == 200) {
      final reponseJson = jsonDecode(response.body);
      print(reponseJson);
      return AuthToken.fromJson(reponseJson);
    } else {
      throw Exception('Unable to Auth data ${response.statusCode}');
    }
  }

  Future<bool> postVotes(Votes v, Device d) async {
    var base = Statics.DEVICE_ROOT;
    var posturl = "${base}${d.profile}/vote/";

    print(posturl);
    var response = await http.post(
      posturl,
      headers: {
        HttpHeaders.authorizationHeader: "Bearer " + d.token,
        HttpHeaders.contentTypeHeader: "application/json"
      },
      body: jsonEncode(v)
    );

    if(response.statusCode == 200) {
      return true;
    } else {
      print('Unable to post data: ${response.statusCode}');
      return false;
    }
  }

}