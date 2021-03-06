import React, { useState } from 'react'
import { makeStyles } from '@material-ui/core/styles'
import clsx from 'clsx'
import Card from '@material-ui/core/Card'
import CardHeader from '@material-ui/core/CardHeader'
import CardMedia from '@material-ui/core/CardMedia'
import CardContent from '@material-ui/core/CardContent'
import CardActions from '@material-ui/core/CardActions'
import Collapse from '@material-ui/core/Collapse'
import Avatar from '@material-ui/core/Avatar'
import IconButton from '@material-ui/core/IconButton'
import Typography from '@material-ui/core/Typography'
import { red } from '@material-ui/core/colors'
import FavoriteIcon from '@material-ui/icons/Favorite'
import ShareIcon from '@material-ui/icons/Share'
import ExpandMoreIcon from '@material-ui/icons/ExpandMore'
import MoreVertIcon from '@material-ui/icons/MoreVert'
import { Link } from 'react-router-dom'
import ModeCommentIcon from '@material-ui/icons/ModeComment'

const useStyles = makeStyles(theme => ({
  card: {
    maxWidth: '60%'
  },
  media: {
    height: 0,
    paddingTop: '56.25%'
  },
  expand: {
    transform: 'rotate(0deg)',
    marginLeft: 'auto',
    transition: theme.transitions.create('transform', {
      duration: theme.transitions.duration.shortest
    })
  },
  expandOpen: {
    transform: 'rotate(180deg)'
  },
  avatar: {
    width: '100px',
    height: 'auto'
  }
}))

export default function SinglePost (props) {
  const classes = useStyles()
  const [expanded, setExpanded] = React.useState(false)

  const [post, setState] = useState({
    text: props.data.text,
    name: props.data.author.first_name,
    surname: props.data.author.last_name,
    src: props.data.author.profile_img,
    date: props.data.add_date,
    id: props.data.author.id,
    postId: props.data.id,
    modDate: props.data.mod_date
  })

  const [usersId, setUsersId] = useState(props.usersId)

  const handleExpandClick = () => {
    setExpanded(!expanded)
  }

  const longDate = post.date
  const additionDate = longDate.substr(0, 10)
  const additionTime = longDate.substr(11, 12)
  const additionTimeFormatted = additionTime.substr(0, 5)
  const longModDateTime = post.modDate
  let modificationDate = ''
  let modificationTime = ''
  let modificationTimeFormatted = ''

  if (longModDateTime !== null) {
    modificationDate = longModDateTime.substr(0, 10)
    modificationTime = longModDateTime.substr(11, 12)
    modificationTimeFormatted = modificationTime.substr(0, 5)
  }

  return (
    <div>
      <Card className={classes.card} style={{ marginLeft: '20%' }}>
        <CardHeader
          avatar={
            <Avatar
              aria-label='recipe'
              className={classes.avatar}
              src={post.src}
            ></Avatar>
          }
          // action={
          //   <IconButton aria-label='settings'>
          //     <MoreVertIcon />
          //   </IconButton>
          // }
          title={
            <div style={{ color: 'red', fontWeight: 'bold' }}>
              {post.name} {post.surname}
            </div>
          }
          subheader={
            post.modDate == null ? (
              <div>
                {' '}
                {additionDate} {additionTimeFormatted}{' '}
              </div>
            ) : (
              <div>
                Edytowano: {modificationDate} {modificationTimeFormatted}
              </div>
            )
          }
        />
        {/* <CardMedia
        className={classes.media}
        image="src"
        title="title"
      /> */}
        <CardContent>
          <Typography variant='body2' color='textSecondary' component='p'>
            <p style={{ maxWidth: '900px' }}>
              <b>{post.text}</b>
            </p>
          </Typography>
        </CardContent>
        <CardActions disableSpacing>
          <IconButton aria-label='add to favorites'>
            <FavoriteIcon />
          </IconButton>

          <Link to={'/simple/personal/post/' + usersId + '/' + post.postId}>
            {post.id === usersId ? (
              ''
            ) : (
              <IconButton aria-label='share'>
                <ModeCommentIcon />
              </IconButton>
            )}
          </Link>
          {/* <text>Pokaż </text> */}
          {/* <IconButton aria-label="share">
          <ShareIcon />
        </IconButton> */}
        </CardActions>
      </Card>
    </div>
  )
}
