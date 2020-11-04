import React from "react";
import PublicPollOverview from "../components/PublicPollOverview";
import "bootstrap/dist/css/bootstrap.css";
import NavBar from "../components/NavBar";

class Home extends React.Component {
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

export default Home;
