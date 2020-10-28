import React from "react";
import Card from "react-bootstrap/Card";
import Spinner from "react-bootstrap/Spinner";
import Button from "react-bootstrap/Button";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/css/bootstrap.css";

class PollFromAPI extends React.Component {

  getdate(startDate, endDate) {
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
            <Card bg={"light"} text={"dark"} className="mb-2">
              <h1 className="display-4" style={{ textAlign: "center" }}>
                {poll.name}
              </h1>
              <h3 className="font-weight-light" style={{ textAlign: "center" }}>
                {poll.question}
              </h3>
              <div className="container">
                <div className="row">
                  <div className="col-sm">
                    <Button
                      variant="info"
                      style={{ width: "90%", marginLeft: "5%" }}
                      block
                      href={"/result/" + poll.id}
                    >
                      Result
                    </Button>
                  </div>
                  <div className="col-sm">
                    <Button
                      variant="success"
                      style={{ width: "90%", marginRight: "5%" }}
                      block
                      href={"/vote/" + poll.id}
                    >
                      Vote
                    </Button>
                  </div>
                </div>
              </div>
              <Card
                bg={"dark"}
                text={"light"}
                style={{ width: "50%", marginLeft: "25%" }}
                className="mb-3 mt-3"
              >
                <h6 style={{ textAlign: "center" }}>
                  <small>
                    Poll status: {this.getdate(poll.timestart, poll.timeend)}
                  </small>
                </h6>
              </Card>
            </Card>
          </div>
        ))}
      </div>
    );
  }
}

export default PollFromAPI;

