import React from "react";
import NavBar from "../components/NavBar";
import Vote from "../components/Vote"

class VotePage extends React.Component {
  render() {
    return (
      <div>
        <div>
          <NavBar />
        </div>
        <div className="row h-100 justify-content-center align-items-center">
        
        <div>
          <div style={{ marginTop: "2rem" }}>
            <Vote/>
          </div>
        </div>
      </div>
    </div>
      
    );
  }
}

export default VotePage;
