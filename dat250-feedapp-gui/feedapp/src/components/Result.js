import React from "react";
import Card from "react-bootstrap/Card";
import Spinner from "react-bootstrap/Spinner";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/css/bootstrap.css";
import NavBar from "../components/NavBar";
import Button from 'react-bootstrap/Button'
import { connect } from "react-redux";

class Result extends React.Component {

  getQuestion(pollid) {
    for (const element of this.props.state.poll.pollData) {
      if (element.id === pollid) {
        return element.question
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
          <NavBar/>
        
          <div key={resultData.id} class="mt-2">
            <Card  text={"dark"} className="mb-2" style={{width:"40%", marginLeft: "30%"}}>
            
              <p style={{textAlign: "center", marginTop: "2%"}} class="display-1">
              <p><u> {this.getQuestion(this.props.id)} </u></p>
              </p>
            
              <p class="display-1" style={{ textAlign: "center" }}>
                <small> {"Total votes: " + resultData.total} </small>
              </p>
              <p class="display-1" style={{ textAlign: "center" }}>
                <small> {"Yes: " + resultData.yes} </small>
              </p>
              <p class="display-1" style={{ textAlign: "center" }}>
              <small> {"No: " + resultData.nos} </small>
              </p>
            </Card>
            </div>
        </div>
          <Button 
            variant="danger" 
          style={{ marginLeft: "70%", marginTop: "5%" }}
          href = "/"
            >
            Return to overview
          </Button>
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
        type: "SET_RESULTDATA",
        isLoaded: true,
        resultData: result,
      })
  };
};



export default connect(mapStateToProps, mapDispatchToProps)(Result);
