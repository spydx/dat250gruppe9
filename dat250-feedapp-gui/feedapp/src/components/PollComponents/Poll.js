import React from 'react';
import Votebutton from './Votebutton';
import PollStatus from './PollStatus';
import Question from './Question';
import Card from 'react-bootstrap/Card';

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
            <Card style={{ width: '25rem', marginLeft: "0.5rem", marginBottom: "1rem"}}>
                <PollStatus />
                <Question />
                <Votebutton />
            </Card>
        );
    }
}

export default Poll;
