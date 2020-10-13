import React from "react";
import Card from "react-bootstrap/Card";
import Form from "react-bootstrap/Form"
import Button from "react-bootstrap/Button"

class Login extends React.Component {
   render() {
       return (
        <Form>
        <Form.Group controlId="formBasicEmail">
          <Form.Label>Email address</Form.Label>
          <Form.Control type="email" placeholder="Enter email" />
          <Form.Text className="text-muted">
            We'll never share your credentials with anyone else.
          </Form.Text>
        </Form.Group>
      
        <Form.Group controlId="formBasicPassword">
          <Form.Label>Password</Form.Label>
          <Form.Control type="password" placeholder="Password" />
        </Form.Group>
        <Form.Group controlId="formBasicCheckbox">
          <Form.Check type="checkbox" label="Remember me" />
        </Form.Group>
        <Button variant="success" type="register" style={{width: '5rem', margin:"1rem"}}>
          Register
        </Button>
        <Button variant="success" type="register" style={{width: '5rem', margin:"0.5rem", marginLeft:"5rem"}}>
          Login
        </Button>
      </Form>
       );
   }
    
}

export default Login;