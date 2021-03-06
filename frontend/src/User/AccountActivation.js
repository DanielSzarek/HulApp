import React from 'react';
import '../Styles/UserProfile.css';
import '../Styles/index.css';
import '../Styles/Login.css';
import '../Styles/App.css';
import { Link } from 'react-router-dom';
import logo from '../Images/logo_hulapp.png';

class AccountActivation extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            uid: this.props.match.params.uid,
            token: this.props.match.params.token,
            message: '',
            res: ''
        };
    }

    componentDidMount() {
        fetch('http://hulapp.pythonanywhere.com/auth/users/activation/', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                uid: this.state.uid,
                token: this.state.token

            })
        })
            .then((response) => {
                if (response.status === 204) {
                    this.setState({ message: "Uff, w końcu! Witaj w naszym teamie - zaczynamy :)" });
                }
                else if (response.status >= 400) {
                    this.setState({ message: "Coś poszło nie tak :(" });
                }
                else {
                    this.setState({ message: "Coś poszło nie tak :(" });
                }

            })
            .then((data) => {
                this.setState({ res: data })
            })
            .catch((error) => {
                this.setState({ message: "ERROR " + error });
            });
    };

    render() {
        return (
            <div>
                <div>
                    <img src={logo} alt={"logo"} />
                </div>
                <div className="activationText">
                    <div className="result">{this.state.message}</div>
                </div>
                <br />
                <div className="logging-container">
                    <Link to="/login">
                        <button type="button" className="button-login btn-red confirm-activation">
                            Zaloguj się
                    </button>
                    </Link>
                </div>
            </div>
        )
    }
}
export default AccountActivation;