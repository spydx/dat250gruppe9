import React from "react";
import PollFromAPI from "./PollComponents/PollFromAPI";
import { connect } from "react-redux";
import { saveState } from "../localStorage";

class PublicPollOverview extends React.Component {

  fetchPollData() {
      fetch("http://localhost:8080/api/polls/")
      .then((res) => res.json())
      .then(
        (result) => {
          this.props.setData(result);
        },
        (error) => {
          this.props.setError(error);
        }
      );
  };

  render() {
    if (!this.props.state.poll.isLoaded) {
      this.fetchPollData();
      saveState(this.props.state)
    }
    return (
      <div>
        <div className="container">
          <div className="row">
            <div className="col-sm">
              <h1 className="display-4" style={{ textAlign: "center" }}>
                All polls
              </h1>
              <PollFromAPI poll={this.props.state.poll}/>
            </div>
            <div className="col-sm">
              <h1 className="display-4" style={{ textAlign: "center" }}>
                Created polls
              </h1>
              <PollFromAPI poll={this.props.state.user}/>
            </div>
          </div>
        </div>
      </div>
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
    setError: (error) => dispatch({
        type: "SET_ERROR",
        error: error,
        isLoaded: true,
      }),

    setData: (result) => dispatch({
        type: "SET_DATA",
        isLoaded: true,
        pollData: result
      })
  };
};


export default connect(mapStateToProps, mapDispatchToProps)(PublicPollOverview);
