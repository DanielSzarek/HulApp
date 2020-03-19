import React from 'react';
import { Card, CircularProgress } from "@material-ui/core";
import { withRouter, Redirect } from 'react-router-dom';
import AuthService from '../User/AuthService';
import Navbarex from '../User/Navbar';
import '../Styles/Tracks.css';
import Example from '../User/ShareOnFb'

import Facebook from 'react-sharingbuttons/dist/buttons/Facebook'
import Twitter from 'react-sharingbuttons/dist/buttons/Twitter'

class UserTrackDetail extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            id: props.match.params.id,
            track: {},
            auth: true,
            progressBarDisplayState: "visible"
        }

        this.Auth = new AuthService();
    }

    async componentDidMount(){
        if ( await this.Auth.loggedIn()) {
          this.Auth.fetch(`http://hulapp.pythonanywhere.com/api/track/${this.state.id}`)
          
          .then((response) => {
            this.setState({
                track: response,
                progressBarDisplayState: "none"
            })
        })
        .catch((error) => {
              console.log({message: "ERROR " + error});
          });
        }
        else{
            this.setState({auth: false});
        }
    }

    formatDateTime(dateString) {
        const date = new Date(dateString);
        return date.toLocaleDateString() + " " + date.toLocaleTimeString();
    }

    calculateAmountOfExhaustGas(length) {
        return length * 120;
    }

     sharingButtons = () => {
  const url = 'https://github.com/caspg/react-sharingbuttons'
  const shareText = 'Check this site!'

  return (
    <div>
      <Facebook url={url} />
      <Twitter url={url} shareText={shareText} />
    </div>
  )
}

    render() {
        return (
            <div>
                {(this.state.auth) ? '' : <Redirect to="/" />}
                <Navbarex />
                <div className="my-track-details">
                <CircularProgress style={{display: this.state.progressBarDisplayState, position: "absolute", marginLeft: "50%", marginTop: "100px"}} />
                { this.state && this.state.track.time_start &&
                    <Card style={{padding: "16px", marginTop: "32px"}}>
                        <h4>{this.formatDateTime(this.state.track.time_start)}</h4>
                        <p>Czas trwania: <b>{this.state.track.duration}</b> sekund</p>
                        <p>Odległość: <b>{this.state.track.track_length}</b> km</p>
                        <p>Ilość zaoszczędzonych spalin: <b>{this.calculateAmountOfExhaustGas(this.state.track.track_length)}</b> gram</p>
                        {/* <Example/> */}
                    </Card>
                }
                </div>
                <div className="share-success">
                <p>Podziel się sukcesem z przyjaciółmi </p>
                </div>
                {/* <p>Udostępnij</p> */}

                {/* <a href="https://stackoverflow.com/questions/26944634/you-must-provide-a-valid-privacy-policy-url-in-order-take-your-app-live-go-to-a" class="react-sharing-button__link react-sharing-button--twitter">
                <svg class="react-sharing-button__icon">...</svg>
                <span class="react-sharing-button__text">Share me</span>
                </a> */}
                <div className="share-fb-mod">
                 <Example/>
                </div>
            </div>
        );
    }
} export default withRouter(UserTrackDetail);