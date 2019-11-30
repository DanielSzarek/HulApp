import React from 'react';
import './Styles/App.css';
import LoggingForm from './User/AlternativeLoggingForm';
import Registration from './User/Registration';
import {  Link, BrowserRouter, Switch, Route } from 'react-router-dom';
import AccountActivation from './User/AccountActivation';

class App extends React.Component {
  render(){
    return(
      <div className="row">
        <BrowserRouter>
          <div className="offset-md-4 col-12 col-md-4">
          <LoggingForm/>
          </div>
          <Switch>
            <Route path="/registration" component={Registration}/>
            <Route path="/activate/:uid/:token" component={AccountActivation}/> 
          </Switch>
        </BrowserRouter>


      </div>
    );
  }
}

export default App;
