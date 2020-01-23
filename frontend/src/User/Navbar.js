import React from "react";
import {
  Navbar,
  NavbarToggler,
  NavbarBrand,
  Nav,
  NavItem,
  NavLink
} from "shards-react";
import { FaRoute } from 'react-icons/fa';
import { FiLogOut } from 'react-icons/fi';
import AuthService from './AuthService';
import logoNav from '../Images/logoNav.png';
import '../Styles/Navbar.css';
import { useHistory } from 'react-router-dom';

export default class Navbarex extends React.Component {

    constructor(props) {
            super(props);

            this.onClickLogOut = this.onClickLogOut.bind(this);
            this.Auth = new AuthService();
        }

    onClickLogOut = (event) => {
        event.preventDefault();
        this.Auth.logout();
        // this.props.history.replace('/login');
        // let path = `login`;
        // let history = useHistory();
        // history.push(path);
        // return <Redirect to='/login' />
        window.location.reload();

    }

  render() {
    return (
      <Navbar className="navbar" type="dark" expand="md" >
        <NavbarBrand href="/"> <img className="logoNav" src={logoNav} alt={"logo"}/></NavbarBrand>
          <Nav>
            <NavItem>
              <NavLink className="navLink" active href="/login">
                Trasy <FaRoute />
              </NavLink>
            </NavItem>
            <NavItem onClick={this.onClickLogOut}>
              <NavLink className="navLinkLogout" active href="/login">
                Wyloguj <FiLogOut />
              </NavLink>
            </NavItem>   
          </Nav>
      </Navbar>
    );
  }
}
