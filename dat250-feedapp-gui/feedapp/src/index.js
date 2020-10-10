import React from 'react';
import ReactDOM from 'react-dom';
import PublicPollOverview from './components/PublicPollOverview';

class Feedapp extends React.Component {
    render(){
        return (
            <div>
                <PublicPollOverview/>
            </div>
        );
    }
}


ReactDOM.render(<Feedapp />, document.getElementById("root"));

