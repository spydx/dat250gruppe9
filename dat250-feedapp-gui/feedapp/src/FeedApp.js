import React from 'react'
import Home from "./pages/Home";
import LoginPage from "./pages/LoginPage";
import Account from "./pages/Account";
import ResultPage from "./pages/ResultPage";
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import VotePage from './pages/VotePage';
import CreatePollPage from './pages/CreatePollPage'



class Feedapp extends React.Component {

    render() {
      return (
        <div> 
          
            <Router>
              <Switch>
                <Route exact path="/result/:id" render={(props) => (<ResultPage props={props}/>)}/>
                <Route exact path="/login" render={() => (<LoginPage />)}/>
                <Route exact path="/account" render={() => (<Account/>)}/>
                <Route exact path="/" render={() => (<Home/>)}/>  
                <Route exact path="/vote/:id" render={(props) => (<VotePage props={props}/>)}/>      
                <Route exact path="/createpoll" render={() => (<CreatePollPage/>)}/>     
              </Switch>   
            </Router>
          
        </div>
      );
    }
}
  
export default Feedapp;