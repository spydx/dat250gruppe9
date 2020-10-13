import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import Home from "./pages/Home";
import LoginPage from "./pages/LoginPage";
import ResultPage from "./pages/ResultPage";
import "bootstrap/dist/css/bootstrap.css";

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

ReactDOM.render(<Feedapp />, document.getElementById("root"));
