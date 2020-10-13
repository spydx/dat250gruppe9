import React from "react";
import Card from "react-bootstrap/Card";
import Spinner from "react-bootstrap/Spinner";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/css/bootstrap.css";

class Result extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      error: null,
      isLoaded: false,
      resultData: [],
    };
  }

  componentDidMount() {
    fetch("http://localhost:8080/api/" + this.props.url)
      .then((res) => res.json())
      .then(
        (result) => {
          this.setState({
            isLoaded: true,
            resultData: result,
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
    const { error, isLoaded, resultData } = this.state;
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
        <div key={resultData.id} class="mt-2">
          <Card bg={"light"} text={"dark"} className="mb-2">
            <h1 class="display-1" style={{ textAlign: "center" }}>
              {"Total:" + resultData.total}
            </h1>
            <h1 class="display-1" style={{ textAlign: "center" }}>
              {"Yes:" + resultData.yes}
            </h1>
            <h1 class="display-1" style={{ textAlign: "center" }}>
              {"No:" + resultData.nos}
            </h1>
          </Card>
        </div>
      </div>
    );
  }
}

export default Result;
