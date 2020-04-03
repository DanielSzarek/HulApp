import React from 'react'
import Navbarex from '../User/Navbar'
import AuthService from '../User/AuthService'
import { ListGroup } from 'react-bootstrap'
import { Link, Redirect } from 'react-router-dom'
import { CircularProgress } from '@material-ui/core'
import '../Styles/PostView.css'
import Avatar from '@material-ui/core/Avatar'
import { Container, Row, Col } from 'react-bootstrap'

class AddPostView extends React.Component {
  constructor (props) {
    super(props)
    this.state = {
      name: '',
      surname: '',
      src: '',
      auth: true,
      progressBarDisplayState: 'visible',
      content: '',
      username: ''
    }
    this.Auth = new AuthService()
    this.handleChange = this.handleChange.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  async componentDidMount () {
    if (await this.Auth.loggedIn()) {
      this.Auth.fetch('http://hulapp.pythonanywhere.com/auth/users/me/')
        .then(response => {
          this.setState({
            name: response.first_name,
            surname: response.last_name,
            src: response.profile_img,
            progressBarDisplayState: 'none',
            username: response.username
          })
        })
        .catch(error => {
          console.log({ message: 'ERROR ' + error })
        })
    } else {
      this.setState({ auth: false })
    }
  }

  handleChange (event) {
    this.setState({
      [event.target.name]: event.target.value
    })
  }

  handleSubmit = event => {
    event.preventDefault()
    this.Auth.fetch('http://hulapp.pythonanywhere.com/api/post/', {
      method: 'POST',

      body: JSON.stringify({
        first_name: this.state.name,
        last_name: this.state.surname,
        text: this.state.content,
        username: this.state.username
      })
    }).then(response => {
      if (response.status >= 200 && response.status < 300) {
        return response.json()
      }
    })
  }

  render () {
    return (
      <div>
        {this.state.auth ? '' : <Redirect to='/' />}
        <Navbarex />
        <div>
          <h1>dodaj post</h1>
          <hr />
        </div>

        <CircularProgress
          style={{
            display: this.state.progressBarDisplayState,
            position: 'absolute',
            marginLeft: '50%',
            marginTop: '100px'
          }}
        />

        <div>
          <form onSubmit={this.handleSubmit}>
            <Container style={{ width: '700px' }}>
              <ListGroup style={{ marginTop: '32px' }}>
                <form>
                  <Row style={{ width: '600px' }}>
                    <Avatar
                      src={this.state.src}
                      style={{ width: '100px', height: '100px' }}
                    ></Avatar>

                    <p style={{ paddingTop: '5%', marginLeft: '5%' }}>
                      {this.state.name} {this.state.surname}
                    </p>
                  </Row>
                  <Row>
                    <input
                      type='text'
                      name='content'
                      placeholder='Napisz post...'
                      onChange={this.handleChange}
                      style={{
                        width: '100%',
                        height: '100px',
                        marginTop: '20px'
                      }}
                    />
                  </Row>
                  <button
                    type='submit'
                    style={{
                      backgroundColor: 'red',
                      color: 'white',
                      border: '0px',
                      marginTop: '20px',
                      width: '200px',
                      height: '40px',
                      marginLeft: '30%'
                    }}
                  >
                    Dodaj
                  </button>
                  <Row></Row>
                </form>
              </ListGroup>
            </Container>
          </form>
        </div>
      </div>
    )
  }
}
export default AddPostView
