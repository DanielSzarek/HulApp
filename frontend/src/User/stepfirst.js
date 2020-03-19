import React from 'react';
import '../Styles/App.css';
import '../Styles/Registration.css';
import '../Styles/Login.css';
import EmailOutlinedIcon from '@material-ui/icons/EmailOutlined';
import {Form, InputGroup} from 'react-bootstrap';
import VisibilityOutlinedIcon from '@material-ui/icons/VisibilityOutlined';
import VisibilityOffOutlinedIcon from '@material-ui/icons/VisibilityOffOutlined';

class RegistrationFirstStep extends React.Component{

    constructor(props) {
        super(props);
        this.state = {
                email: '',
                password: '',
                repeatedPassword: '',
                message: '',
				match: false,
                visible: false,
                success: false,
                passwordHidden : true,
                counterRepeated : 1,
                passwordRepeatedHidden : true,
                counter : 1,
                passwordOk : false
        };
		
		this.checkPassword = this.checkPassword.bind(this);
		this.handleChange = this.handleChange.bind(this);
      }
	
	handleChange = (event) => {
    this.props.onChange(event);
  }

//   handlePasswordCorectness = () => {
//       this.props.handlePasswordCorectness();
//   }
	
	
    checkPassword = (event) => {
        if (event.target.value && event.target.value !== this.props.value.password) {
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

       showPassword = () => {
        this.setState({ counter : this.state.counter + 1 })
        if (this.state.counter % 2 == 0){
        this.setState({
            passwordHidden : false
        })
        }else { this.setState({passwordHidden : true})}
    };

    showPasswordRepeated = () => {
        this.setState({ counterRepeated : this.state.counterRepeated + 1 })
        if (this.state.counterRepeated % 2 == 0){
        this.setState({
            passwordRepeatedHidden : false
        })
        }else { this.setState({passwordRepeatedHidden : true})}
    };
	  
    render(){
        return(
            <div>
                <div className="alt-reg-form">
                <form className="input-in-form" onSubmit={this.handleSubmit}>
                    <Form.Group className="registration-form"  controlId="formBasicEmail">
                        <InputGroup>
                            <InputGroup.Prepend>
                                <InputGroup.Text id="inputGroupPrepend"><EmailOutlinedIcon/></InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Control
                                type="email"
                                placeholder="Email"
                                name="email"
								value={this.props.value.email}
                                onChange={this.handleChange}
                                required
                            />
                        </InputGroup>
                    </Form.Group>

                     <Form.Group  className="registration-form" controlId="formBasicPassword">
                        <InputGroup>
                            <InputGroup.Prepend>
                                <InputGroup.Text onClick={this.showPassword} >
                                {this.state.passwordHidden ? <VisibilityOffOutlinedIcon/> : <VisibilityOutlinedIcon/>}
                                </InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Control
                                type={this.state.passwordHidden ? "password" : "text"}
                                placeholder="Hasło"
                                name="password"
                                onChange={this.handleChange}
                                value={this.props.value.password}
                                required
                            />
                        </InputGroup>
                    </Form.Group>

                     <Form.Group className="registration-form" controlId="formRepeatedPassword">
                        <InputGroup>
                            <InputGroup.Prepend>
                                <InputGroup.Text onClick={this.showPasswordRepeated}>
                                {this.state.passwordRepeatedHidden ? <VisibilityOffOutlinedIcon/> : <VisibilityOutlinedIcon/>}
                                </InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Control
                                type={this.state.passwordRepeatedHidden ? "password" : "text"}
                                placeholder="Powtórz hasło"
                                name="repeatedPassword"
                                onBlur={this.checkPassword}
                                value={this.props.value.repeatedPassword}
                                required
                            />
                        </InputGroup>
                    </Form.Group>
                    </form>

                </div>
            </div>


        
        );
    }
}

export default RegistrationFirstStep;