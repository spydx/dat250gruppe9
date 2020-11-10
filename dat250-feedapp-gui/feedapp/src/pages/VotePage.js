import React from "react";
import NavBar from "../components/NavBar";
import Vote from "../components/Vote";

class VotePage extends React.Component {
  render() {
    return (
      <div>
        <div>
          <NavBar />
        </div>
        <div>
          <div style={{ marginTop: "2rem" }}>
            <Vote id={this.props.props.match.params.id} />
          </div>
        </div>
      </div>
    );
  }
}

export default VotePage;
