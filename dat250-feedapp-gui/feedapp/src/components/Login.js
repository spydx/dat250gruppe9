import React from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import { connect } from "react-redux";
import { Post } from "../utils/actionHandler"

class Login extends React.Component {
   
  // fetchUserData(email, password) {
  //   fetch("http://localhost:8080/api/users/1")
  //     .then((res) => res.json())
  //     .then(
  //       (result) => {
  //         this.props.setLogin(result);
  //       },
  //       (error) => {
  //         this.props.setError(error);
  //       }
  //     );
  // }
  handleSubmit(email, password) {
    const loginRequest = {
      email: email,
      password: password
    }

    Post("http://localhost:8080/api/auth/login", loginRequest)
      .then((res) => res.json())
      .then(
        (result) => {
          this.props.setAccessToken(result);
        },
        (error) => {
          this.props.setError(error);
        }
      );
      
    
  }
  
 

  render() {
    return (
      <Form>
        <Form.Group controlId="email">
          <Form.Label>Email address</Form.Label>
          <Form.Control type="email" placeholder="Enter email" onChange={e => this.setState({email: e.target.value})}/> 
        </Form.Group>

        <Form.Group controlId="password">
          <Form.Label>Password</Form.Label>
          <Form.Control type="password" placeholder="Password" onChange={e => this.setState({password: e.target.value})}/>
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
          onClick={() => { this.handleSubmit(this.state.email, this.state.password) }}
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
    }),
    
    setAccessToken: (result) => dispatch({
      type: "AUTHORIZE",
      token: result.token
    }),

    setEmail: (result) => dispatch({
      type: "SET_EMAIL",
      email: result.email
    })
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Login);
