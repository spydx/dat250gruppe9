import React from "react";
import NavBar from "../components/NavBar";
import CreatePoll from "../components/CreatePoll"

class CreatePollPage extends React.Component {
  render() {
    return (
      <div>
        <div>
          <NavBar />
        </div>
        <div className="row h-100 justify-content-center align-items-center">
        
        <div>
          <div style={{ marginTop: "2rem" }}>
            <CreatePoll/>
          </div>
        </div>
      </div>
    </div>
      
    );
  }
}

export default CreatePollPage;
