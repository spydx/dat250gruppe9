import React from "react";
import PollFromAPI from "./PollComponents/Poll";
import { connect } from "react-redux";
import { Get } from "../utils/actionHandler"
import Button from "react-bootstrap/Button";
import { API_URL } from "../constants/constants"
import Divider from '@material-ui/core/Divider'

class PublicPollOverview extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      didFetch: false
    }
  }

  fetchPollData() { 
    if (this.props.state.user.isLoggedin) {
      Get(API_URL + "/polls/", this.props.state.user.token)
        .then((res) => res.json())
        .then(
          (result) => {
            this.props.setData(result);
            this.setState({ didFetch: true });
            this.fetchUserPolls();
            
          },
          (error) => {
            this.props.setError(error);
            this.setState({ didFetch: true });
          }
        );
    } else {
      Get(API_URL + "/polls/")
        .then((res) => res.json())
        .then(
          (result) => {
            this.props.setData(result);
            this.setState({ didFetch: true });
          },
          (error) => {
            this.props.setError(error);
            this.setState({ didFetch: true });
          }
        );
    }
  }

  fetchUserPolls() {
    Get(API_URL + "/polls/mine", this.props.state.user.token)
        .then((res) => res.json())
        .then(
          (result) => {
            this.props.setUserPolls(result)
          },
          (error) => {
            this.props.setError(error);
            this.setState({ didFetch: true });
          }
        );
  }

  componentDidMount() {
    this.props.setResetResult();
  }


  render() {
    if (!this.state.didFetch) {
      this.fetchPollData();
    }
    if (!this.props.state.user.isLoggedin) {
      return (
        <div>
          <div className="container">
            <hr className="text-dark bg-dark" />
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
          <Button
                variant="success"
                style={{ width: "25%", marginLeft: "73%", marginBottom: "0.5%" }}
                block
                href="/createpoll"
              >
                Create Poll
          </Button>
          <hr className="text-dark bg-dark" />
          <div className="row">
            <div className="col-sm">
              <h1 className="display-4" style={{ textAlign: "center" }}>
                All polls
              </h1>
              <PollFromAPI poll={this.props.state.poll}/>
            </div>
            <Divider variant="middle" orientation="vertical" flexItem/>
            <div className="col-sm">
              <h1 className="display-4" style={{ textAlign: "center" }}>
                Your polls
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
    }),

    setResetResult: () => dispatch({
      type: "RESET_RESULT"
    })
  };
};


export default connect(mapStateToProps, mapDispatchToProps)(PublicPollOverview);
