import React from "react";
import AccountBoxIcon from '@material-ui/icons/AccountBox';

class User extends React.Component {

    render() {
        return (
            <div style={{ marginTop: "2rem", marginBottom: "5rem", marginLeft: "20%", marginRight: "20%" }}>
                <h1><AccountBoxIcon style={{ fontSize: 45, marginBottom: "6px", marginRight: "10px" }}>:</AccountBoxIcon>User Information</h1>
                <hr class="text-dark bg-dark"/>
                <h5>Email</h5>
                <h5>Firstname</h5>
                <h5>Lastname</h5>
            </div>
        )
    }
}

export default User;

