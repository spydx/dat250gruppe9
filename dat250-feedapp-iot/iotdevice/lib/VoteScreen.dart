
import 'package:provider/provider.dart';
import 'package:flutter/material.dart';
import 'package:iotdevice/VoteState.dart';


class VoteScreen extends StatelessWidget {
  static const LOGO = "assets/feedapp-logo.png";
  static double BUTTONW = 150;
  static double BUTTONH = 75;

  @override
  Widget build(BuildContext context) {
    final voteState = Provider.of<VoteState>(context);
    return Scaffold(
      appBar: AppBar(
        title: Text("FeedApp"),
      ),
      body: SafeArea(
        child: Container(
          padding: EdgeInsets.all(20),
          child: Column(
            children: [
              Flexible(
                flex: 2,
                child: Image(
                  image: AssetImage(LOGO),
                ),
              ),
              Spacer(),
              Flexible(
                flex: 2,
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                      SizedBox(
                        width: BUTTONW,
                        height: BUTTONH,
                        child: RaisedButton(
                          child: Text("Yes"),
                          onPressed: () => voteState.voteYes(),
                          color: Colors.green,
                        ),
                      ),
                      SizedBox(
                        width: BUTTONW,
                        height: BUTTONH,
                        child: RaisedButton(
                          child: Text("No"),
                          onPressed: () => voteState.voteNo(),
                          color: Colors.red,
                        ),
                      ),
                      ],
                ),
              ),
              Spacer(),
              Flexible(
                flex: 2,
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    SizedBox(
                      width: BUTTONW,
                      height: BUTTONH,
                      child: RaisedButton(
                        child: Text("Reset Device"),
                        onPressed: () => voteState.reset(),
                      ),
                    ),
                    SizedBox(
                      width: BUTTONW,
                      height: BUTTONH,
                      child: RaisedButton(
                        child: Text("Submit"),
                        onPressed: () => print("submitting"),
                      ),
                    ),
                  ],
                ),
              ),
              Spacer(),
              Flexible(
                flex: 1,
                child:Column(
                  children: [
                    Text(
                      "Status:",
                        style: TextStyle(fontWeight: FontWeight.bold)
                    ),
                    Text(
                      voteState.getStatus()
                    ),
                  ],
                ),
              )
            ],
          ),
        ),
      ),
    );
  }
}