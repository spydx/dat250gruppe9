import React from 'react';
import Votebutton from './Votebutton';
import DateEnded from './DateEnded';

class PollOverview extends React.Component {
    render() {
        return (
            <div>
                <DateEnded></DateEnded>
                <Votebutton></Votebutton>
            </div>
        );
    }
}

export default PollOverview;
