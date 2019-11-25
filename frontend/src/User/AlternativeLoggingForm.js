import React from 'react';
import { Form, Button } from 'react-bootstrap';
import '../Styles/App.css';

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
      }

      handleSubmit = (event) => {
        event.preventDefault();
         console.log("email "+this.state.email)
         //walidacja
         fetch('http://hulapp.pythonanywhere.com/auth/jwt/create/', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email: this.state.email,
                password: this.state.password
            })
            })
            .then((response) => {
                if(response.status === 200 ){
                    console.log("SUCCESSS")
                    this.setState({message: "Jesteś zalogowany "});
                    return response.json();     
                }
                else if(response.status === 401){
                    console.log("UNAUTHORIZED")
                    this.setState({message: "Brak autoryzacji"});
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
            <div>
            <center>
                <form class="input-in-form" onSubmit={this.handleSubmit}>
                    <Form.Group controlId="formBasicEmail">
                        <Form.Label >Email</Form.Label>
                        <Form.Control name="email" type="email" placeholder="Enter email" onChange={this.handleChange} />
                    </Form.Group>
                    <Form.Group controlId="formBasicPassword">
                        <Form.Label>Hasło</Form.Label>
                        <Form.Control name="password" type="password" placeholder="Password" onChange={this.handleChange} />
                    </Form.Group>
                    <Button variant="primary" type="submit">
                        Potwierdź
                    </Button>
                </form>
            </center>

            <div className="result">{ this.state.message }</div>
            </div>
        );
    }  
}
export default LoggingForm;