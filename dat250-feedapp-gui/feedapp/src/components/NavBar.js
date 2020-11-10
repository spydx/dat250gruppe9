import React from "react";
import Navbar from "react-bootstrap/Navbar";
import Container from "react-bootstrap/Container";
import { connect } from "react-redux";
import Button from "react-bootstrap/esm/Button";
import { Dropdown } from "react-bootstrap";


class NavBar extends React.Component {
  render() {
    if (this.props.state.user.isLoggedin) {
      return (
        <Navbar bg="white" variant="white">
          <Container>
            <Navbar.Brand href="\">
              <img
                alt="F"
                src="/pollhubblue.png"
                width="297"
                height="109"
                className="align-left"
              />
            </Navbar.Brand>
            <Dropdown style={{marginRight: "1.5%"}}>
              <Dropdown.Toggle variant="dark" id="dropdown-basic">
                {
                  this.props.state.user.firstname +
                  " " +
                  this.props.state.user.lastname
                }
              </Dropdown.Toggle>
              <Dropdown.Menu>
                <Dropdown.Item href="/account">Account</Dropdown.Item>
                <Dropdown.Item onClick={() => {this.props.setResetUser()}} href="/">Logout</Dropdown.Item>
              </Dropdown.Menu>
            </Dropdown>
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
              src="/pollhubblue.png"
              width="297"
              height="109"
              className="align-left"
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
