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

class EditPost extends React.Component {
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
      published: ''
    }
    this.Auth = new AuthService()
    this.handleChange = this.handleChange.bind(this)
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
            published: response.published
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

  handleChange (event) {
    this.setState({
      content: event.target.value
    })
  }

  handleSubmit = event => {
    event.preventDefault()
    this.Auth.fetch(
      `http://hulapp.pythonanywhere.com/api/post/${this.props.match.params.postId}`,
      {
        method: 'PATCH',

        body: JSON.stringify({
          first_name: this.state.postAuthorName,
          last_name: this.state.postAuthorSurname,
          text: this.state.content,
          published: this.state.published
        })
      }
    )
      .then(response => {
        if (response.status >= 200 && response.status < 300) {
          this.setState({ redirectToPost: true })
          // alert("edytowałeś swój post!")
          return response.json()
        }
      })
      .then(alert('edytowałeś swój post!'))
    {
      setTimeout(() => {
        // window.location.reload()
        return <Redirect to={'/posts/' + this.props.match.params.usersId} />
      }, 3500)
      //   this.setState({redirectToPost: true})
    }
  }

  publishHandler = event => {
    this.setState({ published: !this.state.published })
  }

  render () {
    return (
      <div>
        {this.state.auth ? '' : <Redirect to='/' />}
        {/* {!(this.state.redirectToPost) ? '' : <Redirect to={'/posts/'+this.props.match.params.usersId} />} */}
        <Navbarex />
        <CircularProgress
          style={{
            display: this.state.progressBarDisplayState,
            position: 'absolute',
            marginLeft: '50%',
            marginTop: '100px'
          }}
        />

        <div>
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
                        onClick={this.publishHandler}
                        name='checkedH'
                        // checked={(this.state.published===true) ? false : true}
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
                      onChange={this.handleChange}
                      required
                    />
                  </Form.Group>
                  <button
                    style={{
                      backgroundColor: 'red',
                      border: '0px',
                      color: 'white',
                      height: '35px',
                      marginLeft: '40%'
                    }}
                  >
                    potwierdz
                  </button>
                </form>
              </Card.Text>
            </Card.Body>
          </Card>
        </div>
      </div>
    )
  }
}
export default EditPost
