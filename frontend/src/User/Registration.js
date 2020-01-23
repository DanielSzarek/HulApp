// import React from 'react';
// import logo from '../Images/logo.png';
// import { Form, Button } from 'react-bootstrap';
// import {Link} from 'react-router-dom';
// import '../Styles/App.css';
// import '../Styles/Registration.css';
// import AutocompleteNoToken from './SelectAutocompleteNoToken'


// class Registration extends React.Component{

//     constructor(props) {
//         super(props);
//         this.state = {
//             email: '',
//                 password: '',
//                 repeatedPassword: '',
//                 name: '',
//                 surname: '',
//                 country: '',
//                 city: '',
//                 message: '',
//                 email: '',
// 				match: false
//         };
//         this.handleChange = this.handleChange.bind(this);
//         this.handleSubmit = this.handleSubmit.bind(this);
// 		this.handleCityChange = this.handleCityChange.bind(this);
// 		this.handleCountryChange = this.handleCountryChange.bind(this);
// 		this.handleCountryChange = this.handleCountryChange.bind(this);
// 		this.checkPassword = this.checkPassword.bind(this);
//       }

//     handleSubmit = (event) => {
//         event.preventDefault();
//          console.log("email "+this.state.email)
//          //walidacja
//          fetch('http://hulapp.pythonanywhere.com/auth/users/', {
//             method: 'POST',
//             headers: {
//                 'Accept': 'application/json',
//                 'Content-Type': 'application/json',
//             },
//             body: JSON.stringify({
//                 username: this.state.email,
//                 email: this.state.email,
//                 password: this.state.password,
//                 first_name: this.state.name,
//                 last_name: this.state.surname,
//                 country: this.state.country,
//                 city: this.state.city
//             })
//             })
//             .then((response) => {
//                 if(response.status >= 200 && response.status <300){
//                     console.log("SUCCESSS")
//                     return response.json();     
//                 }else{
//                     console.log("SOMETHING WENT WRONG")
//                     this.setState({ message: "Something went wrong. Response status: "+response.status });
//                 }
//             })
//             .catch((error) => {
//                 this.setState({message: "ERROR " + error});
//             });
//     };

//      handleChange(event) {
//         this.setState({
//             [event.target.name]: event.target.value
//         });
//     }

//     checkPassword = (value) => {
//         if (value && value !== this.state.password.value) {
//           alert("The passwords don't match");
//         } else {
//           //callback();
//         }
//       };
//     render(){
//         return(
         
//     <div className="offset-md-4 col-12 col-md-4">
//             <div className="registration-container">
//                 <img src={logo} alt={"logo"}/>
         
//                 <form className="input-in-form" onSubmit={this.handleSubmit}>
//                     <Form.Group controlId="formBasicEmail">
//                         <Form.Label >Email:</Form.Label>
//                         <Form.Control name="email" type="email"  onChange={this.handleChange} required/>
//                     </Form.Group>
//                     <Form.Group controlId="formBasicPassword">
//                         <Form.Label>Hasło:</Form.Label>
//                         <Form.Control name="password" type="password"  onChange={this.handleChange} required/>
//                     </Form.Group>
//                     <Form.Group controlId="formRepeatedPassword">
//                         <Form.Label>Powtórz hasło:</Form.Label>
//                         <Form.Control name="repeatedPassword" type="password"  onChange={this.handleChange} required/>
//                     </Form.Group>
//                     <Form.Group controlId="formBasicName">
//                         <Form.Label>Imię:</Form.Label>
//                         <Form.Control name="name" type="text"  onChange={this.handleChange} required/>
//                     </Form.Group>
//                     <Form.Group controlId="formBasicSurname">
//                         <Form.Label>Nazwisko:</Form.Label>
//                         <Form.Control name="surname" type="text"  onChange={this.handleChange} required/>
//                     </Form.Group>
//                     <Form.Group controlId="formBasicCity">
//                         <Form.Label>Miasto:</Form.Label>
//                         <Form.Control name="city" type="text"  onChange={this.handleChange} required/>
//                     </Form.Group>
//                     <Form.Group controlId="formBasicCountry">
//                         <Form.Label>Kraj:</Form.Label>
//                         <Form.Control name="country" type="text"  onChange={this.handleChange} required/>
//                     </Form.Group>
//                     <button type="submit" className="button-login btn-red">
//                         Zarejestruj
//                     </button>
//                 </form>

//                     <div className="result">{ this.state.message }</div>
//                 </div>            
//             </div>
//         );
//     }
// }

// export default Registration;


import React from 'react';
import logo from '../Images/logo.png';
import { Form, Button } from 'react-bootstrap';
import {Link} from 'react-router-dom';
import AutoComplete from './SelectAutocompleteNoToken';
import '../Styles/App.css';
import '../Styles/Registration.css';


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
                answear: []
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
         console.log("req ");
		 console.log(JSON.stringify({
                username: this.state.email,
                email: this.state.email,
                password: this.state.password,
                first_name: this.state.name,
                last_name: this.state.surname,
                country: this.state.country,
                city: this.state.city
            }));
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
                if(response.status >= 200 && response.status <300){
                    console.log("SUCCESSS")
                    return response.json(); 
                    
                }else{
                    console.log("SOMETHING WENT WRONG")
                    this.setState({ message: "Something went wrong. Response status: "+response.status });
                    // this.setState({ answear : response.map(request => ({ password : request}))})
                    // this.setState({answear : response.password})
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
		console.log("handleCountryChange: "+val);
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
	  
    render(){
        return(
         
    <div className="offset-md-4 col-12 col-md-4">
            <div className="registration-container">
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
                    <Form.Group controlId="formRepeatedPassword">
                        <Form.Label>Powtórz hasło:</Form.Label>
                        <Form.Control name="repeatedPassword" type="password"  onBlur={this.checkPassword} required/>
                    </Form.Group>
                    <Form.Group controlId="formBasicName">
                        <Form.Label>Imię:</Form.Label>
                        <Form.Control name="name" type="text"  onChange={this.handleChange} required/>
                    </Form.Group>
                    <Form.Group controlId="formBasicSurname">
                        <Form.Label>Nazwisko:</Form.Label>
                        <Form.Control name="surname" type="text"  onChange={this.handleChange} required/>
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
                    </div>
                </form>

                    <div className="result">{ this.state.message }</div>
                    {/* <div className="result">{ this.state.answear.password }</div> */}
                    {/* <div>
                        {this.state.answear.map(item =>
                                <span>{item}</span>
                        )}
                        {this.state.answear}
                    </div> */}

                </div>            
            </div>
        );
    }
}

export default Registration;