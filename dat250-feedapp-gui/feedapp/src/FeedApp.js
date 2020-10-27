import React from 'react'
import Home from "./pages/Home";
import LoginPage from "./pages/LoginPage";
import ResultPage from "./pages/ResultPage";
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'



class Feedapp extends React.Component {

    render() {
      return (
        <div> 
          
            <Router>
              <Switch>
              <Route exact path="/result/:id" render={(props) => (<ResultPage props={props}/>)}/>
                  <Route exact path="/login" render={() => (<LoginPage />)}/>
                  <Route exact path="/" render={() => (<Home/>)}/>            
              </Switch>   
            </Router>
          
        </div>
      );
    }
}
  
export default Feedapp;