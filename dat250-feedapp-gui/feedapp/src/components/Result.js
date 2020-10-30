import React from "react";
import Spinner from "react-bootstrap/Spinner";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/css/bootstrap.css";
import Button from 'react-bootstrap/Button'
import { connect } from "react-redux";
import Divider from '@material-ui/core/Divider'
import ResultGraph from "./ResultComponents/ResultGraph"
import PollStatus from "./PollComponents/PollStatus"

class Result extends React.Component {

  
  getPoll(pollid) {
    for (const element of this.props.state.poll.pollData) {
      if (element.id === pollid) {
        return element;
      }
    } 
  }

  fetchResultData() {
    fetch("http://localhost:8080/api/result/" + this.props.id)
      .then((res) => res.json())
      .then(
        (result) => {
          this.props.setData(result);
          },

        (error) => {
          this.props.setError(error)
            
        }
      );
  }

  render() {
    const poll = this.getPoll(this.props.id)
    if (!this.props.state.result.isLoaded) {
      this.fetchResultData();
    }

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
              <p style={{textAlign: "center", marginTop: "2%"}} className="display-4">
                <small>{poll.question}</small>
              <Divider variant="middle"/>
              </p>

              <div class="container">
                <div class="row">
                  <div class="col" style={{ textAlign: "left", fontSize:"140%" }}>
                    <div style={{textAlign:"right"}}>
                      <PollStatus poll={poll}/>
                    </div>
                    <br></br><br></br>
                    <p>
                      {"Number of Votes: " + resultData.total} 
                    </p>
                    <p>
                      {"Number of Yes: " + resultData.yes}
                    </p>
                    <p>
                      {"Number of No: " + resultData.nos} 
                    </p>

                    <br></br>

                    <p>
                      {"Winner:"}
                    </p>
                  </div>
                  <div class="col">
                    <ResultGraph />
                    <Button 
                      variant="dark" 
                      style={{ marginLeft: "65%", marginTop: "8%", marginRight:"5%" }}
                      href = "/"
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
    state: state
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    setError: (error) => dispatch({
        type: "SET_RESULT_ERROR",
        error: error,
        isLoaded: true,
      }),

    setData: (result) => dispatch({
        type: "SET_RESULTDATA",
        isLoaded: true,
        resultData: result,
      })
  };
};



export default connect(mapStateToProps, mapDispatchToProps)(Result);
