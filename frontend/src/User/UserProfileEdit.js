import React from 'react';
import logo from '../Images/logo.png';
import { Form, Button } from 'react-bootstrap';
import {Link} from 'react-router-dom';
import '../Styles/App.css';
import '../Styles/UserProfile.css';



class Registration extends React.Component{

    constructor(props) {
        super(props);
        this.state = {
            email: '',
                name: '',
                surname: '',
                country: '',
                city: '',
                description: '',
                age: '',
                message: ''
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
      }

    handleSubmit = (event) => {
        event.preventDefault();
        //  console.log("email "+this.state.email)
         //walidacja
         fetch('http://hulapp.pythonanywhere.com/​auth​/users​/me​/', {
            method: 'PATCH',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                username: this.state.email,
                // email: this.state.email,
              
                first_name: this.state.name,
                last_name: this.state.surname,
                // country: this.state.country,
                // city: this.state.city
            })
            })
            .then((response) => {
                if(response.status >= 200 && response.status <300){
                    console.log("SUCCESSS")
                    return response.json();     
                }else{
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

    checkPassword = (value) => {
        if (value && value !== this.state.password.value) {
          alert("The passwords don't match");
        } else {
          //callback();
        }
      };
    render(){
        return(
         
    <div className="offset-md-1 col-12 col-md-10">
            <div className="edit-container">
                {/* <img src={logo} alt={"logo"}/> */}
         <h1>Edytuj profil</h1>
                    <hr/>
                    <div className="row">
                    <div className="col-4">
                    <button onClick={this.uploadHandler}>Edytuj</button>
                    <input type="file" onChange={this.fileChangedHandler}/>
                        
                    </div>
                    <div className='col-8'>
                <form className="input-in-form" onSubmit={this.handleSubmit}>
                    
                    <h2>Osoba:</h2>
                    <hr />
                    <Form.Group controlId="formEditName">
                        <Form.Label>Imię:</Form.Label>
                        <Form.Control name="name" type="text"  onChange={this.handleChange} required/>
                    </Form.Group>
                    <Form.Group controlId="formEditSurname">
                        <Form.Label>Nazwisko:</Form.Label>
                        <Form.Control name="surname" type="text"  onChange={this.handleChange} required/>
                    </Form.Group>
                    <Form.Group controlId="formEditAge">
                        <Form.Label >Wiek:</Form.Label>
                        <Form.Control name="age" type="text"  onChange={this.handleChange} />
                    </Form.Group>
                    <Form.Group controlId="formEditCity">
                        <Form.Label>Miasto:</Form.Label>
                        <Form.Control name="city" type="text"  onChange={this.handleChange} required/>
                    </Form.Group>
                    <Form.Group controlId="formEditCountry">
                        <Form.Label>Kraj:</Form.Label>
                        <Form.Control name="country" type="text"  onChange={this.handleChange} required/>
                    </Form.Group>
                    <Form.Group controlId="formEditDescription">
                        <Form.Label >Opis:</Form.Label>
                        <Form.Control name="description" type="textarea"  onChange={this.handleChange} />
                    </Form.Group>
                    <button type="submit" className="button-login btn-red">
                        Edytuj
                    </button>
                </form>
                </div>
                </div>

                    <div className="result">{ this.state.message }</div>
                </div>            
            </div>
        );
    }
}

export default Registration;