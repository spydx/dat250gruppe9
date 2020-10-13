import React from "react";
import Card from "react-bootstrap/Card";
import Login from "../components/Login.js"

class LoginPage extends React.Component {
    render() {
        return (
            <div class="row h-100 justify-content-center align-items-center">
                <div>
                    <h1 style={{ textAlign: "center", marginTop:"2rem" }}>Login</h1>
                    <div style={{marginTop:"2rem"}}>
                        <Login></Login>
                    </div>
                </div>
            </div>
            
        );
    }

}

export default LoginPage;