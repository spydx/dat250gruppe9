import 'package:flutter/material.dart';
import 'package:iotdevice/LoginScreen.dart';
import 'package:iotdevice/VoteState.dart';
import 'package:provider/provider.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
      create: (_) => VoteState(),
      lazy: false,
      child: MaterialApp(
        debugShowCheckedModeBanner: false,
        home: LoginScreen(),
      ),
    );
  }
}

