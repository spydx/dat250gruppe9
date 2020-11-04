import React from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import { connect } from "react-redux";
import { Post, Get } from "../utils/actionHandler"
import { Redirect } from "react-router-dom";

class Login extends React.Component {
   
  //TODO: fix if the user does not exist
  async handleSubmit(email, password) {
    const loginRequest = {
      email: email,
      password: password
    }

    await Post("http://localhost:8080/api/auth/login", loginRequest)
      .then((res) => res.json())
      .then(
        (result) => {
          this.props.setAccessToken(result);
        },
        (error) => {
          this.props.setReset();
          this.props.setError(error);
        }
      );
    
    await Get("http://localhost:8080/api/users/" + this.props.state.user.id, this.props.state.user.token) //TODO should have access token
      .then((res) => res.json())
      .then(
        (result) => {
          this.props.setLogin(result);
          this.props.setEmail(this.state.email)
        },
        (error) => {
          this.props.setReset();
          this.props.setError(error);
        }
      );

  }

  render() {
    if (this.props.state.user.isLoggedin) { 
      return <Redirect to="/"/>
    }

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
        type: "SET_USER_ERROR",
        error: error,
        isLoaded: true,
      }),

    setLogin: (result) => dispatch({
        type: "SET_LOGIN",
        error: null,
        isLoggedin: true,
        isLoaded: true,
        id: result.id,
        firstname: result.firstname,
        lastname: result.lastname,
    }),
    
    setAccessToken: (result) => dispatch({
      type: "AUTHORIZE",
      error: null,
      token: result.token,
      id: result.profile
    }),

    setEmail: (result) => dispatch({
      type: "SET_EMAIL",
      error: null,
      email: result
    }),

    setReset: () => dispatch({
      type: "RESET_USER"
    })
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Login);
