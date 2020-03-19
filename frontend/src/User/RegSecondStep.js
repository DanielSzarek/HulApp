import React from 'react';
import '../Styles/App.css';
import '../Styles/Registration.css';
import '../Styles/Login.css';
import {Form, InputGroup} from 'react-bootstrap';
import PermIdentityOutlinedIcon from '@material-ui/icons/PermIdentityOutlined';
import AutoComplete from './SelectAutocomplete';


class RegistrationSecondStep extends React.Component{
  
    constructor(props) {
        super(props);
        this.state = {
                name: '',
                surname: '',
                country: '',
                city: '',
                message: '',
				match: false,
                visible: false,
                success: false,
                counterRepeated : 1,
                counter : 1,
                isCityName : false,
                isCountryName : false
        };
        this.handleChange = this.handleChange.bind(this);
		this.handleCityChange = this.handleCityChange.bind(this);
		this.handleCountryChange = this.handleCountryChange.bind(this);
		this.handleCountryChange = this.handleCountryChange.bind(this);
      }

      	handleChange = (event) => {
    this.props.onChange(event);
  }

  handleCityChange(val){
		this.setState({
            city: val,
            isCityName : true,
        })
	}
	
	handleCountryChange(val){
		this.setState({
            country: val,
            isCountryName : true
            })
	}

    handleSelect = (event) => {
        this.props.onSelect(event);
    }
	
	  
    render(){
        return(
            <div>
                <div className="alt-reg-form">
                <form className="input-in-form" onSubmit={this.handleSubmit}>
                    <Form.Group className="registration-form"  controlId="formBasicEmail">
                   <Form.Group className="registration-form"  controlId="formBasicName">
                        <InputGroup>
                            <InputGroup.Prepend>
                                <InputGroup.Text ><PermIdentityOutlinedIcon/></InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Control
                                type="text"
                                placeholder="Imię"
                                name="name"
                                onChange={this.handleChange}
                                value={this.props.value.name}
                                required
                            />
                        </InputGroup>
                    </Form.Group>

                    <Form.Group className="registration-form"  controlId="formBasicSurname">
                        <InputGroup>
                            <InputGroup.Prepend>
                                <InputGroup.Text ><PermIdentityOutlinedIcon/></InputGroup.Text>
                            </InputGroup.Prepend>
                            <Form.Control
                                type="text"
                                placeholder="Nazwisko"
                                name="surname"
                                onChange={this.handleChange}
                                value={this.props.value.surname}

                                required
                            />
                        </InputGroup>
                    </Form.Group>

                    <AutoComplete 
                        controlId="formEditCity" 
                        label="Miasto" 
                        dest="cities" 
                        name="city" 
                        required="true" 
                        onSelect={this.handleSelect} 
                        value = {this.props.value.cityName > 0 ? this.props.value.cityName : "Miasto"}
                        defVal="Miasto"
                         />

                    <AutoComplete 
                        controlId="formEditCountry" 
                        label="Kraj" 
                        dest="countries" 
                        name="countryId" 
                        required="true" 
                        value={this.props.value.country}
						defVal={this.state.countryName > 0 ? this.props.value.countryName : "Państwo"}
                        onSelect={this.handleSelect} />                  
                    </Form.Group>
                    </form>
                    </div>
            </div>
        
        );
    }
}

export default RegistrationSecondStep;