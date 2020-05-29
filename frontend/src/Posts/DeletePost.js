import React from 'react'
import Navbarex from '../User/Navbar'
import AuthService from '../User/AuthService'
import { ListGroup, Card, ListGroupItem } from 'react-bootstrap'
import { Link, Redirect } from 'react-router-dom'
import { CircularProgress } from '@material-ui/core'
import '../Styles/PostView.css'
import Avatar from '@material-ui/core/Avatar'
import { Container, Row, Col } from 'react-bootstrap'
import { Form, InputGroup } from 'react-bootstrap'
import Favorite from '@material-ui/icons/Favorite'
import FormGroup from '@material-ui/core/FormGroup'
import FormControlLabel from '@material-ui/core/FormControlLabel'
import Checkbox from '@material-ui/core/Checkbox'
import FavoriteBorder from '@material-ui/icons/FavoriteBorder'
import LinearProgress from '@material-ui/core/LinearProgress'
import { Alert, AlertTitle } from '@material-ui/lab'

class DeletePost extends React.Component {
  constructor (props) {
    super(props)
    this.state = {
      name: '',
      surname: '',
      src: '',
      auth: true,
      progressBarDisplayState: 'visible',
      content: '',
      username: '',
      post: '',
      postAuthorName: '',
      postAuthorSurname: '',
      postAuthorProfPic: '',
      authorIsAccountOwner: false,
      content: '',
      postAuthorEmail: '',
      redirectToPost: false,
      published: '',
      waiter: true,
      alertSuccessVisible: false
    }
    this.Auth = new AuthService()
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  async componentDidMount () {
    if (await this.Auth.loggedIn()) {
      this.Auth.fetch(
        `http://hulapp.pythonanywhere.com/api/post/${this.props.match.params.postId}`
      )
        .then(response => {
          this.setState({
            post: response,
            content: response.text,
            postAuthorName: response.author.first_name,
            postAuthorSurname: response.author.last_name,
            postAuthorProfPic: response.author.profile_img,
            postAuthorId: response.author.id,
            progressBarDisplayState: 'none',
            postAuthorEmail: response.author.username,
            published: response.published,
            waiter: false
          })
          console.log('publish value: ' + this.state.published)
        })
        .catch(error => {
          console.log({ message: 'ERROR ' + error })
        })
    } else {
      this.setState({ auth: false })
    }
  }

  formatDateTime = dateString => {
    const date = new Date(dateString)
    return date.toLocaleDateString() + ' ' + date.toLocaleTimeString()
  }

  handleSubmit = event => {
    event.preventDefault()
    this.Auth.fetch(
      `http://hulapp.pythonanywhere.com/api/post/${this.props.match.params.postId}`,
      {
        method: 'DELETE',

        body: JSON.stringify({
          id: this.props.match.params.postId
        })
      }
    )
      .then(response => {
        if (response.status >= 200 && response.status < 300) {
          this.setState({ redirectToPost: true })
          return response.json()
        }
      })
      .then(this.setState({ alertSuccessVisible: true }))
      .then(setTimeout(() => this.setState({ success: true }), 2000))
  }

  publishHandler = event => {
    this.setState({ published: !this.state.published })
  }

  render () {
    if (this.state.success) {
      return <Redirect to={'/posts/' + this.state.postAuthorId} />
    }
    return (
      <div>
        {this.state.auth ? '' : <Redirect to='/' />}
        <Navbarex />

        {this.state.waiter ? (
          <LinearProgress
            color='secondary'
            style={{ marginTop: '20px', height: '15px' }}
          />
        ) : (
          <div>
            <h1> CZY NAPEWNO CHCESZ USUNĄĆ TEN POST? </h1>
            <Card style={{ width: '60%', marginLeft: '20%' }}>
              {/* <Card.Img variant="top" src="holder.js/100px180" /> */}
              <Card.Body>
                <Card.Title>
                  <div style={{ display: 'inline-block' }}>
                    <Avatar
                      src={this.state.postAuthorProfPic}
                      style={{
                        width: '50px',
                        height: '50px',
                        display: 'inline-block',
                        marginTop: '20px'
                      }}
                    />
                    {this.state.postAuthorName} {this.state.postAuthorSurname}
                    <div>
                      {this.formatDateTime(this.state.post.add_date)}{' '}
                      Opublikowano:{' '}
                      {this.state.published === true ? 'tak' : 'nie'}
                    </div>
                    <FormControlLabel
                      control={
                        <Checkbox
                          icon={<FavoriteBorder />}
                          checkedIcon={<Favorite />}
                          name='checkedH'
                          checked={this.state.published}
                        />
                      }
                      label='Opublikuj'
                    />
                  </div>
                </Card.Title>
                <Card.Text>
                  {' '}
                  <form onSubmit={this.handleSubmit}>
                    <Form.Group>
                      <Form.Control
                        name='content'
                        type='text'
                        value={this.state.content}
                        required
                      />
                    </Form.Group>
                    {!this.state.alertSuccessVisible ? (
                      ''
                    ) : (
                      <div id='comment-add-alert'>
                        <Alert severity='info'>
                          <AlertTitle>
                            <strong>Twój post został usunięty </strong>
                          </AlertTitle>
                        </Alert>
                      </div>
                    )}
                    <button
                      style={{
                        backgroundColor: 'red',
                        border: '0px',
                        color: 'white',
                        height: '35px',
                        marginLeft: '40%'
                      }}
                    >
                      usuń
                    </button>
                  </form>
                </Card.Text>
              </Card.Body>
            </Card>
          </div>
        )}
      </div>
    )
  }
}
export default DeletePost
