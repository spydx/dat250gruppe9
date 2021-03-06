import React from "react";
import Spinner from "react-bootstrap/Spinner";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/css/bootstrap.css";
import Button from "react-bootstrap/Button";
import { connect } from "react-redux";
import Divider from "@material-ui/core/Divider";
import ResultGraph from "./ResultComponents/ResultGraph";
import PollStatus from "./PollComponents/PollStatus";
import { API_URL } from "../constants/constants";
import { Get } from "../utils/actionHandler";

class Result extends React.Component {
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

  getCurrentWinner(yes, no, pollYes, pollNo) {
    if (yes === no) {
      return "It's a tie.";
    }

    if (yes < no) {
      return pollNo;
    } else {
      return pollYes;
    }
  }

  async fetchResultData() {
    await Get(API_URL + "/result/" + this.props.id)
      .then((res) => res.json())
      .then(
        (result) => {
          this.props.setData(result);
        },

        (error) => {
          console.log(error);
          this.props.setError(error);
        }
      );
  }

  componentDidMount() {
    this.fetchResultData();
    this.setState({});
  }

  render() {
    const poll = this.getPoll(this.props.id);

    const { error, isLoaded, resultData } = this.props.state.result;
    if (error) {
      return <div>Something went wrong: {error.message}</div>;
    } else if (!isLoaded) {
      return (
        <div>
          <Spinner animation="border" role="status" variant="secondary">
            <span className="sr-only">Loading...</span>
          </Spinner>
        </div>
      );
    }
    return (
      <div>
        <div>
          <div key={resultData.id} className="mt-2">
            <div
              style={{ textAlign: "center", marginTop: "2%" }}
              className="display-4"
            >
              <small>{poll.question}</small>
              <Divider variant="middle" />
            </div>

            <div className="container">
              <div className="row">
                <div
                  className="col"
                  style={{ textAlign: "left", fontSize: "140%" }}
                >
                  <div style={{ textAlign: "right" }}>
                    <PollStatus poll={poll} />
                  </div>
                  <br></br>
                  <br></br>
                  <p>{"Number of Votes: " + resultData.total}</p>
                  <p>{poll.answeryes + ": " + resultData.yes}</p>
                  <p>{poll.answerno + ": " + resultData.nos}</p>

                  <br></br>

                  <p>
                    {"Current lead: " +
                      this.getCurrentWinner(
                        resultData.yes,
                        resultData.nos,
                        poll.answeryes,
                        poll.asnwerno
                      )}
                  </p>
                </div>
                <div className="col">
                  <ResultGraph
                    votes={this.props.state.result.resultData.votes}
                    poll={poll}
                  />
                  <Button
                    variant="dark"
                    style={{
                      marginLeft: "65%",
                      marginTop: "8%",
                      marginRight: "5%",
                    }}
                    href="/"
                  >
                    Return to overview
                  </Button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
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
    setError: (error) =>
      dispatch({
        type: "SET_RESULT_ERROR",
        error: error,
        isLoaded: true,
      }),

    setData: (result) =>
      dispatch({
        type: "SET_RESULTDATA",
        isLoaded: true,
        resultData: result,
      }),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Result);
