import React from 'react'
import Navbarex from '../User/Navbar'
import AuthService from '../User/AuthService'
import { ListGroup } from 'react-bootstrap'
import { Link, Redirect } from 'react-router-dom'
import { CircularProgress } from '@material-ui/core'
import '../Styles/PostView.css'
import Avatar from '@material-ui/core/Avatar'
import { Container, Row, Col } from 'react-bootstrap'
import LinearProgress from '@material-ui/core/LinearProgress'
import { Alert, AlertTitle } from '@material-ui/lab'

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
      alertSuccessVisible: false,
      username: '',
      waiter: true,
      success: false,
      id: ''
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
            username: response.username,
            id: response.id,
            waiter: false
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
    if(this.state.content<=0){
      alert("nie możesz dodać pustego posta!")
     } else {
    this.Auth.fetch('http://hulapp.pythonanywhere.com/api/post/', {
      method: 'POST',

      body: JSON.stringify({
        first_name: this.state.name,
        last_name: this.state.surname,
        text: this.state.content,
        username: this.state.username
      })
    })
      .then(response => {
        if (response.status >= 200 && response.status < 300) {
          return response.json()
        }
      })
      .then(this.setState({ alertSuccessVisible: true }))
      .then(setTimeout(() => this.setState({ success: true }), 2000))
    }
  }

  render () {
    if (this.state.success) {
      return <Redirect to={'/posts/' + this.state.id} />
    }
    return (
      <div>
        {this.state.auth ? '' : <Redirect to='/' />}
        <Navbarex />

        {this.state.waiter ? (
          <LinearProgress color='secondary' id='linear-progress' />
        ) : (
          <div>
            <div>
              <h1>dodaj post</h1>
              <hr />
            </div>
            <div>
              <form onSubmit={this.handleSubmit}>
                <Container style={{ width: '700px' }}>
                  <ListGroup style={{ marginTop: '32px' }}>
                    <form>
                      <Row style={{ width: '600px' }}>
                        <Avatar
                          src={this.state.src}
                          style={{ width: '10%', height: '10%' }}
                        ></Avatar>

                        <p
                          style={{
                            paddingTop: '5%',
                            marginLeft: '5%',
                            color: 'red',
                            fontWeight: 'bold'
                          }}
                        >
                          {this.state.name} {this.state.surname}
                        </p>
                      </Row>
                      <Row>
                        <textarea
                          type='text'
                          name='content'
                          placeholder='Napisz post...'
                          onChange={this.handleChange}
                          style={{
                            width: '100%',
                            height: '100px',
                            marginTop: '10px',
                            alignContent: 'flex-start',
                            textAlign: 'left',
                            verticalAlign: 'top',
                            display: 'table-cell'
                          }}
                        />
                      </Row>
                      {!this.state.alertSuccessVisible ? (
                        ''
                      ) : (
                        <div id='comment-add-alert'>
                          <Alert severity='success'>
                            <AlertTitle>
                              <strong>Twój post został dodany :)</strong>
                            </AlertTitle>
                          </Alert>
                        </div>
                      )}
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
        )}
      </div>
    )
  }
}
export default AddPostView
