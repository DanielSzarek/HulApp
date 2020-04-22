import React from 'react'
import Navbarex from '../User/Navbar'
import AuthService from '../User/AuthService'
import { ListGroup, Card, ListGroupItem } from 'react-bootstrap'
import { Link, Redirect } from 'react-router-dom'
import { CircularProgress, IconButton } from '@material-ui/core'
import '../Styles/PostView.css'
// import Avatar from '@material-ui/core/Avatar'
import { Container, Row, Col } from 'react-bootstrap'
import Comments from './Comments.js'
import { Divider, Avatar, Grid, Paper } from '@material-ui/core'
import FavoriteIcon from '@material-ui/icons/Favorite'
import DeleteIcon from '@material-ui/icons/Delete'
import LinearProgress from '@material-ui/core/LinearProgress'
import { Alert, AlertTitle } from '@material-ui/lab'
import { Button } from '@material-ui/core'

class SimplePersonalPost extends React.Component {
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
      comments: [],
      usernameOfComment: '',
      commentText: '',
      accountOwnerId: this.props.match.params.usersId,
      commentToDelete: '',
      // progressBarDisplayState: "visible",
      waiter: true,
      submitFormVisible: false
    }
    this.Auth = new AuthService()
    this.commentAddHandler = this.commentAddHandler.bind(this)
    this.onCommentChange = this.onCommentChange.bind(this)
    this.commentDeleteHandler = this.commentDeleteHandler.bind(this)
    this.onRemoveItem = this.onRemoveItem.bind(this)
  }

  async componentDidMount () {
    if (await this.Auth.loggedIn()) {
      await this.Auth.fetch(
        `http://hulapp.pythonanywhere.com/api/post/${this.props.match.params.postId}`
      )
        .then(response => {
          this.setState({
            post: response,
            postAuthorName: response.author.first_name,
            postAuthorSurname: response.author.last_name,
            postAuthorProfPic: response.author.profile_img,
            postAuthorId: response.author.id,
            waiter: false
          })
        })

        .catch(error => {
          console.log({ message: 'ERROR ' + error })
        })
    } else {
      this.setState({ auth: false })
    }
    await this.Auth.fetch(
      `http://hulapp.pythonanywhere.com/api/comment?post=${this.props.match.params.postId}`
    ).then(response => {
      this.setState({
        comments: response
      })
    })

    await this.Auth.fetch('http://hulapp.pythonanywhere.com/auth/users/me/', {
      method: 'GET'
    }).then(response => {
      this.setState({ usernameOfComment: response.username })
    })
  }

  commentAddHandler = event => {
    event.preventDefault()
    this.Auth.fetch(`http://hulapp.pythonanywhere.com/api/comment/`, {
      method: 'POST',

      body: JSON.stringify({
        username: this.state.usernameOfComment,
        text: this.state.commentText,
        post: this.props.match.params.postId
      })
    })
      .then(response => {
        if (response.status >= 200 && response.status < 300) {
          return response.json()
        }
      })
      .then(alert('Twój komentarz został dodany'))
      .then(window.location.reload())
  }

  onCommentChange (event) {
    this.setState({
      [event.target.name]: event.target.value
    })
  }

  commentDeleteHandler = event => {
    event.preventDefault()
    console.log('comment to delete: ' + this.state.commentToDelete)
    this.Auth.fetch(
      `http://hulapp.pythonanywhere.com/api/comment/${this.state.commentToDelete}`,
      {
        method: 'DELETE'
      }
    )
      .then(response => {
        if (response.status >= 200 && response.status < 300) {
          return response.json()
        }
      })
      .then(window.location.reload())
  }

  onRemoveItem = commentId => {
    this.setState({ commentToDelete: commentId, submitFormVisible: true })
    console.log(this.state.commentToDelete)
  }

  render () {
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
            <Card
              style={{ width: '60%', marginLeft: '20%', marginTop: '30px' }}
            >
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
                    <div
                      style={{
                        fontSize: '30px',
                        display: 'inline-block',
                        marginLeft: '10px',
                        color: 'red',
                        fontWeight: 'bold'
                      }}
                    >
                      {this.state.postAuthorName} {this.state.postAuthorSurname}
                    </div>
                    <div>
                      {this.state.post.add_date.substr(0, 10)}{' '}
                      {this.state.post.add_date.substr(11, 12).substr(0, 5)}{' '}
                      Opublikowano:{' '}
                      {this.state.post.published === true ? 'tak' : 'nie'}
                    </div>
                  </div>
                </Card.Title>
                <Card.Text>
                  <div style={{ fontSize: '40px' }}>{this.state.post.text}</div>
                </Card.Text>
                {Number(this.props.match.params.usersId) ===
                Number(this.state.postAuthorId) ? (
                  <div>
                    <Link
                      to={
                        '/edit/my-post/' +
                        this.props.match.params.usersId +
                        '/' +
                        this.props.match.params.postId
                      }
                    >
                      <button
                        style={{
                          backgroundColor: 'red',
                          border: '0px',
                          color: 'white',
                          height: '35px'
                        }}
                      >
                        Edytuj
                      </button>{' '}
                    </Link>{' '}
                    <Link to={'/delete/post/' + this.props.match.params.postId}>
                      <button
                        style={{
                          backgroundColor: 'red',
                          border: '0px',
                          color: 'white',
                          height: '35px'
                        }}
                      >
                        usuń
                      </button>
                    </Link>
                  </div>
                ) : (
                  ''
                )}
                <IconButton aria-label='add to favorites'>
                  <FavoriteIcon />
                </IconButton>
              </Card.Body>
              <form class='ui reply form'>
                <div class='field'>
                  <textarea
                    rows='1'
                    cols='70'
                    style={{ width: '100%' }}
                    onChange={this.onCommentChange}
                    name='commentText'
                  ></textarea>
                </div>
                <button
                  style={{
                    backgroundColor: 'red',
                    color: 'white',
                    border: '0px',
                    marginLeft: '40%',
                    marginTop: '15px',
                    marginBottom: '15px',
                    height: '30px'
                  }}
                  onClick={this.commentAddHandler}
                >
                  Skomentuj
                </button>
              </form>
            </Card>
          </div>
        )}
        {!this.state.submitFormVisible ? (
          ''
        ) : (
          <div
            style={{
              width: '70%',
              marginLeft: '15%',
              marginTop: '10px',
              marginBottom: '10px',
              paddingRight: '40px'
            }}
          >
            <Alert
              severity='error'
              action={
                <Button
                  color='inherit'
                  size='small'
                  onClick={this.commentDeleteHandler}
                >
                  <strong>Tak, usuń</strong>
                </Button>
              }
            >
              <AlertTitle>
                <strong>Czy napewno chcesz usunąć swój komentarz?</strong>
              </AlertTitle>
            </Alert>
          </div>
        )}
        {this.state.comments.map((comment, index) => (
          <Paper
            style={{
              padding: '20px 20px',
              marginTop: 10,
              width: '60%',
              marginLeft: '20%'
            }}
            key={comment.id}
          >
            <Grid container wrap='nowrap' spacing={2}>
              <Grid item>
                <Avatar alt='User' src={comment.author.profile_img} />
              </Grid>
              <Grid justifyContent='left' item xs zeroMinWidth>
                <h4
                  style={{
                    margin: 0,
                    textAlign: 'left',
                    color: 'red',
                    fontWeight: 'bold',
                    display: 'inline-block'
                  }}
                >
                  {comment.author.first_name} {comment.author.last_name}
                </h4>
                <div
                  style={{
                    maxWidth: '900px',
                    display: 'inline-block',
                    paddingLeft: '20px'
                  }}
                >
                  <p
                    style={{
                      textAlign: 'left',
                      maxWidth: '900px',
                      maxLength: '100'
                    }}
                  >
                    {comment.text}
                  </p>
                </div>
                <p style={{ textAlign: 'left', color: 'gray' }}>
                  opublikowano: {comment.add_date.substr(0, 10)}{' '}
                  {comment.add_date.substr(11, 12).substr(0, 5)}
                </p>
              </Grid>
              {Number(this.state.accountOwnerId) ===
              Number(comment.author.id) ? (
                <div
                  value={comment.id}
                  onClick={() => this.onRemoveItem(comment.id)}
                  name='commentToDelete'
                >
                  <IconButton style={{ height: '40px' }}>
                    <DeleteIcon />
                  </IconButton>
                </div>
              ) : (
                ''
              )}
            </Grid>
          </Paper>
        ))}
      </div>
    )
  }
}
export default SimplePersonalPost
