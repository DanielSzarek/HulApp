import React from 'react';
import './Styles/App.css';
import AlternativeLoggingForm from './User/AlternativeLoggingForm';
import Registration from './User/Registration';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import AccountActivation from './User/AccountActivation';
import UserProfileEdit from './User/UserProfileEdit';
import ResetPassword from './User/ResetPassword';
import ResetPasswordConfirm from './User/ResetPasswordConfim';
import UserTracks from './Track/UserTracks'
import UserTrackDetail from './Track/UserTrackDetail';
import SearchUser from './User/SearchUser';
import CustomizedSteppers from './User/nowy';
import RegistrationSuccess from './User/RegistrationSuccess';



class App extends React.Component {
  render(){
    return(
      <div>
        <BrowserRouter>
          <Switch>
            <Route exact path="/" component={AlternativeLoggingForm} />
            <Route path="/login" component={AlternativeLoggingForm} />
            {/* <Route path="/registration" component={Registration} /> */}
            <Route path="/registration" component={CustomizedSteppers} />
            <Route path="/success" component={RegistrationSuccess} />

            <Route path="/activate/:uid/:token" component={AccountActivation} /> 
            <Route path="/profile-edit" component={UserProfileEdit} /> 
            <Route path="/reset-password" component={ResetPassword} />
            <Route path="/password/reset/confirm/:uid/:token" component={ResetPasswordConfirm} />
            <Route path="/tracks" component={UserTracks} />
            <Route path="/track/:id" component={UserTrackDetail} />
            <Route path="/user/:userId" component={SearchUser} />
          </Switch>
        </BrowserRouter>
      </div>
    );
  }
}

export default App;
