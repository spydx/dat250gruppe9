import React from 'react';

class DateEnded extends React.Component {
    state={
        curTime : new Date().toLocaleString(),
    }
        render() {
        return (
            <div className="DateEnded">
            <p>Poll ended: {this.state.curTime}</p>
            </div>
        );
    }
}

export default DateEnded;
