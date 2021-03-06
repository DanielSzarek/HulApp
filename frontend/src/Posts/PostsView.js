import React from 'react'
import Navbarex from '../User/Navbar'
import AuthService from '../User/AuthService'
import { ListGroup } from 'react-bootstrap'
import { Link, Redirect } from 'react-router-dom'
import { CircularProgress } from '@material-ui/core'
import '../Styles/PostView.css'
import SinglePost from './SinglePost.js'
import '../Styles/SinglePost.css'


class PostsView extends React.Component {
  constructor (props) {
    super(props)
    this.state = {
      posts: [],

      usersId: this.props.match.params.usersId,
      auth: true,
      progressBarDisplayState: 'visible'
    }
    this.Auth = new AuthService()
  }

  async componentDidMount () {
    if (await this.Auth.loggedIn()) {
      this.Auth.fetch('http://hulapp.pythonanywhere.com/api/post')
        .then(response => {
          this.setState({
            posts:  response,
            progressBarDisplayState: 'none'
          })
          this.state.posts.push(this.state.usersId);
          console.log(this.props.match.params.usersId)
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
        {this.state.auth ? '' : <Redirect to='/' />}
        <Navbarex />
        <div>
          <h1>Tablica Postów</h1>
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
        <div class='container'>
          <div className='sticky-button-container'>
          <Link to='/add-post'>
            <button className='sticky-button'> NAPISZ POST</button>
            </Link>
          </div>
          <div>
            <ListGroup style={{ marginTop: '32px', border: '0px' }}>
              {this.state.posts.map(post => (
                <ListGroup.Item style={{ color: 'black' }}>
                  <SinglePost data={post} usersId={this.props.match.params.usersId}/>
                </ListGroup.Item>
              ))}
            </ListGroup>
          </div>
        </div>
      </div>
    )
  }
}
export default PostsView
