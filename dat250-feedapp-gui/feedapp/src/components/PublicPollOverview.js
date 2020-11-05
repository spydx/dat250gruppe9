import React from "react";
import PollFromAPI from "./PollComponents/PollFromAPI";
import { connect } from "react-redux";
import { Get } from "../utils/actionHandler"
import Button from "react-bootstrap/Button";
import { API_URL } from "../constants/constants"

class PublicPollOverview extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      didFetch: false
    }
  }

  fetchPollData() {
    Get(API_URL + "/polls/")
    .then((res) => res.json())
    .then(
       (result) => {
          this.props.setData(result);
          this.setState({ didFetch: true });
          if (this.props.state.user.isLoggedin) { // if user is logged in add all its polls to the state store
              this.props.setUserPolls(this.getUserPolls(this.props.state.user.id))
          }
        },
        (error) => {
          this.props.setError(error);
          this.setState({ didFetch: true });
        }
      );
  }

  getUserPolls(uid) {
    var polls = []
    for (const element of this.props.state.poll.pollData) {
      if (element.owner.id === uid) {
        polls.push(element);
      }
    }
    return polls;
  }


  render() {
    if (!this.state.didFetch) {
      this.fetchPollData();
    }
    if (!this.props.state.user.isLoggedin) {
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
            </div>
          </div>
        </div>
      );
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
              <Button
                variant="success"
                style={{ width: "50%", marginLeft: "50%" }}
                block
                href="/createpoll"
              >
                Create Poll
              </Button>
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
        type: "SET_POLL_ERROR",
        error: error,
        isLoaded: true,
      }),

    setData: (result) => dispatch({
        type: "SET_DATA",
        isLoaded: true,
        pollData: result
    }),
    
    setUserPolls: (result) => dispatch({
      type: "SET_USER_POLLDATA",
      pollData: result
    })
  };
};


export default connect(mapStateToProps, mapDispatchToProps)(PublicPollOverview);
