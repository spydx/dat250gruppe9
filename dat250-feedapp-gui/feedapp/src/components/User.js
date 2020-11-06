import React from "react";
import AccountBoxIcon from "@material-ui/icons/AccountBox";
import Button from "react-bootstrap/Button";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import { connect } from "react-redux";

class User extends React.Component {
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
          <div className="row">
            <div className="col-sm-2">
              <h5>Email</h5>
            </div>
            <div className="col-sm">
              <div className="form-group">
                <input
                  type="email"
                  className="form-control"
                  id="inputEmail"
                  aria-describedby="emailHelp"
                  placeholder={this.props.state.user.email}
                />
              </div>
            </div>
          </div>

          <div className="row">
            <div className="col-sm-2">
              <h5>Firstname</h5>
            </div>
            <div className="col-sm">
              <div className="form-group">
                <input
                  type="email"
                  className="form-control"
                  id="inputFirstname"
                  placeholder={this.props.state.user.firstname}
                />
              </div>
            </div>
          </div>

          <div className="row">
            <div className="col-sm-2">
              <h5>Lastname</h5>
            </div>
            <div className="col-sm">
              <div className="form-group">
                <input
                  type="email"
                  className="form-control"
                  id="inputLastname"
                  aria-describedby="emailHelp"
                  placeholder={this.props.state.user.lastname}
                />
              </div>
            </div>
          </div>
        </Container>
          <Container style={{ marginTop: "10%" }}>
            <Row className="d-flex flex-row-reverse" xs={4} md={6} lg={8} xl={10}>
              <Col>
                <Button variant="success" size="lg" block href="/">
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

export default connect(mapStateToProps)(User);
