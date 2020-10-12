import React from "react";
import PollFromAPI from "./PollComponents/PollFromAPI";
import Card from "react-bootstrap/Card";
import PollList from "./PollComponents/PollList";

class PublicPollOverview extends React.Component {
  render() {
    return (
      <div>
        <div>
          <h1 style={{ textAlign: "center" }}>Public Poll Overview</h1>
        </div>
        <div>
          <PollList />
        </div>

        <h1 style={{ textAlign: "center" }}>Poll Overview</h1>
        <div class="container">
          <div class="row">
            <div class="col-sm">
              <h1 style={{ textAlign: "center" }}>All polls</h1>
              <PollFromAPI />
            </div>
            <div class="col-sm">
              <h1 style={{ textAlign: "center" }}>Created polls</h1>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default PublicPollOverview;
