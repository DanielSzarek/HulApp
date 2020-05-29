import React from 'react'
import {
  Navbar,
  // NavbarToggler,
  NavbarBrand,
  Nav,
  NavItem,
  NavLink
} from 'shards-react'
import { FaRoute, FaUser } from 'react-icons/fa'
import { FiLogOut } from 'react-icons/fi'
import AuthService from './AuthService'
import logoNav from '../Images/logoNav.png'
import '../Styles/Navbar.css'
import PersonAutoselect from '../User/PersonAutoselect'
import SearchIcon from '@material-ui/icons/Search'
import { Redirect } from 'react-router-dom'
import InfoIcon from '@material-ui/icons/Info'
import MapIcon from '@material-ui/icons/Map'
import '../Styles/InputProps.css'
import { InputGroup } from 'react-bootstrap'
import TogglePostsMenu from '../Posts/TogglePostsMenu.js'
import ToggleMenuNavbar from '../Map/ToggleMenuNavbar.js'

export default class Navbarex extends React.Component {
  constructor (props) {
    super(props)
    this.state = {
      loggedUsersId: '',
      userId: '',
      userCriteria: '',
      redirect: false,
      redLogPage: false
    }

    this.onClickLogOut = this.onClickLogOut.bind(this)
    this.Auth = new AuthService()
    this.handleUserChange = this.handleUserChange.bind(this)
    this.userChanged = this.userChanged.bind(this)
  }

  async componentDidMount () {
    if (await this.Auth.loggedIn()) {
      this.Auth.fetch('http://hulapp.pythonanywhere.com/auth/users/me/')
        .then(response => {
          this.setState({
            loggedUsersId: response.id
          })
        })
        .catch(error => {
          console.log({ message: 'ERROR ' + error })
        })
    } else {
      this.setState({ auth: false })
    }
  }

  onClickLogOut = event => {
    event.preventDefault()
    this.Auth.logout()
    window.location.reload()
    this.setState({
      redLogPage: true
    })
  }

  handleUserChange (val) {
    console.log('user handleUserChange autocomplete changed ' + val)
    this.setState({
      userId: val,
      redirect: true
    })
    {
      setTimeout(() => {
        window.location.reload()
      }, 500)
    }
    console.log('redirect' + this.state.redirect)
  }

  userChanged (val) {
    console.log('user userChanged autocomplete changed ' + val)
  }

  render () {
    return (
      <Navbar className='navbar' type='dark' expand='md'>
        <NavbarBrand href='/'>
          {' '}
          <img className='logoNav' src={logoNav} alt={'logo'} />
        </NavbarBrand>
        <Nav>
          <NavItem>
            <NavLink id="tracks" className="navLink" active href="/tracks">
              Trasy <FaRoute />
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink id="profile" className="navLink" active href="/">
              Profil <FaUser />
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink id="posts" className="navLink" active href={"/posts/" +this.state.loggedUsersId} style={{paddingRight: '40px'}}>
              Tablica <InfoIcon />
              {/* <TogglePostsMenu/>
              Posty */}
            </NavLink>
          </NavItem>
          <NavItem className='navLink' style={{ marginRight: '20px' }}>
            <NavLink className='navLink' active href={'/map'}>
              <div className='map-button'>
                {' '}
                Mapy <MapIcon />
              </div>
            </NavLink>
          </NavItem>
          <NavItem>
            <InputGroup.Prepend>
              <div className='inputgrouptext'>
                <InputGroup.Text>
                  <SearchIcon />
                </InputGroup.Text>
              </div>
              <PersonAutoselect
                id="searchUsers"
                controlId='formUsersSearch'
                label='Przyjaciele'
                dest='users'
                name='userId'
                required='true'
                onSelect={this.handleUserChange}
                onChange={this.userChanged}
                value={this.state.user}
                defVal={this.state.userId}
                onClick={this.clickHandler}
                let
                url={'/user/' + this.state.userId}
              />
            </InputGroup.Prepend>
          </NavItem>
          {this.state.redirect && (
            <Redirect to={'/user/' + this.state.userId} />
          )}
          <NavItem onClick={this.onClickLogOut}>
            <NavLink id="logout" className="navLinkLogout" active href="/login">
              Wyloguj <FiLogOut />
            </NavLink>
          </NavItem>
        </Nav>
      </Navbar>
    )
  }
}
