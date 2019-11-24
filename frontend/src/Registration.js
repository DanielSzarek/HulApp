import React from 'react';

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
                message: ''
        };
      }

    handleSubmit = (event) => {
        event.preventDefault();
       // alert("tutaj 2");
         console.log("email "+this.state.email)
         //walidacja
         fetch('http://hulapp.pythonanywhere.com/api/users', {
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
                }
            })
            .catch((error) => {
                this.setState({message: "ERROR " + error});
            });
    };

    checkPassword = (value) => {
        if (value && value !== this.state.password.value) {
          alert("The passwords don't match");
        } else {
          //callback();
        }
      };
    render(){
        return(
            <div>
            <form onSubmit={this.handleSubmit}>
                <input 
                    type="text" 
                    placeholder="E-mail"
                    maxLength="254"
                    value={this.state.email}
                    onChange={event => this.setState({ email: event.target.value })}
                    required/>
                <input 
                    type="password" 
                    placeholder="Hasło" 
                    minLength="5"
                    style={{marginLeft: "26px"}} 
                    value={this.state.password}
                    onChange={event => this.setState({ password: event.target.value })}
                    required />
                <input 
                    type="password" 
                    placeholder="Powtórz hasło" 
                    minLength="5"
                    style={{marginLeft: "26px"}}  
                    value={this.state.repeatedPassword}
                    onChange={event => this.setState({ repeatedPassword: event.target.value })}
                    onBlur={event => this.checkPassword(event.target.value,this.state.password)}
                    required />
                <input 
                    type="text" 
                    placeholder="Imię" 
                    style={{marginLeft: "26px"}} 
                    value={this.state.name}
                    onChange={event => this.setState({ name: event.target.value })}
                    required />
                <input 
                    type="text" 
                    placeholder="Nazwisko" 
                    style={{marginLeft: "26px"}} 
                    value={this.state.surname}
                    onChange={event => this.setState({ surname: event.target.value })}
                    required />

                <input 
                    type="text" 
                    placeholder="Miasto" 
                    style={{marginLeft: "26px"}} 
                    value={this.state.city}
                    onChange={event => this.setState({ city: event.target.value })}
                    required />

                <input 
                    type="text" 
                    placeholder="Kraj" 
                    style={{marginLeft: "26px"}} 
                    value={this.state.country}
                    onChange={event => this.setState({ country: event.target.value })}
                    required /> 
                
                <div className="form-container-to-register-button">
                    <button  type="submit" className="btn btn-primary active btn-lg" style={{marginLeft: "26px"}} onClick={this.validate}>
                        Dodaj mnie!
                </button>
                </div>
            </form>
            <div className="result">{ this.state.message }</div>
            </div>
        );
    }
}

export default Registration;