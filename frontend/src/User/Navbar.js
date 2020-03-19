import React from "react";
import {
  Navbar,
  NavbarToggler,
  NavbarBrand,
  Nav,
  NavItem,
  NavLink
} from "shards-react";
import { FaRoute, FaUser } from 'react-icons/fa';
import { FiLogOut } from 'react-icons/fi';
import AuthService from './AuthService';
import logoNav from '../Images/logoNav.png';
import '../Styles/Navbar.css';
import { useHistory } from 'react-router-dom';
import PersonAutoselect from '../User/PersonAutoselect';
import SearchIcon from '@material-ui/icons/Search';
import {Link} from 'react-router-dom';
import {withRouter, Redirect} from 'react-router-dom';



export default class Navbarex extends React.Component {

    constructor(props) {
            super(props);
            this.state = {
              userId : '',
              userCriteria: '',
              redirect: false
            }

            this.onClickLogOut = this.onClickLogOut.bind(this);
            this.Auth = new AuthService();
            this.handleUserChange = this.handleUserChange.bind(this);
            this.userChanged = this.userChanged.bind(this);
        }

    onClickLogOut = (event) => {
        event.preventDefault();
        this.Auth.logout();
        window.location.reload();

    }

     handleUserChange(val){
		    console.log("user handleUserChange autocomplete changed "+val);
		    this.setState({
          userId: val,
          redirect: true})
    console.log("redirect" +this.state.redirect)
	}

  userChanged(val){
		console.log("user userChanged autocomplete changed "+val);

	}

  render() {
    return (
      <Navbar className="navbar" type="dark" expand="md" >
        <NavbarBrand href="/"> <img className="logoNav" src={logoNav} alt={"logo"}/></NavbarBrand>
          <Nav>
            <NavItem>
              <NavLink className="navLink" active href="/tracks">
                Trasy <FaRoute />
              </NavLink>
            </NavItem>
            <NavItem>
              <NavLink className="navLink" active href="/">
                Profil <FaUser />
              </NavLink>
            </NavItem>
             <NavItem>
                <PersonAutoselect
                        controlId="formUsersSearch" 
                        label="Przyjaciele" dest="users" 
                        name="userId" required="true" 
                        onSelect={this.handleUserChange} 
						            onChange={this.userChanged}
                        value={this.state.user} 
                        defVal={this.state.userId}
                        onClick={this.clickHandler}
                        let url = {"/user/"+this.state.userId}
                        />
            </NavItem>
            {this.state.redirect&& <Redirect to={"/user/"+this.state.userId}/>}
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
