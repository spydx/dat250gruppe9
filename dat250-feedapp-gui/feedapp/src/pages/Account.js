import React from "react";
import "bootstrap/dist/css/bootstrap.css";
import NavBar from "../components/NavBar";
import User from "../components/User";

class Account extends React.Component {
  render() {
    return (
      <div>
        <div>
          <NavBar />
        </div>
        <div>
            <User />
        </div>
      </div>
    );
  }
}

export default Account;
