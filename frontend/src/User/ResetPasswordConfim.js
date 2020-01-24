import React from 'react';
import '../Styles/UserProfile.css';
import '../Styles/index.css';
import '../Styles/Login.css';
import '../Styles/App.css';
import { Link , Redirect} from 'react-router-dom';
import { Form, Button } from 'react-bootstrap';
import AlertNewPwdConfirmed from './AlertNewPwdConfirmed';
import logo from '../Images/logo_hulapp.png';

class ResetPasswordConfirm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            uid: this.props.match.params.uid,
            token: this.props.match.params.token,
            message: '',
            res: '',
            newPassword: '',
            newPasswordRepeated: '',
            match: false,
            passwordChanged: false,
            visible: false,
            messageError: ''

        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.checkPassword = this.checkPassword.bind(this);

    }

    handleSubmit(event) {
        event.preventDefault();
        fetch('http://hulapp.pythonanywhere.com/auth/users/reset_password_confirm/', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                uid: this.state.uid,
                token: this.state.token,
                new_password: this.state.newPasswordRepeated

            })
        })
            .then((response) => {
                let self = this;

                if (response.status === 204) {
                    console.log("SUCCESSS")
                    this.setState({ message: "" , visible: true});
                    {this.state.visible&&setTimeout(() => this.setState({ passwordChanged: true }), 6000)}
                }
                else if (response.status >= 400) {
                    console.log("FAILED: ")
                    var json = response.json().then((obj) => {
					var allPropertyNames = Object.keys(obj);
					var err = "";
					for (var j=0; j<allPropertyNames.length; j++) {
						var name = allPropertyNames[j];
						var value = obj[name];
						err += name + ": " + value + " ";
					}
                    self.setState({ message: "Zmiana hasła nie jest możliwa: " + err, newPassword: "", newPasswordRepeated: ""  });
					});
                }
                else {
                    console.log("SOMETHING WENT WRONG")
                    this.setState({ message: "" });
                }
                return response.json();

            })
            .then((data) => {
                this.setState({ res: data })
            })
            .catch((error) => {
                this.setState({ messageError: "ERROR " + error });
            });
    };

     checkPassword = (event) => {
        if (event.target.value && event.target.value !== this.state.newPassword) {
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

       handleChange(event) {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    render() {
        if (this.state.passwordChanged){
    return (<Redirect to='/login'  />)
		}
        return (
            <div>

             <div>
                <img src={logo} alt={"logo"} />

                <div className="offset-md-3 col-12 col-md-6">
                    <div className="forgotPwd">
                        <h2>Podaj swoje nowe hasło: </h2>
                          <div className="resultReg">{this.state.message}</div>
                    </div>
                     <div className="mailSend">
                        {this.state.visible&& <AlertNewPwdConfirmed/>}
                    </div>
                </div>
                <div className="offset-md-4 col-12 col-md-4">
                    <div className="logging-container">
                            <form className="input-in-form" onSubmit={this.handleSubmit}>
                                <Form.Group controlId="formBasicPassword">
                                    <Form.Control name="newPassword" type="password" onChange={this.handleChange} value={this.state.newPassword} placeholder="Hasło" required />
                                </Form.Group>
                                <Form.Group controlId="formBasicPasswordRepeated">
                                    <Form.Control name="newPasswordRepeated" type="password" onChange={this.handleChange} value={this.state.newPasswordRepeated} onBlur={this.checkPassword} placeholder="Powtórz hasło" required />
                                </Form.Group>

                                <button type="submit" className="button-login btn-red">
                                    Potwierdź</button>
                            </form>
                            <Link to="/">
                                <button type="button" className="button-login button-forgotten-pwd" >
                                    Powrót</button>
                            </Link>
                      


                    </div>
                </div>
            </div>

            </div>
        )
    }
}
export default ResetPasswordConfirm;