import React from 'react';
import './Styles/App.css';
import AlternativeLoggingForm from './User/AlternativeLoggingForm';
import Registration from './User/Registration';
import {  Link, BrowserRouter, Switch, Route } from 'react-router-dom';
import AccountActivation from './User/AccountActivation';
import UserProfileEdit from './User/UserProfileEdit';
import Navbarex from './User/Navbar';
import ResetPassword from './User/ResetPassword';
import ResetPasswordConfirm from './User/ResetPasswordConfim';


class App extends React.Component {
  render(){
    return(
      // <div className="row">
      <div>
        <BrowserRouter>
          <Switch>
          <Route exact path="/" component={AlternativeLoggingForm}/>
          <Route  path="/login" component={AlternativeLoggingForm}/>
            <Route path="/registration" component={Registration}/>
            <Route path="/activate/:uid/:token" component={AccountActivation}/> 
            <Route path="/profile-edit" component={UserProfileEdit}/> 
            <Route path="/reset-password" component={ResetPassword}/>
            <Route path="/password/reset/confirm/:uid/:token" component={ResetPasswordConfirm}/>


          </Switch>
        </BrowserRouter>
      </div>
    );
  }
}

export default App;
