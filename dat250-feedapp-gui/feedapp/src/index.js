import "bootstrap/dist/css/bootstrap.css";
import React from "react";
import ReactDOM from "react-dom";
import PublicPollOverview from "./components/PublicPollOverview";
import NavBar from "./components/NavBar";

class Feedapp extends React.Component {
  render() {
    return (
      <div>
        <div>
          <NavBar />
        </div>
        <div>
          <PublicPollOverview />
        </div>
      </div>
    );
  }
}

ReactDOM.render(<Feedapp />, document.getElementById("root"));
