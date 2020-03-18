import React from 'react';
import '../Styles/App.css';
import '../Styles/Login.css';
import { Link } from 'react-router-dom';
import logo from '../Images/logo_hulapp.png';
import { GoogleReCaptchaProvider, GoogleReCaptcha } from 'react-google-recaptcha-v3';
import AuthService from './AuthService';

import EmailOutlinedIcon from '@material-ui/icons/EmailOutlined';
// import {Col, Form, InputGroup, Button} from 'react-bootstrap';
import {Form, InputGroup} from 'react-bootstrap';

import VisibilityOffOutlinedIcon from '@material-ui/icons/VisibilityOffOutlined';
import VisibilityOutlinedIcon from '@material-ui/icons/VisibilityOutlined';
// import QontoConnector from "../User/nowy"



class LoggingForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            email: '',
            password: '',
            message: '',
            passwordHidden : true,
            counter : 1

        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

        this.Auth = new AuthService();
    }

    handleSubmit = (event) => {
        event.preventDefault();

        this.Auth.login(this.state.email, this.state.password)
            .then(res => {
                this.props.history.replace('/profile-edit');
            })
            .catch((error) => {
                this.setState({ message: "ERROR " + error });
            });
    };

    handleChange(event) {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    showPassword = () => {
        this.setState({ counter : this.state.counter + 1 })
        if (this.state.counter % 2 == 0){
        this.setState({
            passwordHidden : false
        })
        }else { this.setState({passwordHidden : true})}
    }

    async componentWillMount() {
        if (await this.Auth.loggedIn())
            this.props.history.replace('/profile-edit');
    }

    render() {
        return (
            <div>
                <img src={logo} alt={"logo"} />
                {/* <QontoConnector/> */}

                <div className="offset-md-4 col-12 col-md-4">

                    <div className="logging-container">
                        <GoogleReCaptchaProvider
                            reCaptchaKey="6Lfxoc4UAAAAAAt8MKjQQdAhGR_Z_cEDI8XqNyJf">
                            <GoogleReCaptcha />

                    <form className="input-in-form" onSubmit={this.handleSubmit}>
                    <Form.Group className="logging-form"  controlId="formBasicEmail">
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

                     <Form.Group className="logging-form"  controlId="formBasicPassword">
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
                            <div  className="logging-form-buttons" >
                                <button type="submit" className="button-login btn-red">
                                    Zaloguj
                                </button>
                            </div>
                            </form>

                            <div className="result">{this.state.message}</div>

                            <Link to="/registration">
                            <div  className="logging-form-buttons" >
                                <button type="button" className="button-login" >
                                    Załóż konto
                                </button>
                            </div>
                            </Link>
                            <Link to="/reset-password">
                            <div  className="logging-form-buttons" >
                                <button type="button" className="button-login button-forgotten-pwd" >
                                    Nie pamiętam hasła
                                </button>
                            </div>
                            </Link>
                        </GoogleReCaptchaProvider>

                    </div>
                </div>
            </div>
        );
    }
}
export default LoggingForm;