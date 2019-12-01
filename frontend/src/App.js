import React from 'react';
import './Styles/App.css';
import AlternativeLoggingForm from './User/AlternativeLoggingForm';
import Registration from './User/Registration';
import {  Link, BrowserRouter, Switch, Route } from 'react-router-dom';
import AccountActivation from './User/AccountActivation';
import NewPwdRequest from './User/NewPwdRequest';

class App extends React.Component {
  render(){
    return(
      <div className="row">
        <BrowserRouter>
          <Switch>
          <Route exact path="/" component={AlternativeLoggingForm}/>
            <Route path="/registration" component={Registration}/>
            <Route path="/activate/:uid/:token" component={AccountActivation}/> 
            <Route path="/forgotten-password" component={NewPwdRequest}/>
          </Switch>
        </BrowserRouter>
      </div>
    );
  }
}

export default App;
