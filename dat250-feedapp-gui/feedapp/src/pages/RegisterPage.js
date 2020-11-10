import React from "react";
import Register from "../components/Register.js";
import NavBar from "../components/NavBar";

class RegisterPage extends React.Component {
  render() {
    return (
      <div>
        <div>
          <NavBar />
        </div>
        <div className="align-items-center" >
            <h1 style={{ textAlign: "center", marginTop: "1rem" }}>Register</h1>
            <hr className="text-dark bg-dark" style={{width:"60%"}} />
        </div>
        
        <div className="row h-100 justify-content-center align-items-center">
        <div>
            <div style={{ marginTop: "2rem" }}>
              <Register/> 
            </div>
          </div>
        </div>
      </div>
      
    );
  }
}

export default RegisterPage;
