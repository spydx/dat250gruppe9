import React from 'react';
import Badge from 'react-bootstrap/Badge'
import EventIcon from '@material-ui/icons/Event';

class PollStatus extends React.Component {
    getStatus(startDate, endDate) {
        var currentDate = new Date(Date.now());
        startDate = new Date(startDate);
        endDate = new Date(endDate);
        var ended = currentDate > endDate && endDate.getFullYear() !== 1970;
        return ended

    }

    render() {
        const ended = this.getStatus(this.props.poll.timestart, this.props.poll.timeend)
        if (ended) {
            return (
                <div>
                    <Badge variant="danger">Closed</Badge> 
                    {this.props.poll.timeend}
                    <EventIcon/>
                </div>
            );
        } else {
            return (
                <div>
                    <Badge variant="success">Live</Badge>
                </div>
            );
        }

        
    }
}

export default PollStatus;
