import React from "react";
import Card from "react-bootstrap/Card";
import Spinner from "react-bootstrap/Spinner";
import Button from "react-bootstrap/Button";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/css/bootstrap.css";
import PollStatus from "./PollStatus";

class PollFromAPI extends React.Component {

  getdate(startDate, endDate) { //TODO: change this to the same as getStatus from PollStatus.js
    var currentDate = new Date(Date.now());
    startDate = new Date(startDate);
    endDate = new Date(endDate);
    var started = currentDate > startDate;
    var ended = currentDate > endDate && endDate.getFullYear() !== 1970;
    if (started && !ended) {
      if (endDate.getFullYear() === 1970) return "On Going, no endtime set";
      else
        return (
          "On Going, poll ends in " +
          Math.floor((endDate - currentDate) / 1000 / 60) +
          " minutes"
        );
    }
    if (!started) {
      return (
        "Starting in " +
        Math.floor((endDate - startDate) / 1000 / 60) +
        " minutes"
      );
    }
    if (ended) return "Ended";
    return "What???";
  }

  render() {
    const { error, isLoaded, pollData } = this.props.poll;

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
        {pollData.map((poll) => (
          
          <div key={poll.id} className="mt-2">
            { this.getdate(poll.timestart, poll.timeend) !== "Ended" &&
              
              <Card text={"dark"} className="mb-2" style={{width: "70%", marginLeft: "25%"}}>
                <h1 style={{ textAlign: "Left", fontSize: "140%", marginTop: "1%", marginLeft: "1%"}}>
                  <PollStatus poll={poll}/>
                </h1>
                <h3 className="font-weight-light" style={{ textAlign: "center" }}>
                  {poll.question}
                </h3>
                <div className="container">
                  <div className="row">
                    <div className="col-sm" style={{textAlign: "right"}}>
                      <Button
                        variant="success"
                        style={{ width: "20%", marginLeft: "83%", marginBottom: "1%" }}
                        block
                        href={"/vote/" + poll.id}
                      >
                        Vote
                      </Button>
                    </div>
                  </div>
                </div>
              </Card>

            }
            { this.getdate(poll.timestart, poll.timeend) === "Ended" &&
            
              <Card text={"dark"} className="mb-2" style={{width: "70%", marginLeft: "25%"}}>
                <h1 style={{ textAlign: "Left", fontSize: "140%", marginTop: "1%", marginLeft: "1%"}}>
                  <PollStatus poll={poll}/>
                </h1>
                <h3 className="font-weight-light" style={{ textAlign: "center" }}>
                  {poll.question}
                </h3>
                <div className="container">
                  <div className="row">
                    <div className="col-sm" style={{textAlign: "right"}}>
                      <Button
                        variant="info"
                        style={{ width: "25%", marginLeft: "78%", marginBottom: "1%"}}
                        block
                        href={"/result/" + poll.id}
                      >
                        Result
                      </Button>
                    </div>
                  </div>
                </div>
              </Card>
            }
            
          </div>
        ))}
      </div>
    );
  }
}

export default PollFromAPI;

