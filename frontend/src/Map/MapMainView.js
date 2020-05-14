import React, { Component } from 'react'
import { Map, GoogleApiWrapper, Marker, InfoWindow } from 'google-maps-react'
import Navbarex from '../User/Navbar'
import AuthService from '../User/AuthService'
import { Redirect } from 'react-router-dom'
import StarIcon from '@material-ui/icons/Star'
import NavigateNextIcon from '@material-ui/icons/NavigateNext'

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
      points: [],
      showingInfoWindow: false,
      activeMarker: {},
      selectedPlace: {},
      pointRating: '',
      authorName: '',
      authorSurname: '',
      authorProfileImg: '',
      pointById: []
    }
    this.Auth = new AuthService()
  }

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

  onMarkerClick = (props, marker, e) => {
    this.setState({
      selectedPlace: props,
      activeMarker: marker,
      showingInfoWindow: true
    })
    this.Auth.fetch(
      'http://hulapp.pythonanywhere.com/api/point/' +
        this.state.activeMarker.label
    ).then(response => {
      console.log(response.author.first_name)
      this.setState({ pointById: response })
    })
    console.log(marker)
    console.log(this.state.showingInfoWindow)
    console.log(this.state.pointById.rating)
    console.log('minus: ' + (Number(5) - this.state.pointById.rating))
  }

  onMapClicked = props => {
    if (this.state.showingInfoWindow) {
      this.setState({
        showingInfoWindow: false,
        activeMarker: null
      })
    }
  }

  render () {
    return (
      <div>
        <Navbarex />
        {this.state.auth ? '' : <Redirect to='/' />}
        <Map
          google={this.props.google}
          onClick={this.onMapClicked}
          zoom={11}
          initialCenter={{
            lat: 54.52158,
            lng: 18.538301
          }}
        >
          {this.state.points.map(point => (
            <Marker
              name={point.description}
              label={String(point.id)}
              title={point.name}
              description={point.description}
              onClick={this.onMarkerClick}
              position={{
                lat: point.latitude,
                lng: point.longitude
              }}
            />
          ))}

          {this.state.showingInfoWindow && (
            <InfoWindow
              marker={this.state.activeMarker}
              visible={this.state.showingInfoWindow}
              style={{ width: '200px' }}
            >
              <div>
                <h4>{this.state.activeMarker.title}</h4>
                <h5>{this.state.activeMarker.name}</h5>
                <div style={{ display: 'inline' }}>
                  {Array.from(Array(this.state.pointById.rating)).map(() => (
                    <StarIcon />
                  ))}
                  <h5 style={{ display: 'inline' }}>/ 5</h5>
                </div>
              </div>
              {/* <div>
                {' '}
                <button
                  style={{
                    backgroundColor: 'red',
                    border: 'none',
                    fontWeight: 'bold',
                    marginLeft: '30%',
                    marginTop: '10px'
                  }}
                >
                  <NavigateNextIcon /> Zobacz
                </button>{' '}
              </div> */}
            </InfoWindow>
          )}
        </Map>
      </div>
    )
  }
}

export default GoogleApiWrapper({
  apiKey: 'xxx'
})(MapMainView)
