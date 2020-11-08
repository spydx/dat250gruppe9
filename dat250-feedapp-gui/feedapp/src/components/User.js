import React from "react";
import AccountBoxIcon from "@material-ui/icons/AccountBox";
import Button from "react-bootstrap/Button";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import Container from "react-bootstrap/Container";
import OverlayTrigger from "react-bootstrap/OverlayTrigger";
import Tooltip from "react-bootstrap/Tooltip";
import { connect } from "react-redux";
import { Put } from "../utils/actionHandler";
import { Delete } from "../utils/actionHandler";
import { API_URL } from "../constants/constants";
import { Redirect } from "react-router-dom";

class User extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      firstname: this.props.state.user.firstname,
      lastname: this.props.state.user.lastname,
      deleteAccount: false,
    };
    this.handleChange = this.handleChange.bind(this);
    this.submitChange = this.handleSubmit.bind(this);
    this.handleCheckbox = this.handleCheckbox.bind(this);
  }

  handleChange(e) {
    this.setState({ [e.target.name]: e.target.value });
    console.log(this.state.deleteAccount);
  }

  handleCheckbox(e) {
    this.state.deleteAccount = !this.state.deleteAccount;
    console.log(this.state.deleteAccount);
  }

  async deleteAccount() {}

  async handleSubmit(firstname, lastname) {
    if (this.state.deleteAccount) {
      console.log("Deleting account");
      await Delete(
        API_URL + "/users/" + this.props.state.user.id,
        this.props.state.user.token
      )
        .then((res) => res)
        .then(
          (result) => {
            console.log("Logged out user");
            console.log(result);
            this.props.setResetUser();
          },
          (error) => {
            console.log(error);
          }
        );
    } else {
      const updateUserRequest = {
        firstname: firstname,
        lastname: lastname,
      };
      console.log("Changing name");
      console.log(updateUserRequest);

      await Put(
        API_URL + "/users/" + this.props.state.user.id,
        updateUserRequest,
        this.props.state.user.token
      )
        .then((res) => res.json())
        .then((result) => {
          this.props.setUserName(result);
        });
    }
  }

  render() {
    if (!this.props.state.user.isLoggedin) {
      return <Redirect to="/" />;
    }
    return (
      <Container fluid="sm" className="mt-4">
        <h1>
          <AccountBoxIcon
            style={{ fontSize: 45, marginBottom: "6px", marginRight: "10px" }}
          ></AccountBoxIcon>
          User Information
        </h1>
        <hr className="text-dark bg-dark" />
        <Container fluid="sm">
          <Form>
            <Form.Group as={Row} controlId="formEmail">
              <Form.Label column sm="2">
                <h5>Email</h5>
              </Form.Label>
              <Col>
                <Form.Control
                  plaintext
                  readOnly
                  defaultValue={this.props.state.user.email}
                />
              </Col>
            </Form.Group>

            <Form.Group as={Row} controlId="formFirstName">
              <Form.Label column sm="2">
                <h5>Firstname</h5>
              </Form.Label>
              <Col>
                <Form.Control
                  type="text"
                  required
                  name="firstname"
                  value={this.state.firstname}
                  onChange={this.handleChange}
                />
              </Col>
            </Form.Group>

            <Form.Group as={Row} controlId="formLastName">
              <Form.Label column sm="2">
                <h5>Lastname</h5>
              </Form.Label>
              <Col>
                <Form.Control
                  type="text"
                  required
                  name="lastname"
                  value={this.state.lastname}
                  onChange={this.handleChange}
                />
              </Col>
            </Form.Group>

            <Form.Group as={Row} controlId="formDeleteAccount">
              <OverlayTrigger
                placement="bottom"
                overlay={
                  <Tooltip id="tooltip">
                    <strong>Warning, deleting account is permanent</strong>.
                  </Tooltip>
                }
              >
                <Form.Check
                  style={{ marginLeft: "15px" }}
                  name="deleteAccount"
                  type="checkbox"
                  label="Delete account"
                  onChange={this.handleCheckbox}
                />
              </OverlayTrigger>
            </Form.Group>
          </Form>
        </Container>
        <Container style={{ marginTop: "10%" }}>
          <Row className="d-flex flex-row-reverse" xs={4} md={6} lg={8} xl={10}>
            <Col>
              <Button
                type="submit"
                variant="success"
                size="lg"
                block
                onClick={() =>
                  this.handleSubmit(this.state.firstname, this.state.lastname)
                }
              >
                {"Save"}
              </Button>
            </Col>
            <Col>
              <Button variant="danger" size="lg" block href="/">
                {"Cancel"}
              </Button>
            </Col>
          </Row>
        </Container>
      </Container>
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
    setUserName: (result) =>
      dispatch({
        type: "SET_NAME",
        firstname: result.firstname,
        lastname: result.lastname,
      }),
    setResetUser: () =>
      dispatch({
        type: "RESET_USER",
      }),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(User);
