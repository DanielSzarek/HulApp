import React from 'react'
import Navbarex from '../User/Navbar'
import AuthService from '../User/AuthService'
import { ListGroup, Card, ListGroupItem } from 'react-bootstrap'
import { Link, Redirect } from 'react-router-dom'
import { CircularProgress, IconButton } from '@material-ui/core'
import '../Styles/PostView.css'
import '../Styles/SimplePersonalPost.css'
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
      commentToEdit: '',
      editFormVisible: false,
      commentTextEdit: '',
      waiter: true,
      submitFormVisible: false,
      alertAddSuccessVisible: false
    }
    this.Auth = new AuthService()
    this.commentAddHandler = this.commentAddHandler.bind(this)
    this.onCommentChange = this.onCommentChange.bind(this)
    this.commentDeleteHandler = this.commentDeleteHandler.bind(this)
    this.onRemoveItem = this.onRemoveItem.bind(this)
    this.onEditItem = this.onEditItem.bind(this)
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
      .then(this.setState({ alertAddSuccessVisible: true }))
      .then(setTimeout(() => (window.location.reload()), 1000))
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
  commentEditHandler = event => {
    event.preventDefault()
    console.log('comment to edit: ' + this.state.commentToEdit)
    this.Auth.fetch(
      `http://hulapp.pythonanywhere.com/api/comment/${this.state.commentToEdit}`,
      {
        method: 'PUT',
        body: JSON.stringify({
          username: this.state.usernameOfComment,
          text: this.state.commentTextEdit,
          post: this.props.match.params.postId
        })
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
  onEditItem = commentId => {
    this.setState({ commentToEdit: commentId, editFormVisible: true })
    console.log(this.state.commentToEdit)
  }

  render () {
    return (
      <div>
        {this.state.auth ? '' : <Redirect to='/' />}
        <Navbarex />

        {this.state.waiter ? (
          <LinearProgress color='secondary' id='linear-progress' />
        ) : (
          <div>
            <Card id='post-card'>
              <Card.Body>
                <Card.Title>
                  <div style={{ display: 'inline-block' }}>
                    <Avatar
                      src={this.state.postAuthorProfPic}
                      id='post-avatar'
                    />
                    <div id='post-author-name'>
                      {this.state.postAuthorName} {this.state.postAuthorSurname}
                    </div>
                    <div>
                      {!(this.state.post.mod_date === null) ? (
                        <div>
                          edytowano: {this.state.post.mod_date.substr(0, 10)}{' '}
                          {this.state.post.mod_date.substr(11, 12).substr(0, 5)}{' '}
                        </div>
                      ) : (
                        <div>
                          dodano: {this.state.post.add_date.substr(0, 10)}{' '}
                          {this.state.post.add_date.substr(11, 12).substr(0, 5)}{' '}
                        </div>
                      )}
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
                      <button id='post-edit-button' className='post-buttons'>
                        Edytuj
                      </button>{' '}
                    </Link>{' '}
                    <Link to={'/delete/post/' + this.props.match.params.postId}>
                      <button id='post-delete-button' className='post-buttons'>
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
              <form>
                <div>
                  <textarea
                    style={{ width: '100%' }}
                    onChange={this.onCommentChange}
                    name='commentText'
                  ></textarea>
                </div>
                {!this.state.alertAddSuccessVisible ? (
                  ''
                ) : (
                  <div id='comment-add-alert'>
                    <Alert severity='success'>
                      <AlertTitle>
                        <strong>Ok</strong>
                      </AlertTitle>
                    </Alert>
                  </div>
                )}
                <button
                  id='comment-add-button'
                  className='comment-buttons'
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
          <div id='comment-add-alert'>
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
          <Paper id='comments-paper' key={comment.id}>
            <Grid container wrap='nowrap' spacing={2}>
              <Grid item>
                <Avatar alt='User' src={comment.author.profile_img} />
              </Grid>
              <Grid justifyContent='left' item xs zeroMinWidth>
                <h4 id='comments-author-names'>
                  {comment.author.first_name} {comment.author.last_name}
                </h4>
                <div id='comments-text-container'>
                  <p id='comments-text-paragraph'>{comment.text}</p>
                </div>
                <div>
                  <div
                    style={{
                      textAlign: 'left',
                      color: 'gray',
                      display: 'inline-block'
                    }}
                  >
                    {!(comment.mod_date === null) ? (
                      <div>
                        edytowano: {comment.mod_date.substr(0, 10)}{' '}
                        {comment.mod_date.substr(11, 12).substr(0, 5)}{' '}
                      </div>
                    ) : (
                      <div>
                        dodano: {comment.add_date.substr(0, 10)}{' '}
                        {comment.add_date.substr(11, 12).substr(0, 5)}{' '}
                      </div>
                    )}
                  </div>
                  {Number(this.state.accountOwnerId) ===
                  Number(comment.author.id) ? (
                    <div
                      value={comment.id}
                      id='comments-edit-ref'
                      onClick={() => this.onEditItem(comment.id)}
                    >
                      Edytuj
                    </div>
                  ) : (
                    ''
                  )}
                </div>
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
            {!(Number(this.state.commentToEdit) === Number(comment.id)) ? (
              ''
            ) : (
              <div>
                {!this.state.editFormVisible ? (
                  ''
                ) : (
                  <form>
                    <div>
                      <textarea
                        defaultValue={comment.text}
                        id='comments-edit-textarea'
                        onChange={this.onCommentChange}
                        name='commentTextEdit'
                      ></textarea>
                    </div>
                    <div style={{ diplay: 'inline' }}>
                      <button
                        id='comments-edit-button'
                        onClick={this.commentEditHandler}
                      >
                        Edytuj
                      </button>
                    </div>
                  </form>
                )}
              </div>
            )}
          </Paper>
        ))}
      </div>
    )
  }
}
export default SimplePersonalPost
