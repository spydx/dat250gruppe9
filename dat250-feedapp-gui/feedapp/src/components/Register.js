import React from "react";
import Button from "react-bootstrap/Button";
import { connect } from "react-redux";
import { Post, Get } from "../utils/actionHandler"
import { Redirect } from "react-router-dom";
import { API_URL } from "../constants/constants"
import MailOutlineIcon from '@material-ui/icons/MailOutline';
import PersonOutlineIcon from '@material-ui/icons/PersonOutline';
import LockOpenIcon from '@material-ui/icons/LockOpen';
import { Row, Form, Col } from "react-bootstrap";

class Register extends React.Component {
    
    async handleSubmit(email, firstname, lastname, password, passwordCheck) {

        if (password.length <= 6) {
            this.props.setReset();
            this.props.setError("Passwords must be between 6-60 characters long!")
            return;
        }else if (password !== passwordCheck) {
            
            this.props.setReset();
            this.props.setError("Passwords do not match")
            return;
        }

        const registerRequest = {
            email: email,
            firstname: firstname,
            lastname: lastname,
            password: password,
        }
        console.log(registerRequest)
        await Post(API_URL + "/auth/register", registerRequest)
            .then((res) => res.json())
            .then(
                (result) => {
                    console.log(result)
                },
                (error) => {
                    this.props.setError(error)
                    return;
                }
        )
        
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
                return;
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
                this.props.setError("Something went wrong: Could probably not create user with this information");
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
            return <Redirect to="/account"/>
        }

        return(
            <Form style = {{marginTop:"13%"}}>
                <Form.Group as={Row} controlId="Email">
                    <Form.Label column sm={2}>
                        <MailOutlineIcon/>
                    </Form.Label> 
                    <Col sm={10}>
                        <Form.Control type="email" placeholder="E-mail" onChange={e => this.setState({email: e.target.value})}/>
                    </Col>
                </Form.Group>
                

                <Form.Group as={Row} controlId="firstname">
                    <Form.Label column sm={2}>
                        <PersonOutlineIcon/>
                    </Form.Label> 
                    <Col sm={10}>
                        <Form.Control type="" placeholder="Firstname" onChange={e => this.setState({firstname: e.target.value})}/>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} controlId="lastname">
                    <Form.Label column sm={2}>
                        <PersonOutlineIcon/>
                    </Form.Label> 
                    <Col sm={10}>
                        <Form.Control type="" placeholder="Lastname" onChange={e => this.setState({lastname: e.target.value})}/>
                    </Col>
                </Form.Group>
            
                <Form.Group as={Row} controlId="password">
                    <Form.Label column sm={2}>
                        <LockOpenIcon/>
                    </Form.Label> 
                    <Col sm={10}>
                        <Form.Control type="password" placeholder="Password" onChange={e => this.setState({password: e.target.value})}/>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} controlId="passwordCheck">
                    <Form.Label column sm={2}>
                        <LockOpenIcon/>
                    </Form.Label> 
                    <Col sm={10}>
                        <Form.Control type="password" placeholder="Re-enter password" onChange={e => this.setState({ passwordCheck: e.target.value })} />
                        {this.props.state.user.error &&
                            <small id="emailHelp" className="form-text text-muted">{this.props.state.user.error}</small>
                        }                       
                    </Col>
                    
                </Form.Group>
                
                
                
                
                <Form.Group as={Row} controlId="password">
                    <Form.Label column sm={2}></Form.Label> 
                    <Col sm={10} style={{textAlign: "right"}}>
                    <Button
                        variant="success" 
                        onClick={() => {
                            this.handleSubmit(
                                this.state.email,
                                this.state.firstname,
                                this.state.lastname,
                                this.state.password,
                                this.state.passwordCheck
                        )}}
                    >
                        
                        Register
                    </Button>
                    </Col>
                </Form.Group>
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
  
  export default connect(mapStateToProps, mapDispatchToProps)(Register); 