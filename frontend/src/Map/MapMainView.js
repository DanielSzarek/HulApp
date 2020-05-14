import React, { Component } from 'react'
import { Map, GoogleApiWrapper, Marker } from 'google-maps-react'
import Navbarex from '../User/Navbar'
import AuthService from '../User/AuthService'
import { Redirect } from 'react-router-dom'

const mapStyles = {
  width: '100%',
  height: '86%'
}

export class MapMainView extends Component {
  constructor (props) {
    super(props)
    this.state = {
      userId: '',
      auth: true,
      points: []
    }
    this.Auth = new AuthService()
  }

  //    async componentDidMount() {
  //     if (await this.Auth.loggedIn()) {
  //         this.Auth.fetch('http://hulapp.pythonanywhere.com/auth/users/me/')
  //             .then((res) => {
  //                 this.setState({
  //                     userId: res.id,
  //                 });
  //             })
  //             .catch((error) => {
  //                 console.log({ message: "ERROR " + error });
  //             });
  //     }
  //     else {
  //         this.setState({ auth: false });
  //     }

  // }

  async componentDidMount () {
    if (await this.Auth.loggedIn()) {
      this.Auth.fetch('http://hulapp.pythonanywhere.com/auth/users/me/')
        .then(res => {
          this.setState({
            userId: res.id
          })
          this.Auth.fetch(`http://hulapp.pythonanywhere.com/api/point/`).then(
            r => {
              console.log(r)
              this.setState({
                points: r
              })
              this.state.points.map(point => console.log(point.latitude))
            }
          )
        })
        .catch(error => {
          console.log({ message: 'ERROR ' + error })
        })
    } else {
      this.setState({ auth: false })
    }
  }

  render () {
    return (
      <div>
        <Navbarex />
        {this.state.auth ? '' : <Redirect to='/' />}
        <Map
          google={this.props.google}
          zoom={11}
          style={mapStyles}
          initialCenter={{
            lat: 54.52158,
            lng: 18.538301
          }}
        >
          {this.state.points.map(point => (
            <Marker
              title={point.latitude}
              name={'SOMA'}
              position={{
                lat: parseFloat(point.latitude),
                lng: parseFloat(point.longitude)
              }}
            />
            // position={{lat: 54.52158, lng: 18.538301}} />
          ))}
        </Map>
      </div>
    )
  }
}

export default GoogleApiWrapper({
  apiKey: 'xxx'
})(MapMainView)
