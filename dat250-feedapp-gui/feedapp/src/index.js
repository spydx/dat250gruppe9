import React from "react";
import ReactDOM from "react-dom";

import "bootstrap/dist/css/bootstrap.css";
import { Provider } from "react-redux";
import { rootReducers } from "./reducers";
import { loadState } from './localStorage';
import { createStore } from "redux";
import Feedapp from './FeedApp'

const persistedState = loadState();
const store = createStore(rootReducers, persistedState)
window.store = store;
ReactDOM.render(
  <Provider store={store}>
      <Feedapp />  
  </Provider>
  ,
  document.getElementById("root"));
