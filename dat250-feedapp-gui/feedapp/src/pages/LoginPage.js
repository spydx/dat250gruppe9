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
        <div className="align-items-center" >
            <h1 style={{ textAlign: "center", marginTop: "1rem" }}>Login</h1>
            <hr className="text-dark bg-dark" style={{width:"60%"}} />
        </div>

        <div className="row h-100 justify-content-center align-items-center">
        
        <div>
          
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
