import React from 'react';
import logo from '../Images/logo.png';
import { Form, Button } from 'react-bootstrap';
import {Link} from 'react-router-dom';
import '../Styles/App.css';
import '../Styles/UserProfile.css';
import AutoComplete from './SelectAutocomplete';
import AuthService from './AuthService';
import { Redirect } from 'react-router-dom';
import Navbarex from './Navbar';
import Avatar from 'react-avatar';
import axios from 'axios';
import {Alert } from "shards-react";



class ProfileEdition extends React.Component{

    constructor(props) {
        super(props);
        this.state = {
                userId: '',
                email: '',
                name: '',
                surname: '',
                countryId: '',
                city: '',
                description: '',
                age: '',
                message: '',
                showAlert: false,
                auth: true,
                visible: true 
        };
		this.Auth = new AuthService();
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleCityChange = this.handleCityChange.bind(this);
		this.handleCountryChange = this.handleCountryChange.bind(this);
        this.dismiss = this.dismiss.bind(this);

      }
	  
     async componentDidMount(){
		  if ( await this.Auth.loggedIn()){
            this.Auth.fetch('http://hulapp.pythonanywhere.com/auth/users/me/')
		    .then((res) => {
			  console.log(res);
			  this.setState({
				  name: res.first_name,
				  userId: res.id,
				  surname: res.last_name,
				  email: res.email,
				  countryId: 2,
                  fileUploaded : null,
                  city:res.city,
                  src : res.profile_img
			  })
		  })
		  .catch((error) => {
                console.log({message: "ERROR " + error});
            });
		  }
		  else{
			  this.setState({auth: false});
		  }
	  }

//dodaj token w headersach

  handleSubmit = (event) => {
        event.preventDefault();
         this.Auth.fetch('http://hulapp.pythonanywhere.com/auth/users/me/', {
            method: 'PATCH',

            body: JSON.stringify({
				// id: this.state.userId,
                // email: this.state.email,              
                first_name: this.state.name,
                last_name: this.state.surname,
                country: this.state.countryId,
				city: this.state.city
            })
            })
            .then((response) => {
                if(response.status >= 200 && response.status <300){
                    console.log("SUCCESSS")
                    return response.json();     
                }
                // else{
                //     console.log("SOMETHING WENT WRONG")
                //     // this.setState({ message: "Something went wrong. Response status: "+response.status+", response detail" + response.detail });
                //     this.setState({ message: "Something went wrong. Response status: " +response.status  });

                // }
            })
            // .catch((error) => {
            //     this.setState({message: "ERROR " + error});
            //     console.log(error);
            // });
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
		this.setState({countryId: val})
	}
      
      fileSelectedHandler = (event)=>{
        console.log(event.target.files[0]);
        this.setState({
            fileUploaded : event.target.files[0]
        })
    }

    fileUploadHandler = () => {
        console.log("downloaded " +this.state.fileUploaded)

        const fd = new FormData();
        fd.append('profile_img', this.state.fileUploaded, this.state.fileUploaded.name);
		fd.append('username', this.state.email);
        console.log("fD " +this.state.fileUploaded.name);
		
		axios(
		{ 
			method: 'PATCH',
			url: 'http://hulapp.pythonanywhere.com/auth/users/me/', 
			headers: { 'content-type': 'multipart/form-data', 'Authorization': "Bearer " +  localStorage.getItem('id_token')}, 
			data: fd })
        .then(res =>{
            this.setState({src: res.profile_img});
            window.location.reload();
			
        } 
    );
 
    }

    submitHandlerAlertShow = () => {
        this.setState({
            showAlert : true
        })
    }


    render(){
        console.log("this.state.countryId" + this.state.countryId);
        return(
            <div>
                <Navbarex/>
            <div className="offset-md-1 col-12 col-md-10">
            {(this.state.auth) ? '' : <Redirect to="/" />}
                <div className="edit-container">
                    <h1>Edytuj profil</h1>
                        <hr/>
                        <div className="row">
                        <div className="col-4">
                        <input type='file' onChange={this.fileSelectedHandler} accept="image/*"/>
                        <button onClick={this.fileUploadHandler}> Upload! </button>
                        <Avatar  size='300' round="300px" name="H"  src={this.state.src}  />
                </div>
                <div className='col-8'>
                    <form className="input-in-form" onSubmit={this.handleSubmit}>
                    <h2>Osoba:</h2>
                    <hr />
                    <Form.Group controlId="formEditName">
                        <Form.Label>Imię:</Form.Label>
                        <Form.Control name="name" type="text" value={this.state.name} onChange={this.handleChange} required/>
                    </Form.Group>
                    <Form.Group controlId="formEditSurname">
                        <Form.Label>Nazwisko:</Form.Label>
                        <Form.Control name="surname" type="text" value={this.state.surname} onChange={this.handleChange} required/>
                    </Form.Group>
                    <AutoComplete 
                        controlId="formEditCity" 
                        label="Miasto" dest="cities" 
                        name="city" required="true" 
                        placeholder="Wprowadź..." 
                        onSelect={this.handleCityChange} 
                        value={this.state.city} />
					<AutoComplete 
                        controlId="formEditCountry" 
                        label="Kraj" dest="countries" 
                        name="countryId" required="true" 
                        placeholder="Wprowadź..." 
                        value={this.state.countryId} 
                        onSelect={this.handleCountryChange} />
                        {this.state.showAlert &&
                            <div className="y">
                            <Alert theme="warning" dismissible={this.dismiss} open={this.state.visible} >
                                Dane zostały zmienone 
                            </Alert>
                            </div>
                        }
                    <button type="submit" onClick={this.submitHandlerAlertShow} className="button-login btn-red">
                        Edytuj
                    </button>
                </form>
                </div>
                </div>
                    <div className="result">{ this.state.message }</div>
                </div>            
            </div>

            
            </div>
        );
    }
    dismiss() {
    this.setState({ visible: false });
  }
}

export default ProfileEdition;