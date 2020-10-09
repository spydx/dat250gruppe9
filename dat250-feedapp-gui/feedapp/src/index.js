import React from 'react';
import ReactDOM from 'react-dom';
import PollOverview from './components/PollComponents/PollOverview';

class Feedapp extends React.Component {
    render(){
        return (
            <div>
                <PollOverview></PollOverview>
                <PollOverview></PollOverview>
                <PollOverview></PollOverview>
            </div>
        );
    }
}


 
ReactDOM.render(<Feedapp />, document.getElementById("root"));

