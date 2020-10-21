import React from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Home from "./pages/Home";
import LoginPage from "./pages/LoginPage";
import ResultPage from "./pages/ResultPage";

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


export default Feedapp;