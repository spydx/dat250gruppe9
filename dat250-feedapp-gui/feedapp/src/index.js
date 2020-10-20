import React from "react";
import ReactDOM from "react-dom";

import "bootstrap/dist/css/bootstrap.css";
import { Provider, ReactReduxContext } from "react-redux";
import configureStore from "./stateManager";
import Feedapp from './FeedApp'
import { ConnectedRouter } from "connected-react-router";
import history from './history'



const store = configureStore();
window.store = store

ReactDOM.render(
  <Provider store={store}>
    <ConnectedRouter history={history} context={ReactReduxContext}>
      <Feedapp />
    </ConnectedRouter> 
  </Provider>
  ,
  document.getElementById("root"));
