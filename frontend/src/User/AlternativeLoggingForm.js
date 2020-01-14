import React from 'react';
import { Form, Button } from 'react-bootstrap';
import '../Styles/App.css';
import '../Styles/Login.css';
import {Link} from 'react-router-dom';
import logo from '../Images/logo.png';
import {GoogleReCaptchaProvider,GoogleReCaptcha} from 'react-google-recaptcha-v3';
import AuthService from './AuthService';


class LoggingForm extends React.Component{

    constructor(props) {
        super(props);
        this.state = {
                email: '',
                password: '',
                message: ''
                
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

        this.Auth = new AuthService();
      }

      handleSubmit = (event) => {
        event.preventDefault();
         console.log("email "+this.state.email)


        this.Auth.login(this.state.email,this.state.password)
            .then(res =>{
               this.props.history.replace('/profile-edit');
            })
            
            // .catch(err =>{
            //     alert(err);
            // })
            // .then((response) => {
            //     if(response.status === 200 ){
            //         console.log("SUCCESSS")
            //         this.setState({message: "Jesteś zalogowany "});
            //         return response.json();     
            //     }
            //     else if(response.status === 401){
            //         console.log("UNAUTHORIZED")
            //         this.setState({message: "Brak autoryzacji"});
            //     }
            //     else{
            //         console.log("SOMETHING WENT WRONG")
            //         this.setState({ message: "Something went wrong. Response status: "+response.status });
            //     }
            // })
            // .then((resp)=>{ return resp.json() }).then((resp)=>{ console.log(resp) })
            // .then(response => {
            //     console.log(response);
            //     return response.json()
            // })
            .catch((error) => {
                this.setState({message: "ERROR " + error});

                // return response.json();
                // this.setState({resp : "yout problem: " +response});  
            });
    };

    handleChange(event) {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

async componentWillMount(){
    if(await this.Auth.loggedIn())
        this.props.history.replace('/profile-edit');
        console.log("here, bad");
}

    render(){
        return(
     <div className="offset-md-4 col-12 col-md-4">
            <div className="logging-container">
            <GoogleReCaptchaProvider
                reCaptchaKey="6Lfxoc4UAAAAAAt8MKjQQdAhGR_Z_cEDI8XqNyJf">
            <GoogleReCaptcha onVerify={token => console.log("token from reCaptcha: " +token)} />
                <img src={logo} alt={"logo"}/>
         
                <form className="input-in-form" onSubmit={this.handleSubmit}>
                    <Form.Group controlId="formBasicEmail">
                        <Form.Label >Email:</Form.Label>
                        <Form.Control name="email" type="email"  onChange={this.handleChange} required/>
                    </Form.Group>
                    <Form.Group controlId="formBasicPassword">
                        <Form.Label>Hasło:</Form.Label>
                        <Form.Control name="password" type="password"  onChange={this.handleChange} required/>
                    </Form.Group>
                    <button type="submit" className="button-login btn-red">
                        Zaloguj
                    </button>
                </form>
           

            <div className="result">{ this.state.message }</div>

             <Link to="/registration">
              <button  type="button" className="button-login" >
                Załóż konto
              </button>
            </Link>
            <Link to="/forgotten-password">
              <button  type="button" className="button-login button-forgotten-pwd" >
                Nie pamiętam hasła
              </button>
            </Link>
        </GoogleReCaptchaProvider>

            </div>
            </div>
        );
    }  
}
export default LoggingForm;