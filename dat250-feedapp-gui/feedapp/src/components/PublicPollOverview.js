import React from "react";
import Poll from "./PollComponents/Poll";
import { connect } from "react-redux";
import { Get } from "../utils/actionHandler"
import Button from "react-bootstrap/Button";
import { API_URL } from "../constants/constants"
import Divider from '@material-ui/core/Divider'
import UserPoll from "./PollComponents/UserPolls"
import {Form} from "react-bootstrap";

class PublicPollOverview extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      didFetch: false
    }
  }

  async fetchPollData() { 
    if (this.props.state.user.isLoggedin) {
      await Get(API_URL + "/polls/", this.props.state.user.token)
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
       await Get(API_URL + "/polls/")
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

  async fetchUserPolls() {
     await Get(API_URL + "/polls/mine", this.props.state.user.token)
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
    this.props.setResetPoll();
    this.props.setResetUserError();
  }


  render() {
    if (!this.state.didFetch) {
      this.fetchPollData();
    }
    return (
      <div>
        <div className="container">
          {this.props.state.user.isLoggedin &&
            <div>
              <div style={{display: "flex"}}>
                <h1 style={{ textAlign: "Left", fontSize: "190%"}}>
                Poll Overview
                </h1>
              <div style={{ display: "flex", marginLeft: "51%" }}>
                <p style={{width: "50%", fontSize: "120%", marginTop: "1%"}}> Open poll: </p>
                
                <Form.Control
                  placeholder="Pollid"
                  onChange={e => this.setState({ openpollid: e.target.value })}
                  style={{float: "inline-end", marginRight: "1%"}}
                />
                <Button
                  variant="success"
                  style={{ marginBottom: "3%" }}
                  href={"/vote/" + this.state.openpollid}
                >
                  Open</Button>
              </div>
                    
              </div>
              <hr className="text-dark bg-dark" />
              <div className="row">
                <div className="col-sm">
                  <Poll poll={this.props.state.poll}/>
                </div>
                <Divider variant="middle" orientation="vertical" flexItem/>
                <div className="col-sm">
                  <div style={{display: "flex"}}>
                    <h1 style={{ textAlign: "Left", fontSize: "160%"}}>
                      Your Polls
                    </h1>
                    <Button
                    variant="success"
                    style={{ width: "30%", marginLeft: "48.41%", marginBottom: "0.5%" }}
                    block
                    href="/createpoll"
                    >
                      Create Poll
                    </Button>
                  </div>
                  <UserPoll poll={this.props.state.user}/>
                </div>
              </div>
            </div>
          }
          {!this.props.state.user.isLoggedin &&
            <div>
            <h1 className="display-4" style={{ textAlign: "Left" }}>
                Poll Overview
            </h1>
            <hr className="text-dark bg-dark" />
              <div className="row">
                <div className="col-sm">
                  <div className="container" style={{width: "50%"}}>
                      <Poll poll={this.props.state.poll}/>
                  </div>
                </div>
              </div>
            </div>
          }  
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
    }),

    setResetPoll: () => dispatch({
      type: "RESET_POLL_DATA"
    }),

    setResetUserError: () => dispatch({
      type: "RESET_USER_ERROR",
      error: null
    })
  };
};


export default connect(mapStateToProps, mapDispatchToProps)(PublicPollOverview);
