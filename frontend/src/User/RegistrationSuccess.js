import React from 'react';
import { Link, Redirect } from 'react-router-dom';
import logo from '../Images/logo_hulapp.png';
import '../Styles/altReg.css';


class RegistrationSuccess extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            redirectToMain: false
        }
    }

    render() {
        { setTimeout(() => this.setState({ redirectToMain: true }), 6000) }
        if (this.state.redirectToMain) {
            return (<Redirect to='/login' />)
        }
        return (
            <div>
                <img src={logo} alt={"logo"} />
                <div className="registration-success-message">
                    <p>Pozostał Ci ostatni krok.</p>
                    <p> Aby ukończyć rejestrację sprawdź skrzynkę pocztową i kliknij w link aktywacyjny.</p>
                    <p> Czekamy na Ciebie!</p>
                </div>
                <div className="return-to-log return-to-log-incase">
                    Masz już konto?  <Link className="link-to-log" to="/login">Zaloguj się!</Link>
                </div>
            </div>
        );
    }
}

export default RegistrationSuccess;