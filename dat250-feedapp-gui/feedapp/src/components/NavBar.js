import React from "react";
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";
import Container from "react-bootstrap/Container";
import { connect } from "react-redux";

class NavBar extends React.Component {
  render() {

    if (this.props.state.user.isLoggedin) {
      return (
        <Navbar bg="white" variant="white">
          <Container>
            <Navbar.Brand href="\">
              <img
                alt="F"
                src="/feedApp-logo.png"
                width="100"
                height="70"
                className="align-left"
              />
            </Navbar.Brand>

            <Navbar.Collapse className="justify-content-center">
              <Navbar.Text >
                <h1 className="display-4">FeedApp</h1>
              </Navbar.Text>
            </Navbar.Collapse>

            <NavDropdown title={this.props.state.user.firstname + " " + this.props.state.user.lastname} id="collasible-nav-dropdown">
              <NavDropdown.Item href="\">Account</NavDropdown.Item>
            </NavDropdown>
          </Container>
        </Navbar>
      )
    }

    return (
      <Navbar bg="white" variant="white">
        <Container>
          <Navbar.Brand href="\">
            <img
              alt="F"
              src="/feedApp-logo.png"
              width="100"
              height="70"
              className="align-left"
            />
          </Navbar.Brand>

          <Navbar.Collapse className="justify-content-center">
            <Navbar.Text >
              <h1 className="display-4">FeedApp</h1>
            </Navbar.Text>
          </Navbar.Collapse>

          <NavDropdown title="User" id="collasible-nav-dropdown">
            <NavDropdown.Item href="\">Account</NavDropdown.Item>
            <NavDropdown.Item href="/login">Login</NavDropdown.Item>
          </NavDropdown>
        </Container>
      </Navbar>
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
    
  };
};



export default connect(mapStateToProps, mapDispatchToProps)(NavBar);
