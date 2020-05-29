import React, { Component } from 'react';
import { Form } from 'react-bootstrap';
import '../Styles/App.css';
import '../Styles/Login.css';
import { Link } from 'react-router-dom';
import logo from '../Images/logo_hulapp.png';
import AlertSendMailForgotPwd from './AlertSendMailForgotPwd';

class ResetPassword extends Component {

    constructor(props) {
        super(props);
        this.state = {
            email: '',
            visible: false,
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit = (event) => {
        event.preventDefault();
        fetch('http://hulapp.pythonanywhere.com/auth/users/reset_password/', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email: this.state.email
            })
        })
            .then((response) => {
                if (response.status >= 200 && response.status < 300) {
                    this.setState({ visible: true })
                    // return response.json(); 
                } else {
                    // this.setState({ message: "Something went wrong. Response status: "+response.status });
                    // this.setState({ answear : response.map(request => ({ password : request}))})
                    // this.setState({answear : response.password})
                }
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

    render() {
        return (
            <div>
                <div>
                    <img src={logo} alt={"logo"} />
                    <div className="offset-md-3 col-12 col-md-6">
                        <div className="forgotPwd">
                            <h2>Zapomniałeś hasła? </h2>
                            <h2>Podaj nam swój e-mail na który zostanie wysłany link.</h2>
                        </div>
                        <div className="mailSend">
                            {this.state.visible && <AlertSendMailForgotPwd />}
                        </div>
                    </div>
                    <div className="offset-md-4 col-12 col-md-4">
                        <div className="logging-container">
                            <form className="input-in-form" onSubmit={this.handleSubmit}>
                                <Form.Group controlId="formBasicEmail">
                                    <Form.Control name="email" type="email" onChange={this.handleChange} placeholder="e-mail" required />
                                </Form.Group>
                                <button type="submit" className="button-login btn-red">
                                    Wyślij</button>
                            </form>
                            <Link to="/">
                                <button type="button" className="button-login button-forgotten-pwd" >
                                    Powrót</button>
                            </Link>

                            <div className="result">{this.state.message}</div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}
export default ResetPassword;