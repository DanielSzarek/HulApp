import React from 'react';
import '../Styles/App.css';
import '../Styles/Registration.css';
import '../Styles/Login.css';
import { Form, InputGroup } from 'react-bootstrap';
import PermIdentityOutlinedIcon from '@material-ui/icons/PermIdentityOutlined';
import AutoComplete from './SelectAutocomplete';


class RegistrationSecondStep extends React.Component {

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
            counterRepeated: 1,
            counter: 1,
            isCityName: false,
            isCountryName: false,
            errors: {
        name: 'should be filled',
        surname: 'should be filled'
      }
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleCityChange = this.handleCityChange.bind(this);
        this.handleCountryChange = this.handleCountryChange.bind(this);
        this.handleCountryChange = this.handleCountryChange.bind(this);
        this.validate = this.validate.bind(this);

    }

    componentDidMount () {

    if (this.props.value.name.length > 0) {
      this.setState(prevState => ({
        errors: {
          ...prevState.errors,
          name: ''
        }
      }))
    }
	if (this.props.value.surname.length > 0) {
      this.setState(prevState => ({
        errors: {
          ...prevState.errors,
		  surname: ''
        }
      }))
    }

	if(this.props.value.name.length > 0 && this.props.value.surname.length > 0){
		this.props.onValidated(true);
	}
	else{
		this.props.onValidated(false);
	}
  }

    // handleChange = (event) => {
    //     this.props.onChange(event);
    // }

    handleChange = (event) => {
        this.props.onChange(event);
		
		const { name, value } = event.target

    let errors = this.state.errors
	switch (name) {
      case 'name':
        errors.name = value.length  > 0 ? '' : 'Must be filled!'
        break
      case 'surname':
        errors.surname =
          value.length > 0 ? '' : 'Must be filled'
        break
      default:
        break
    }
	
	this.setState({ errors, [name]: value }, () => {
      console.log(errors)
    })
    this.props.onValidated(this.validate())
	
    }

    handleCityChange(val) {
        this.setState({
            city: val,
            isCityName: true,
        })
    }

    handleCountryChange(val) {
        this.setState({
            country: val,
            isCountryName: true
        })
    }

    handleSelect = (event) => {
        this.props.onSelect(event);
    }


validate = () => {
    return (
      this.state.errors.name.length === 0 &&
      this.state.errors.surname.length === 0
    )
  }
    render() {
        return (
            <div>
                <div className="alt-reg-form">
                    <form className="input-in-form" onSubmit={this.handleSubmit}>
                        <Form.Group className="registration-form" controlId="formBasicEmail">
                            <Form.Group className="registration-form" controlId="formBasicName">
                                <InputGroup>
                                    <InputGroup.Prepend>
                                        <InputGroup.Text ><PermIdentityOutlinedIcon /></InputGroup.Text>
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
                            <Form.Group className="registration-form" controlId="formBasicSurname">
                                <InputGroup>
                                    <InputGroup.Prepend>
                                        <InputGroup.Text ><PermIdentityOutlinedIcon /></InputGroup.Text>
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
                                value={this.props.value.cityName > 0 ? this.props.value.cityName : "Miasto"}
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