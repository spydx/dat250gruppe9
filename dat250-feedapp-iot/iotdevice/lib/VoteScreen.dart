
import 'package:provider/provider.dart';
import 'package:flutter/material.dart';
import 'package:iotdevice/VoteState.dart';

import 'Statics.dart';


class VoteScreen extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    final voteState = Provider.of<VoteState>(context);
    return Container(
          padding: EdgeInsets.all(20),
          child: Column(
            children: [
              Flexible(
                flex: 2,
                child: Image(
                  image: AssetImage(Statics.LOGO),
                ),
              ),
              Spacer(),
              Flexible(
                flex: 2,
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                      SizedBox(
                        width: Statics.BUTTONW,
                        height: Statics.BUTTONH,
                        child: RaisedButton(
                          child: Text("Yes"),
                          onPressed: () => voteState.voteYes(),
                          color: Colors.green,
                        ),
                      ),
                      SizedBox(
                        width: Statics.BUTTONW,
                        height: Statics.BUTTONH,
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
                      width: Statics.BUTTONW,
                      height: Statics.BUTTONH,
                      child: RaisedButton(
                        child: Text("Reset Device"),
                        onPressed: () => voteState.reset(),
                      ),
                    ),
                    SizedBox(
                      width: Statics.BUTTONW,
                      height: Statics.BUTTONH,
                      child: RaisedButton(
                        child: Text("Submit"),
                        onPressed: () => voteState.send(),
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
    );
  }
}