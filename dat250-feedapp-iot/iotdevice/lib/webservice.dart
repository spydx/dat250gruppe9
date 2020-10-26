import 'package:http/http.dart' as http;


class WebServices {
  static const postUrl = "http://localhost/api/iot/vote";

  // TODO: Missing json object
  Future<String> postVotes() async {
    var url = postUrl;
    var response = await http.post(url);

    if(response.statusCode == 200) {
      return response.body;
    } else {
      throw Exception('Unable to post data: ${response.statusCode}');
    }
  }

}