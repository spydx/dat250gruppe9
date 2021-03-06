import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/css/bootstrap.css";
import Button from "react-bootstrap/Button";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import HowToVoteTwoToneIcon from "@material-ui/icons/HowToVoteTwoTone";
import { connect } from "react-redux";
import { Post } from "../utils/actionHandler";
import { API_URL } from "../constants/constants";
import { Redirect } from "react-router-dom";

class Vote extends React.Component {
  getStatus(endDate) {
    var currentDate = new Date();
    endDate = new Date(endDate);
    var ended = currentDate > endDate && endDate.getFullYear() !== 1970;
    return ended;
  }

  getPoll(pollid) {
    //Check for public polls
    for (const element of this.props.state.poll.pollData) {
      if (element.id === pollid) {
        return element;
      }
    }
    //check for users polls
    for (const element of this.props.state.user.pollData) {
      if (element.id === pollid) {
        return element;
      }
    }
  }

  async handleSubmit(answer) {
    const voteRequest = {
      answer: answer,
    };
    if (this.props.state.user.isLoggedin) {
      await Post(
        API_URL + "/polls/" + this.props.id + "/vote/",
        voteRequest,
        this.props.state.user.token
      );
    } else {
      await Post(API_URL + "/polls/" + this.props.id + "/vote/", voteRequest);
    }
  }

  render() {
    const poll = this.getPoll(this.props.id);
    if (this.getStatus(poll.timeend)) {
      return <Redirect to={"/result/" + poll.id} />;
    }

    return (
      <Container fluid="sm" className="mt-4">
        <h1>
          <HowToVoteTwoToneIcon
            style={{ fontSize: 45, marginBottom: "6px", marginRight: "10px" }}
          ></HowToVoteTwoToneIcon>
          Vote
        </h1>
        <hr className="text-dark bg-dark" />

        <Container className="mt-5">
          <h3> Voting on: {poll.question}</h3>
          <Container className="mt-5">
            <Row>
              <Col className="d-flex justify-content-end">
                <Button
                  size="lg"
                  variant="success"
                  onClick={() => {
                    this.handleSubmit(true);
                  }}
                  href={"/result/" + this.props.id}
                >
                  {poll.answeryes}
                </Button>
              </Col>
              <Col className="d-flex justify-content-start">
                <Button
                  size="lg"
                  variant="danger"
                  onClick={() => {
                    this.handleSubmit(false);
                  }}
                  href={"/result/" + this.props.id}
                >
                  {poll.answerno}
                </Button>
              </Col>
            </Row>
          </Container>
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
  return {};
};

export default connect(mapStateToProps, mapDispatchToProps)(Vote);
