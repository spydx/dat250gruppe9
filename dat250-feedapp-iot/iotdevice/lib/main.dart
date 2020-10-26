import 'package:flutter/material.dart';
import 'package:iotdevice/VoteState.dart';
import 'package:provider/provider.dart';
import 'package:iotdevice/VoteScreen.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
      create: (_) => VoteState(),
      lazy: false,
      child: MaterialApp(
        debugShowCheckedModeBanner: false,
        home: VoteScreen(),
      ),
    );
  }
}

