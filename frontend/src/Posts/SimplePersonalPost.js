import React from 'react'
import Navbarex from '../User/Navbar'
import AuthService from '../User/AuthService'
import { ListGroup, Card, ListGroupItem } from 'react-bootstrap'
import { Link, Redirect } from 'react-router-dom'
import { CircularProgress } from '@material-ui/core'
import '../Styles/PostView.css'
import Avatar from '@material-ui/core/Avatar'
import { Container, Row, Col } from 'react-bootstrap'

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
      authorIsAccountOwner: false
    }
    this.Auth = new AuthService()
  }

  async componentDidMount () {
    if (await this.Auth.loggedIn()) {
      this.Auth.fetch(
        `http://hulapp.pythonanywhere.com/api/post/${this.props.match.params.postId}`
      )
        .then(response => {
          this.setState({
            post: response,
            postAuthorName: response.author.first_name,
            postAuthorSurname: response.author.last_name,
            postAuthorProfPic: response.author.profile_img,
            postAuthorId: response.author.id,
            progressBarDisplayState: 'none'
          })
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

  render () {
    return (
      <div>
        {this.state.auth ? '' : <Redirect to='/' />}
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
          <Card style={{ width: '60%' , marginLeft:'20%', marginTop:'30px'}}>
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
                    {this.state.post.published === true ? 'tak' : 'nie'}
                  </div>
                </div>
              </Card.Title>
              <Card.Text>{this.state.post.text}</Card.Text>
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
                </div>
              ) : (
                ''
              )}
              {/* <Button variant="primary">Go somewhere</Button> */}
            </Card.Body>
          </Card>
        </div>
      </div>
    )
  }
}
export default SimplePersonalPost

//I SAVE THIS COMMENT BECAUSE IT SHOULD WORKS BUT THE ERROR IS RESPONSE.JSON() IS NOT A FUNCTION.
//BUT...
//IT WOULD LOOK NICER WITH THAT SO LATER TO WORK ON IT

// import React, { useState, useEffect } from 'react'
// import { makeStyles } from '@material-ui/core/styles'
// import clsx from 'clsx'
// import Card from '@material-ui/core/Card'
// import CardHeader from '@material-ui/core/CardHeader'
// import CardMedia from '@material-ui/core/CardMedia'
// import CardContent from '@material-ui/core/CardContent'
// import CardActions from '@material-ui/core/CardActions'
// import Collapse from '@material-ui/core/Collapse'
// import Avatar from '@material-ui/core/Avatar'
// import IconButton from '@material-ui/core/IconButton'
// import Typography from '@material-ui/core/Typography'
// import { red } from '@material-ui/core/colors'
// import FavoriteIcon from '@material-ui/icons/Favorite'
// import ShareIcon from '@material-ui/icons/Share'
// import ExpandMoreIcon from '@material-ui/icons/ExpandMore'
// import MoreVertIcon from '@material-ui/icons/MoreVert'
// import AuthService from '../User/AuthService'

// const useStyles = makeStyles(theme => ({
//   card: {
//     maxWidth: '60%'
//   },
//   media: {
//     height: 0,
//     paddingTop: '56.25%'
//   },
//   expand: {
//     transform: 'rotate(0deg)',
//     marginLeft: 'auto',
//     transition: theme.transitions.create('transform', {
//       duration: theme.transitions.duration.shortest
//     })
//   },
//   expandOpen: {
//     transform: 'rotate(180deg)'
//   },
//   avatar: {
//     width: '100px',
//     height: 'auto'
//   }
// }))

// export default function SimplePersonalPost (props) {
//   const classes = useStyles()
//   const [expanded, setExpanded] = React.useState(false)

//   const [post, setPost] = useState([])

//   const handleExpandClick = () => {
//     setExpanded(!expanded)
//   }

//   const formatDateTime = dateString => {
//     const date = new Date(dateString)
//     return date.toLocaleDateString() + ' ' + date.toLocaleTimeString()
//   }

//   const Auth = new AuthService()

//   //   async function fetchData() {
//   const fetchData = async () => {
//     if (Auth.loggedIn()) {
//       //   const res = await Auth.fetch(`http://hulapp.pythonanywhere.com/api/post/${props.match.params.postId}`);

//       const response = await Auth.fetch(
//         `http://hulapp.pythonanywhere.com/api/post/${props.match.params.postId}`
//       )
//       //    res
//       //   .json()
//       //   .then(response => setPost(response))
//       //   .catch(err => setErrors(err))
//       const json = await response.json()
//       setPost(json)
//     }
//   }

//   useEffect(() => {
//     fetchData()
//   })

//   return (
//     <div>
//       <Card className={classes.card} style={{ marginLeft: '20%' }}>
//         <CardHeader
//           avatar={
//             <Avatar
//               aria-label='recipe'
//               className={classes.avatar}
//               src={post.src}
//             ></Avatar>
//           }
//           action={
//             <IconButton aria-label='settings'>
//               <MoreVertIcon />
//             </IconButton>
//           }
//           title={
//             <div>
//               {post.name} {post.surname}
//             </div>
//           }
//           subheader={<div> {formatDateTime(post.date)} </div>}
//         />
//         {/* <CardMedia
//         className={classes.media}
//         image="src"
//         title="title"
//       /> */}
//         <CardContent>
//           <Typography variant='body2' color='textSecondary' component='p'>
//             <p style={{ maxWidth: '900px' }}>
//               <b>{post.text}</b>
//             </p>
//           </Typography>
//         </CardContent>
//         <CardActions disableSpacing>
//           <IconButton aria-label='add to favorites'>
//             <FavoriteIcon />
//           </IconButton>

//           {post.id === post.usersId ? '' : <text>Pokaż </text>}
//         </CardActions>
//       </Card>
//     </div>
//   )
// }
