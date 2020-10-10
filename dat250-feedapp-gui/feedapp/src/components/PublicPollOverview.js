import React from 'react';
import PollList from './PollComponents/PollList';

class PublicPollOverview extends React.Component {
    render() {
        return (
            <div>
                <div>
                    <h1>Public Poll Overview</h1>
                </div>
                <div>
                    <PollList />
                </div>
            </div>
        );
    }
}

export default PublicPollOverview;