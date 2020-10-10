import React from 'react';
import Votebutton from './Votebutton';
import PollStatus from './PollStatus';
import Question from './Question';


const PollOverviewStyle = {
    border: "1px solid black",
    margin: "25px 800px",
    padding: "5px"
}

const votebuttonStyle = {
    textAlign: "right"
}
const QuestionStyle = {
    textAlign: "center"
}

class Poll extends React.Component {
    render() {
        return (
            <div style={PollOverviewStyle}>
                <PollStatus></PollStatus>
                <div style={QuestionStyle}>
                    <Question></Question>
                </div>
                
                <div style ={votebuttonStyle}>
                    <Votebutton></Votebutton>
                </div>
                
            </div>
        );
    }
}

export default Poll;
