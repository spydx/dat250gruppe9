import React from 'react';

class PollStatus extends React.Component {
    state={
        curTime : new Date().toLocaleString(),
    }
        render() {
        return (
            <div className="PollStatus">
            <p>Poll ended: {this.state.curTime}</p>
            </div>
        );
    }
}

export default PollStatus;
