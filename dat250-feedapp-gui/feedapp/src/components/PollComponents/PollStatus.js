import React from 'react';
import Badge from 'react-bootstrap/Badge'

class PollStatus extends React.Component {
    state={
        curTime : new Date().toLocaleString(),
    }
        render() {
        return (
            <div className="PollStatus">
            <p>
                Poll ended: {this.state.curTime} 
                <Badge variant="info">ended</Badge>
            </p>
            </div>
        );
    }
}

export default PollStatus;
