import React from "react";
import PollFromAPI from "./PollComponents/PollFromAPI";

class PublicPollOverview extends React.Component {
  render() {
    return (
      <div>
        {/*
        <div>
          <h1 style={{ textAlign: "center" }}>Public Poll Overview</h1>
        </div>
        <div> <PollList /> </div>
*/}
        {/*
        <h1 class="display-3" style={{ textAlign: "center" }}>
          Poll Overview
        </h1>
        */}
        <div className="container">
          <div className="row">
            <div className="col-sm">
              <h1 className="display-4" style={{ textAlign: "center" }}>
                All polls
              </h1>
              <PollFromAPI url="polls/" />
            </div>
            <div className="col-sm">
              <h1 className="display-4" style={{ textAlign: "center" }}>
                Created polls
              </h1>
              {/*<PollFromAPI url="users/polls/1" />*/}
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default PublicPollOverview;
