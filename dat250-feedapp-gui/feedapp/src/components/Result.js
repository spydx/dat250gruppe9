import React from "react";
import Card from "react-bootstrap/Card";
import Spinner from "react-bootstrap/Spinner";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/css/bootstrap.css";
import NavBar from "../components/NavBar";
import Button from 'react-bootstrap/Button'
import { connect } from "react-redux";

class Result extends React.Component {

  getQuestion(resultID) {
    console.log("hei")
    for (const element of this.props.state.poll.pollData) {
      console.log(element);
    } 
  }

  fetchResultData() {
    fetch("http://localhost:8080/api/" + this.props.url)
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
      this.getQuestion(this.props.state.result.resultData.id)
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
        </div>
          <h2 style={{textAlign: "center", marginTop: "2%"}}>
            <p><u>Result: "Spørsmål må inn her" </u></p>
          </h2>
        <div>
          <div key={resultData.id} class="mt-2">
            <Card  text={"dark"} className="mb-2" style={{width:"40%", marginLeft: "30%"}}>
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
            onClick={() => {
                        alert("Returning to poll-overview");
                      }}
            variant="danger" 
            style = {{ marginLeft: "70%", marginTop: "5%"}}
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
