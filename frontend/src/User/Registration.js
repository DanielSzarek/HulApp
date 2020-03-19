import React from 'react';
import logo from '../Images/logo.png';
// import {Button } from 'react-bootstrap';
import {Link, Redirect} from 'react-router-dom';
import AutoComplete from './SelectAutocompleteNoToken';
import '../Styles/App.css';
import '../Styles/Registration.css';
import '../Styles/Login.css';
import AlertRegistrationSucces from './AlertRegistrationSucces';
import EmailOutlinedIcon from '@material-ui/icons/EmailOutlined';
// import {Col, Form, InputGroup} from 'react-bootstrap';
import {Form, InputGroup} from 'react-bootstrap';

import VisibilityOutlinedIcon from '@material-ui/icons/VisibilityOutlined';
import VisibilityOffOutlinedIcon from '@material-ui/icons/VisibilityOffOutlined';
import PermIdentityOutlinedIcon from '@material-ui/icons/PermIdentityOutlined';
// import QontoConnector from "../User/nowy"




class Registration extends React.Component{

    constructor(props) {
        super(props);
        this.state = {
            email: '',
                password: '',
                repeatedPassword: '',
                name: '',
                surname: '',
                country: '',
                city: '',
                message: '',
				match: false,
                visible: false,
                success: false,
                passwordHidden : true,
                counterRepeated : 1,
                passwordRepeatedHidden : true,
                counter : 1
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
		this.handleCityChange = this.handleCityChange.bind(this);
		this.handleCountryChange = this.handleCountryChange.bind(this);
		this.handleCountryChange = this.handleCountryChange.bind(this);
		this.checkPassword = this.checkPassword.bind(this);
      }

    handleSubmit = (event) => {
        event.preventDefault();
         fetch('http://hulapp.pythonanywhere.com/auth/users/', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                username: this.state.email,
                email: this.state.email,
                password: this.state.password,
                first_name: this.state.name,
                last_name: this.state.surname,
                country: this.state.country,
                city: this.state.city
            })
            })
            .then((response) => {
                let self = this;
                if(response.status >= 200 && response.status <300){
                    this.setState({ message: "" , visible: true});
                    {this.state.visible&&setTimeout(() => this.setState({ success: true }), 6000)}
                    
                }else{
                    var json = response.json().then((obj) => {
					var allPropertyNames = Object.keys(obj);
					var err = "";
					for (var j=0; j<allPropertyNames.length; j++) {
						var name = allPropertyNames[j];
						var value = obj[name];
						err += name + ": " + value + " ";
					}
                    self.setState({ message: "Rejestracja nie jest możliwa: " + err, newPassword: "", newPasswordRepeated: ""  });
					});
                }
            })
            .catch((error) => {
                this.setState({message: "ERROR " + error});
            });
    };

     handleChange(event) {
        this.setState({
            [event.target.name]: event.target.value
        });
    }
	
	handleCityChange(val){
		this.setState({city: val})
	}
	
	handleCountryChange(val){
		this.setState({country: val})
	}

    checkPassword = (event) => {
        if (event.target.value && event.target.value !== this.state.password) {
			event.target.style.background = 'red';
			event.target.style.border = '1px solid red';
			this.setState({match:false});
			alert("The passwords don't match");
        } else {
			event.target.style.background = '#E2E2E2';
			event.target.style.border = '1px solid #ced4da';
			this.setState({match:true});
        }
      };

       showPassword = () => {
        this.setState({ counter : this.state.counter + 1 })
        if (this.state.counter % 2 == 0){
        this.setState({
            passwordHidden : false
        })
        }else { this.setState({passwordHidden : true})}
    };

    showPasswordRepeated = () => {
        this.setState({ counterRepeated : this.state.counterRepeated + 1 })
        if (this.state.counterRepeated % 2 == 0){
        this.setState({
            passwordRepeatedHidden : false
        })
        }else { this.setState({passwordRepeatedHidden : true})}
    };
	  
    render(){
        if (this.state.success){
    return (<Redirect to='/login'  />)
		}
        return(
            <div>
    <div className="offset-md-4 col-12 col-md-4">
    {/* <QontoConnector/> */}
            <div className="registration-container">
                <img src={logo} alt={"logo"}/>
                </div>
                </div>
                <div className="offset-md-3 col-12 col-md-6">
                <div className="mailSend">
                        {this.state.visible&& <AlertRegistrationSucces/>}
                    </div>
                          <div className="resultReg">{ this.state.message }</div>
                </div>

          <div className="offset-md-4 col-12 col-md-4">
            <div className="registration-container">
                <form className="input-in-form" onSubmit={this.handleSubmit}>
                    <Form.Group className="registration-form"  controlId="formBasicEmail">
                        <InputGroup>
                            <InputGroup.Prepend>
                                <InputGroup.Text id="inputGroupPrepend"><EmailOutlinedIcon/></InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Control
                                type="email"
                                placeholder="Email"
                                name="email"
                                onChange={this.handleChange}
                                required
                            />
                        </InputGroup>
                    </Form.Group>

                     <Form.Group  className="registration-form" controlId="formBasicPassword">
                        <InputGroup>
                            <InputGroup.Prepend>
                                <InputGroup.Text onClick={this.showPassword} >
                                {this.state.passwordHidden ? <VisibilityOffOutlinedIcon/> : <VisibilityOutlinedIcon/>}
                                </InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Control
                                type={this.state.passwordHidden ? "password" : "text"}
                                placeholder="Hasło"
                                name="password"
                                onChange={this.handleChange}
                                required
                            />
                        </InputGroup>
                    </Form.Group>

                    <Form.Group className="registration-form" controlId="formRepeatedPassword">
                        <InputGroup>
                            <InputGroup.Prepend>
                                <InputGroup.Text onClick={this.showPasswordRepeated}>
                                {this.state.passwordRepeatedHidden ? <VisibilityOffOutlinedIcon/> : <VisibilityOutlinedIcon/>}
                                </InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Control
                                type={this.state.passwordRepeatedHidden ? "password" : "text"}
                                placeholder="Powtórz hasło"
                                name="repeatedPassword"
                                onBlur={this.checkPassword}
                                required
                            />
                        </InputGroup>
                    </Form.Group>

                    <Form.Group className="registration-form"  controlId="formBasicName">
                        <InputGroup>
                            <InputGroup.Prepend>
                                <InputGroup.Text ><PermIdentityOutlinedIcon/></InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Control
                                type="text"
                                placeholder="Imię"
                                name="name"
                                onChange={this.handleChange}
                                required
                            />
                        </InputGroup>
                    </Form.Group>

                    <Form.Group className="registration-form"  controlId="formBasicSurname">
                        <InputGroup>
                            <InputGroup.Prepend>
                                <InputGroup.Text ><PermIdentityOutlinedIcon/></InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Control
                                type="text"
                                placeholder="Nazwisko"
                                name="surname"
                                onChange={this.handleChange}
                                required
                            />
                        </InputGroup>
                    </Form.Group>

                    <AutoComplete 
                        controlId="formBasicCity" 
                        label="Miasto" dest="cities" 
                        name="city" required="true" 
                        placeholder="Wprowadź..." 
                        onSelect={this.handleCityChange} 
                        value={this.state.city} />
					<AutoComplete 
                        controlId="formBasicCountry" 
                        label="Kraj" dest="countries" 
                        name="country" required="true" 
                        placeholder="Wprowadź..." 
                        value={this.state.country} 
                        onSelect={this.handleCountryChange} />
                        <div className="x">
                    <button type="submit" className="button-login btn-red" disabled={!this.state.match}>
                        Zarejestruj
                    </button>
                    <Link to="/login">
                    <div className = "button-return">
                    <button type="button" className="button-login" >
                            Powrót do logowania
                    </button>
                    </div>
                    </Link>
                    </div>                
                </form>
                </div>   
            </div>
             </div>
        
        );
    }
}

export default Registration;