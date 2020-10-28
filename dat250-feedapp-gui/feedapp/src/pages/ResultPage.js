import React from "react";
import Result from "../components/Result";

class ResultPage extends React.Component {
  render() {
    return (
      <div>
        <Result id={this.props.props.match.params.id}/>
      </div>
    );
  }
}

export default ResultPage;
