import React from "react";
import Result from "../components/Result";

class ResultPage extends React.Component {
  render() {
    console.log("this is props: ",this.props)
    return (
      <div>
        <Result id={this.props.props.match.params.id}/>
      </div>
    );
  }
}

export default ResultPage;
