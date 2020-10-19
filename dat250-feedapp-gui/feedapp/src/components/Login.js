import React from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import { connect } from "react-redux";

class Login extends React.Component {
   
  fetchUserData(email, password) {
    fetch("http://localhost:8080/api/users/1")
      .then((res) => res.json())
      .then(
        (result) => {
          this.props.setLogin(result);
        },
        (error) => {
          this.props.setError(error);
        }
      );
  }
  
  render() {
    var email = "";
    var password = ""
    return (
      <Form>
        <Form.Group controlId="email">
          <Form.Label>Email address</Form.Label>
          <Form.Control type="email" placeholder="Enter email" /> 
        </Form.Group>

        <Form.Group controlId="password">
          <Form.Label>Password</Form.Label>
          <Form.Control type="password" placeholder="Password" />
        </Form.Group>
        <Form.Group controlId="checkbox">
          <Form.Check type="checkbox" label="Remember me" />
        </Form.Group>
        <Button
          variant="success"
          style={{ width: "5rem", margin: "1rem" }}
        >
          Register
        </Button>
        <Button
          variant="success"
          style={{ width: "5rem", margin: "0.5rem", marginLeft: "5rem" }}
          onClick = {() => {this.fetchUserData(email, password)}}
        >
          Login
        </Button>
      </Form>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    state: state
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    setError: (error) => dispatch({
        type: "SET_ERROR",
        error: error,
        isLoaded: true,
      }),

    setLogin: (result) => dispatch({
        type: "SET_LOGIN",
        isLoggedin: true,
        isLoaded: true,
        id: result.id,
        firstname: result.firstname,
        lastname: result.lastname,
        email: result.email,
        role: result.role,
        pollData: result.pollData
      })
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Login);
