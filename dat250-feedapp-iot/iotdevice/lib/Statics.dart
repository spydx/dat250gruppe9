import "dart:io";

class Statics {
  static Map<String, String> env = Platform.environment;

  static String API_URL = env.containsKey("API") ? env["API"] : "http://localhost:8080/api/";
  static String DEVICE_AUTH = API_URL +"devices/authenticate";
  static String DEVICE_ROOT = API_URL + "devices/";
  static const LOGO = "assets/pollhubblue.png";
  static double BUTTONW = 150;
  static double BUTTONH = 75;
  static const FEEDAPP_TITLE = "PollHub";
}
