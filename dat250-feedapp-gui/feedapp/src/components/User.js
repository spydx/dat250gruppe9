import React from "react";
import AccountBoxIcon from "@material-ui/icons/AccountBox";
import Button from "react-bootstrap/Button";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import Container from "react-bootstrap/Container";
import { connect } from "react-redux";
import { Put } from "../utils/actionHandler";
import { API_URL } from "../constants/constants";

class User extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      firstname: this.props.state.user.firstname,
      lastname: this.props.state.user.lastname,
    };
    this.handleChange = this.handleChange.bind(this);
    this.submitChange = this.handleSubmit.bind(this);
  }

  handleChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  async handleSubmit(firstname, lastname) {
    const updateUserRequest = {
      firstname: firstname,
      lastname: lastname,
    };
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

  render() {
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
          </Form>
        </Container>
        <Container style={{ marginTop: "10%" }}>
          <Row className="d-flex flex-row-reverse" xs={4} md={6} lg={8} xl={10}>
            <Col>
              <Button
                type="submit"
                variant="success"
                size="lg"
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
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(User);
