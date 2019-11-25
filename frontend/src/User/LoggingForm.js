import React from 'react';

class LoggingForm extends React.Component{

    constructor(props) {
        super(props);
        this.state = {
                email: '',
                password: '',
                username: '',
                message: ''
        };
      }

      handleSubmit = (event) => {
        event.preventDefault();
       // alert("tutaj 2");
         console.log("email "+this.state.email)
         //walidacja
         fetch('http://hulapp.pythonanywhere.com/auth/jwt/create/', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                username: this.state.email,
                email: this.state.email,
                password: this.state.password
            })
            })
            .then((response) => {
                if(response.status === 201 ){
                    console.log("SUCCESSS")
                    this.setState({message: "Jesteś zalogowany "});
                    //return response.json();     
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

    render(){
        return(
            <div>
            <form onSubmit={this.handleSubmit}>
                <input 
                    type="text" 
                    placeholder="E-mail"
                    value={this.state.email}
                    onChange={event => this.setState({ email: event.target.value })}
                    required/>
                <input 
                    type="password" 
                    placeholder="Hasło" 
                    style={{marginLeft: "26px"}} 
                    value={this.state.password}
                    onChange={event => this.setState({ password: event.target.value })}
                    required />
                <div className="form-container-to-log-in-button">
                    <button  type="submit" class="btn btn-primary active btn-lg" style={{marginLeft: "26px"}} >
                        Zaloguj mnie!
                    </button>
                </div>
            </form>
            <div className="result">{ this.state.message }</div>
            </div>
        );
  }  
}
export default LoggingForm;