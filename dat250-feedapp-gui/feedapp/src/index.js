import React from 'react';
import ReactDOM from 'react-dom';
import PublicPollOverview from './components/PublicPollOverview';
import LoginPage from './pages/LoginPage'
import "bootstrap/dist/css/bootstrap.css";
import NavBar from "./components/NavBar";

class Feedapp extends React.Component {
    render(){
        return (
            <div>
                <LoginPage/>
            </div>
        );
    }
}

ReactDOM.render(<Feedapp />, document.getElementById("root"));
