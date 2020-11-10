# iotdevice

A voting "device" for FeedApp

Desined for iOS/Andriod and Web

To run for web
```
> flutter channel master
> flutter upgrade
> flutter config --enable-web
> flutter run -d chrome
```

Remeber to turn of CORS when running this on localhost and if you are running the API on localhost.

Example for Chrome Beta:
```
> open -a Google\ Chrome\ Beta.app --args --disable-web-security --user-data-dir={writabledir}
```
{writabledir} is a random dir that you have write access to so you can store the disabled cors profile.


To run for Android device
```
> flutter run -d android 
```

To run for iOS
```
> flutter run -d ios
```



## Resources

This project is a starting point for a Flutter application.

A few resources to get you started if this is your first Flutter project:

- [Lab: Write your first Flutter app](https://flutter.dev/docs/get-started/codelab)
- [Cookbook: Useful Flutter samples](https://flutter.dev/docs/cookbook)

For help getting started with Flutter, view our
[online documentation](https://flutter.dev/docs), which offers tutorials,
samples, guidance on mobile development, and a full API reference.
