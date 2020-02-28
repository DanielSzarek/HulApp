import React from 'react';
import { GoogleReCaptchaProvider } from 'react-google-recaptcha-v3';

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
         console.log("email "+this.state.email)
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

const ReCaptchaComponent = () => {
  const { executeRecaptcha } = useGoogleReCaptcha();
  const token = executeRecaptcha("login_page");
 
  return (...)
}

    render(){
        return(
            <div>

        <GoogleReCaptchaProvider
                reCaptchaKey="6Lfxoc4UAAAAAAt8MKjQQdAhGR_Z_cEDI8XqNyJf"
            >
            <GoogleReCaptcha onVerify={token => console.log(token)} />
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
        </GoogleReCaptchaProvider>
     </div>
        );
  }  
}
export default LoggingForm;