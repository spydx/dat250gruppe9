import React from "react";
import Login from "../components/Login.js";
import NavBar from "../components/NavBar";

class LoginPage extends React.Component {
  render() {
    return (
      <div>
        <div>
          <NavBar />
        </div>
        <div className="row h-100 justify-content-center align-items-center">
        
        <div>
          <h1 style={{ textAlign: "center", marginTop: "2rem" }}>Login</h1>
          <div style={{ marginTop: "2rem" }}>
            <Login></Login>
          </div>
        </div>
      </div>
    </div>
      
    );
  }
}

export default LoginPage;
