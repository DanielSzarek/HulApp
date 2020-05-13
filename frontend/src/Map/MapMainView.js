import React, { Component } from 'react';
import { Map, GoogleApiWrapper } from 'google-maps-react';
import Navbarex from '../User/Navbar';
import AuthService from '../User/AuthService';
import { Redirect } from 'react-router-dom';


const mapStyles = {
  width: '100%',
  height: '86%'
};



export class MapMainView extends Component {
        constructor(props) {
        super(props);
        this.state = {
            userId: '',
            auth: true,
        };
        this.Auth = new AuthService();
    }

       async componentDidMount() {
        if (await this.Auth.loggedIn()) {
            this.Auth.fetch('http://hulapp.pythonanywhere.com/auth/users/me/')
                .then((res) => {
                    this.setState({
                        userId: res.id,
                    });
                })
                .catch((error) => {
                    console.log({ message: "ERROR " + error });
                });
        }
        else {
            this.setState({ auth: false });
        }
    }

  render() {
    return (
        <div>
        <Navbarex />
        {(this.state.auth) ? '' : <Redirect to="/" />}
      <Map
        google={this.props.google}
        zoom={11}
        style={mapStyles}
        initialCenter={{
      lat: 54.521580,
      lng: 18.538301
        }}
      />
      </div>
    );
  }
}

export default GoogleApiWrapper({
  apiKey: 'XXXX'
})(MapMainView);