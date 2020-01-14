import React from "react";
import {
  Navbar,
  NavbarToggler,
  NavbarBrand,
  Nav,
  NavItem,
  NavLink,
  Dropdown,
  DropdownToggle,
  DropdownMenu,
  DropdownItem,
  InputGroup,
  InputGroupAddon,
  InputGroupText,
  FormInput,
  Collapse
} from "shards-react";
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
      <Navbar type="dark" theme="secondary" expand="md">
        <NavbarBrand href="/"> <img className="logoNav" src={logoNav} alt={"logo"}/></NavbarBrand>
          <Nav navbar>
            <NavItem onClick={this.onClickLogOut}>
              <NavLink active href="/login">
                Wyloguj
              </NavLink>
            </NavItem>   
          </Nav>
      </Navbar>
    );
  }
}
