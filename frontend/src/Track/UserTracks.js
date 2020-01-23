import React from 'react';
import Navbarex from '../User/Navbar';
import AuthService from '../User/AuthService';
import { ListGroup } from "react-bootstrap";
import TrackItem from "./TrackItem"


class UserTracks extends React.Component{

    constructor(props) {
        super(props);

        this.state = {
            tracks: [],
            auth: true
        }
        this.Auth = new AuthService();
    }

    async componentDidMount(){
        if ( await this.Auth.loggedIn()) {
          this.Auth.fetch('http://hulapp.pythonanywhere.com/api/tracks/')
          .then((response) => {
            this.setState({
                tracks: response
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
                <Navbarex/>
                <h4 style={{textAlign: "center"}}>Moje trasy</h4>
                <ListGroup style={{marginTop: "32px", position: "absolute"}}>
                    {
                        this.state.tracks.map(track => 
                            <ListGroup.Item>
                                <TrackItem data={track}/>
                            </ListGroup.Item>
                        )
                    }
                </ListGroup>
            </div>
        );
    }
} export default UserTracks;