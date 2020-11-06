import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/css/bootstrap.css";
import Button from 'react-bootstrap/Button'
import { connect } from "react-redux";
import { Post } from "../utils/actionHandler";
import { API_URL } from "../constants/constants"

class Vote extends React.Component {

    getPoll(pollid) {
        
        for (const element of this.props.state.poll.pollData) {
            if (element.id === pollid) {
            return element;
            }
        } 
    }

    async handleSubmit(answer) {
        const voteRequest = {
            answer: answer
        }
        if (this.props.state.user.isLoggedin) {
            await Post(API_URL + "/polls/" + this.props.id + "/vote/", voteRequest, this.props.state.user.token)
            
        } else {
            await Post(API_URL + "/polls/" + this.props.id + "/vote/", voteRequest)
            
        }
    }

    render() {
        const poll = this.getPoll(this.props.id)
        return(
            <div>
                <div>
                    <h1 style={{ textAlign: "center", marginTop: "2rem" }}><u>Vote</u></h1>
                </div>
                <div style={{margin: "5%", width: "500px"}}>
                        <h3 className="font-weight-light" style={{ textAlign: "center", marginTop: "5%" }}>
                            Voting on: {poll.question}
                        </h3>
                        <div className="container">
                            <div className="row">
                                <div className="col-sm" style = {{marginTop:"8%"}}>
                                    <Button
                                        variant = "success"
                                        style = {{width: "35%", marginLeft: "30%"}}
                                        onClick={() => { this.handleSubmit(true) }}
                                        href={ "/result/" + this.props.id }
                                    >
                                        {poll.answeryes}
                                    </Button>
                                </div>
                                <div className="col-sm" style = {{marginTop:"8%"}}>
                                    <Button
                                        variant = "danger"
                                        style = {{width: "35%", marginLeft:"23%"}}
                                        onClick={() => { this.handleSubmit(false) }}
                                        href={ "/result/" + this.props.id }
                                    >
                                        {poll.answerno}
                                    </Button>
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
      state: state
    };
  };
  
  const mapDispatchToProps = (dispatch) => {
    return {};
  };
  
  
  
  export default connect(mapStateToProps, mapDispatchToProps)(Vote);