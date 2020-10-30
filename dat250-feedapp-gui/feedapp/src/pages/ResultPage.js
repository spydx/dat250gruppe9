import React from "react";
import NavBar from "../components/NavBar";
import Result from "../components/Result";



class ResultPage extends React.Component {
  render() {
    return (
      <div >
        <div>
          <NavBar/>
        </div>
        <div>
          <Result id={this.props.props.match.params.id}/>
        </div>
        
      </div>
    );
  }
}

export default ResultPage;
