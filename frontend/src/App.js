import React from 'react';
import './Styles/App.css';
import LoggingForm from './User/LoggingForm';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import AccountActivation from './User/AccountActivation';
import UserProfileEdit from './User/UserProfileEdit';
import ResetPassword from './User/ResetPassword';
import ResetPasswordConfirm from './User/ResetPasswordConfim';
import UserTracks from './Track/UserTracks'
import UserTrackDetail from './Track/UserTrackDetail';
import SearchUser from './User/SearchUser';
import CustomizedSteppers from './User/RegisterStepper';
import RegistrationSuccess from './User/RegistrationSuccess';
import PostsView from './Posts/PostsView.js'
import AddPostView from './Posts/AddPostView.js'
import SimplePersonalPost from './Posts/SimplePersonalPost.js'
import EditPost from './Posts/EditPost.js'
import DeletePost from './Posts/DeletePost.js'
import MapMainView from './Map/MapMainView.js'



class App extends React.Component {
  render(){
    return(
      <div>
        <BrowserRouter>
          <Switch>
            <Route exact path="/" component={LoggingForm} />
            <Route path="/login" component={LoggingForm} />
            <Route path="/posts/:usersId" component={PostsView} />
            <Route path="/registration" component={CustomizedSteppers} />
            <Route path="/success/:email" component={RegistrationSuccess} />
            <Route path="/add-post" component={AddPostView} />
            <Route path="/simple/personal/post/:usersId/:postId" component={SimplePersonalPost} />
            <Route path="/edit/my-post/:usersId/:postId" component={EditPost} />
            <Route path="/delete/post/:postId" component={DeletePost} /> 
            <Route path="/map" component={MapMainView} /> 

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
