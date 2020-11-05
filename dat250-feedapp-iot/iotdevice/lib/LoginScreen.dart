import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:iotdevice/VoteScreen.dart';
import 'package:provider/provider.dart';

import 'Statics.dart';
import 'VoteState.dart';

class LoginScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final voteState = Provider.of<VoteState>(context);

    return Scaffold(
      appBar: AppBar(
        title: Text(Statics.FEEDAPP_TITLE),
      ),
      body: voteState.isAuthenticated ? VoteScreen() :
        SafeArea(
        child: Container(
          padding: EdgeInsets.all(20),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Flexible(
                flex: 1,
                child: Image(
                  image: AssetImage(Statics.LOGO),
                ),
              ),
              //Spacer(),
              Container(
                      width: 100.0,
                      height: 100,
                      child: TextField(
                        key: Key('pin'),
                        obscureText: true,
                        controller: voteState.textEditingController,
                        decoration:
                          InputDecoration(
                              labelText: "Enter device pin"),
                        keyboardType: TextInputType.number,
                        inputFormatters: [
                          FilteringTextInputFormatter.digitsOnly
                        ],)
                ),
              Container(
                child: RaisedButton(

                  padding: EdgeInsets.all(8.0),
                  onPressed: () {
                    voteState.login();
                  },
                  child: Text("Login",
                      style: TextStyle(fontSize: 20.0)),

                ),
              ),


            ],

          ),
        ),
      ),
    );
  }

}