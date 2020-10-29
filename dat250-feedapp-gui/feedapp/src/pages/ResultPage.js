import React from "react";
import Result from "../components/Result";
import ResultGraph from "../components/ResultGraph"

class ResultPage extends React.Component {
  render() {
    return (
      <div>
        <Result id={this.props.props.match.params.id}/>
        <ResultGraph/>
      </div>
    );
  }
}

export default ResultPage;
