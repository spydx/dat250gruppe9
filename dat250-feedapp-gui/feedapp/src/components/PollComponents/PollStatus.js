import React from 'react';
import Badge from 'react-bootstrap/Badge'
import EventIcon from '@material-ui/icons/Event';
import moment from 'moment';

class PollStatus extends React.Component {

    getStatus(endDate) {//TODO: this might be broken
        var currentDate = new Date();
        endDate = new Date(endDate);
        var ended = currentDate > endDate && endDate.getFullYear() !== 1970;
        return ended

    }

    render() {
        const ended = this.getStatus(this.props.poll.timeend)
        var date = this.props.poll.timeend
        date = moment(date).format("DD-MM-YYYY");

        if (ended) {
            return (
                <div>
                    {date}
                    <EventIcon/>
                    <Badge variant="danger">Closed</Badge> 
                </div>
            );
        } else {
            return (
                <div>
                    {date}
                    <EventIcon/>
                    <Badge variant="success">Live</Badge>
                </div>
            );
        }

        
    }
}

export default PollStatus;
