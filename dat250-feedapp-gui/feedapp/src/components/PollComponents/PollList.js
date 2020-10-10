import React from 'react';
import Poll from './Poll';

class PollList extends React.Component {
    
    render() {
        return (
            <div>
                <Poll></Poll>
                <Poll></Poll>
                <Poll></Poll>
            </div>
        );
    }
}

export default PollList;