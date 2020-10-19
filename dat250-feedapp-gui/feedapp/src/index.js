import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Home from "./pages/Home";
import LoginPage from "./pages/LoginPage";
import ResultPage from "./pages/ResultPage";
import "bootstrap/dist/css/bootstrap.css";
import { Provider } from "react-redux";
import { store } from "./stateManager";

class Feedapp extends React.Component {


  render() {
    return (
      <div>
        <Router>
          <Switch>
            <Route path="/result">
              <ResultPage />
            </Route>
            <Route path="/login">
              <LoginPage />
            </Route>
            <Route path="/">
              <Home />
            </Route>
          </Switch>
        </Router>
      </div>
    );
  }
}





ReactDOM.render(
  <Provider store={store} >
    <Feedapp />
  </Provider>,
  document.getElementById("root"));
