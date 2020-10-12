import React from "react";
import Card from "react-bootstrap/Card";
import Spinner from "react-bootstrap/Spinner";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/css/bootstrap.css";

class PollFromAPI extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      error: null,
      isLoaded: false,
      pollData: [],
    };
  }

  getdate(startDate, endDate) {
    var currentDate = new Date(Date.now());
    startDate = new Date(startDate);
    endDate = new Date(endDate);
    var started = currentDate > startDate;
    var ended = currentDate > endDate && endDate.getFullYear() !== 1970;
    if (started && !ended) {
      if (endDate.getFullYear() == 1970) return "On Going, no endtime set";
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

  componentDidMount() {
    fetch("http://localhost:8080/api/polls/")
      .then((res) => res.json())
      .then(
        (result) => {
          this.setState({
            isLoaded: true,
            pollData: result,
          });
        },
        (error) => {
          this.setState({
            isLoaded: true,
            error,
          });
        }
      );
  }

  render() {
    const { error, isLoaded, pollData } = this.state;
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
          <div key={poll.id} class="mt-2">
            <button type="button" className="btn btn-light btn-lg btn-block">
              <h1 class="display-4">{poll.name}</h1>
              <p class="font-weight-light">{poll.question}</p>
              <Card
                bg={"light"}
                text={"dark"}
                style={{ width: "90%", marginLeft: "5%" }}
                className="mb-2"
              >
                <h6 class="text-dark">{poll.answeryes}</h6>
                <h6 class="text-dark">{poll.answerno}</h6>
              </Card>
              <Card
                bg={"dark"}
                text={"light"}
                style={{ width: "90%", marginLeft: "5%" }}
                className="mb-2"
              >
                <h6>
                  <small>
                    Poll status: {this.getdate(poll.timestart, poll.timeend)}
                  </small>
                </h6>
              </Card>
            </button>
          </div>
        ))}
      </div>
    );
  }
}

export default PollFromAPI;
