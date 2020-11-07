import React from "react";
import {Form, Row, Col} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import { connect } from "react-redux";
import { Post, Get } from "../utils/actionHandler"
import { Redirect } from "react-router-dom";
import { API_URL } from "../constants/constants"
import MailOutlineIcon from '@material-ui/icons/MailOutline';
import VpnKeyIcon from '@material-ui/icons/VpnKey';

class Login extends React.Component {
   
  async handleSubmit(email, password) {
    const loginRequest = {
      email: email,
      password: password
    }

    await Post(API_URL + "/auth/login", loginRequest)
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
    
    await Get(API_URL + "/users/" + this.props.state.user.id, this.props.state.user.token)
      .then((res) => res.json())
      .then(
        (result) => {
          if (this.props.state.user.id && this.props.state.user.token) {
            this.props.setError(null);
            this.props.setLogin(result);
            this.props.setEmail(this.state.email);
          } else {
            this.props.setReset();
            this.props.setError("Password and email did not match!");
          }
        },
        (error) => {
          this.props.setReset();
          this.props.setError(error);
        }
      );
  }

  componentDidMount() {
    this.props.setReset();
  }

  render() {
    if (this.props.state.user.isLoggedin) { 
      return <Redirect to="/"/>
    }

    return (
      <Form>
        <Form.Group as={Row} controlId="email">
          <Form.Label column sm={2}>
            <MailOutlineIcon/>
          </Form.Label>
          <Col sm={10}>
            <Form.Control type="email" placeholder="Enter email" onChange={e => this.setState({email: e.target.value})}/> 
          </Col>
        </Form.Group>

        <Form.Group as={Row} controlId="password">
          <Form.Label column sm={2}>
            <VpnKeyIcon/>
          </Form.Label>
          <Col sm={10}>
            <Form.Control type="password" placeholder="Password" onChange={e => this.setState({password: e.target.value})}/>
          </Col>
        </Form.Group>
        {this.props.state.user.error &&
          <div>
            {this.props.state.user.error}
          </div>
        }
        
        <Button
          variant="success"
          style={{ width: "5rem", margin: "1rem" }}
          href="/register"
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
