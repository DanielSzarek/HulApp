import React from 'react';
import { Form, Button } from 'react-bootstrap';
import '../Styles/App.css';
import '../Styles/Login.css';
import {Link} from 'react-router-dom';
import logo from '../Images/logo.png';


class NewPwdRequest extends React.Component {

      constructor(props) {
        super(props);
        this.state = {
                email: '',
                message: ''
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
      }

       handleSubmit = (event) => {
        event.preventDefault();
         console.log("email "+this.state.email)
         //walidacja
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
                if(response.status === 204 ){
                    console.log("SUCCESSS")
                    this.setState({message: "Na Twój mail została wysłana wiadomość z linkiem umozliwiającym zmianę hasła"});
                        
                }
                else{
                    console.log("SOMETHING WENT WRONG")
                    this.setState({ message: "Something went wrong. Response status: "+response.status });
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


  render(){
    return(
     <div className="offset-md-4 col-12 col-md-4">

            <div className="logging-container">

            <img src={logo} alt={"logo"}/>
         
                <form className="input-in-form" onSubmit={this.handleSubmit}>
                    <Form.Group controlId="formBasicEmail">
                        <Form.Label >Email</Form.Label>
                        <Form.Control name="email" type="email"  onChange={this.handleChange} required/>
                    </Form.Group>
                   
                    <button type="submit" className="button-login btn-red">
                        Zmień hasło
                    </button>
                </form>
           

            <div className="result">{ this.state.message }</div>

            
            </div>
            </div>
        );
    }  
}

export default NewPwdRequest;
