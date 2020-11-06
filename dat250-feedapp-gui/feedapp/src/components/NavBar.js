import React from "react";
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";
import Container from "react-bootstrap/Container";
import { connect } from "react-redux";
import Button from "react-bootstrap/esm/Button";

class NavBar extends React.Component {
  render() {
    if (this.props.state.user.isLoggedin) {
      return (
        <Navbar bg="white" variant="white">
          <Container>
            <Navbar.Brand href="\">
              <img
                alt="F"
                src="/pollhublogo.png"
                width="297"
                height="109"
                className="align-center"
              />
            </Navbar.Brand>
            <NavDropdown
              title={
                this.props.state.user.firstname +
                " " +
                this.props.state.user.lastname
              }
              id="collapsible-nav-dropdown"
            >
              <NavDropdown.Item href="/account">Account</NavDropdown.Item>
              <NavDropdown.Item onClick={() => {this.props.setResetUser()}} href="/">Logout</NavDropdown.Item>
            </NavDropdown>
          </Container>
        </Navbar>
      );
    }

    return (
      <Navbar bg="white" variant="white">
        <Container>
          <Navbar.Brand href="\">
            <img
              alt="F"
              src="/pollhublogo.png"
              width="297"
              height="109"
              className="align-center"
            />
          </Navbar.Brand>
          <Button href="/login" variant="success">Login/Register</Button>
        </Container>
      </Navbar>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    state: state,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    setResetUser: () => dispatch({
      type: "RESET_USER"
    })
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(NavBar);
