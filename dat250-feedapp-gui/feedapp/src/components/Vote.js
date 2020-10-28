import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/css/bootstrap.css";
import Card from "react-bootstrap/Card";
import Button from 'react-bootstrap/Button'
import { connect } from "react-redux";



class Vote extends React.Component {

    getPoll(pollid) {
        
        for (const element of this.props.state.poll.pollData) {
          if (element.id === pollid) {
            return element;
          }
        } 
      }

    render() {
        console.log("polls",this.props.state.poll.pollData )
        const poll = this.getPoll(this.props.id)
        return(
            <div>
                <div>
                    <h1 style={{ textAlign: "center", marginTop: "2rem" }}><u>Vote</u></h1>
                </div>
                <div style={{margin: "5%"}}>
                        <h3 className="font-weight-light" style={{ textAlign: "center", marginTop: "5%" }}>
                            Voting on: {poll.question}
                        </h3>
                        <div className="container">
                            <div className="row">
                                <div className="col-sm" style = {{marginTop:"8%"}}>
                                    <Button
                                        variant = "success"
                                        style = {{width: "35%", marginLeft: "30%"}}
                                        onClick = {() => {
                                        alert("Vote yes");
                                    }}
                                    >
                                        Yes
                                    </Button>
                                </div>
                                <div className="col-sm" style = {{marginTop:"8%"}}>
                                    <Button
                                        variant = "danger"
                                        style = {{width: "35%", marginLeft:"23%"}}
                                        onClick = {() => {
                                            alert("Vote no");
                                        }}
                                    >
                                        No
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
    return {
      
    };
  };
  
  
  
  export default connect(mapStateToProps, mapDispatchToProps)(Vote);