import React from "react";
import Card from "react-bootstrap/Card";
import Spinner from "react-bootstrap/Spinner";
import Button from "react-bootstrap/Button";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/css/bootstrap.css";
import PollStatus from "./PollStatus";

class Poll extends React.Component {

  getStatus(startDate, endDate) {
    var currentDate = new Date(Date.now());
    startDate = new Date(startDate);
    endDate = new Date(endDate);
    var ended = currentDate > endDate && endDate.getFullYear() !== 1970;
    return ended
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
            { !this.getStatus(poll.timestart, poll.timeend) &&
              
              <Card text={"dark"} className="mb-2" style={{textAlign: "center"}}>
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
                        style={{ width: "20%", marginLeft: "80%", marginBottom: "1%" }}
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
            { this.getStatus(poll.timestart, poll.timeend) &&
            
              <Card text={"dark"} className="mb-2" style={{ textAlign: "center"}}>
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
                        style={{ width: "20%", marginLeft: "80%", marginBottom: "1%", backgroundColor: "#2196F3"}}
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

export default Poll;

