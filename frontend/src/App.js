import React from 'react';
import './Styles/App.css';
import AlternativeLoggingForm from './User/AlternativeLoggingForm';
import Registration from './User/Registration';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import AccountActivation from './User/AccountActivation';
import UserProfileEdit from './User/UserProfileEdit';
import UserTracks from './Track/UserTracks'
import UserTrackDetail from './Track/UserTrackDetail';


class App extends React.Component {
  render(){
    return(
      // <div className="row">
      <div>
        <BrowserRouter>
          <Switch>
            <Route exact path="/" component={AlternativeLoggingForm} />
            <Route path="/login" component={AlternativeLoggingForm} />
            <Route path="/registration" component={Registration} />
            <Route path="/activate/:uid/:token" component={AccountActivation} /> 
            <Route path="/profile-edit" component={UserProfileEdit} />
            <Route path="/tracks" component={UserTracks} />
            <Route path="/track/:id" component={UserTrackDetail} />
          </Switch>
        </BrowserRouter>
      </div>
    );
  }
}

export default App;
