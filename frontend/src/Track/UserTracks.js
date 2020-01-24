import React from 'react';
import Navbarex from '../User/Navbar';
import AuthService from '../User/AuthService';
import { ListGroup } from "react-bootstrap";
import TrackItem from "./TrackItem"
import { Link, Redirect } from 'react-router-dom';
import { CircularProgress } from "@material-ui/core";


class UserTracks extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            tracks: [],
            auth: true,
            progressBarDisplayState: "visible"
        }
        this.Auth = new AuthService();
    }

    async componentDidMount(){
        if ( await this.Auth.loggedIn()) {
          this.Auth.fetch('http://hulapp.pythonanywhere.com/api/tracks/')
          .then((response) => {
            this.setState({
                tracks: response,
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

    render() {
        return(
            <div>
                {(this.state.auth) ? '' : <Redirect to="/" />}
                <Navbarex />
                <CircularProgress style={{display: this.state.progressBarDisplayState, position: "absolute", marginLeft: "50%", marginTop: "100px"}} />
                <h3 style={{marginTop: "10px", textAlign: "center"}}>Moje trasy</h3>
                <ListGroup style={{ marginTop: "32px"}}>
                    {
                        this.state.tracks.map(track => 
                            <Link to={`/track/${track.id}`} key={track.id} style={{marginTop: "16px"}}>
                                <ListGroup.Item style={{color: "black"}}>
                                    <TrackItem data={track}/>
                                </ListGroup.Item>
                            </Link>
                        )
                    }
                </ListGroup>
            </div>
        );
    }
} export default UserTracks;