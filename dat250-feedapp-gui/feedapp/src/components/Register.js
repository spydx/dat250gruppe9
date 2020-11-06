import React from "react";
import Button from "react-bootstrap/Button";
import { connect } from "react-redux";
import { Post, Get } from "../utils/actionHandler"
import { Redirect } from "react-router-dom";
import { API_URL } from "../constants/constants"
import MailOutlineIcon from '@material-ui/icons/MailOutline';
import LockOpenIcon from '@material-ui/icons/LockOpen';
import LockIcon from '@material-ui/icons/Lock';
import { FormLabel, Row, Form, Col } from "react-bootstrap";

class Register extends React.Component {
    render() {
        return(
            <Form style = {{marginTop:"13%"}}>
                    <Form.Group as={Row} controlId="Email">
                        <Form.Label column sm={2}>
                            <MailOutlineIcon/>
                        </Form.Label> 
                        <Col sm={10}>
                            <Form.Control type="email" placeholder="E-mail" onChange={e => this.setState({name: e.target.value})}/>
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} controlId="password">
                        <Form.Label column sm={2}>
                            <LockOpenIcon/>
                        </Form.Label> 
                        <Col sm={10}>
                            <Form.Control type="password" placeholder="Password" onChange={e => this.setState({name: e.target.value})}/>
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} controlId="password">
                        <Form.Label column sm={2}>
                            <LockOpenIcon/>
                        </Form.Label> 
                        <Col sm={10}>
                            <Form.Control type="password" placeholder="Re-enter password" onChange={e => this.setState({name: e.target.value})}/>
                        </Col>
                    </Form.Group>
                    <div>
                        <Button variant="success" 
                                style={{ marginLeft: "5%", marginTop: "5%" }}
                                href = "/">
                            Register
                        </Button>
                    </div>
            </Form>
        );
    }
}

export default Register; 