import React from 'react';
import { Form, InputGroup } from 'react-bootstrap';
import '../Styles/App.css';
import '../Styles/UserProfile.css';
import AutoComplete from './SelectAutocomplete';
import AuthService from './AuthService';
import { Redirect } from 'react-router-dom';
import Navbarex from './Navbar';
import Avatar from 'react-avatar';
import axios from 'axios';
import { Alert } from "shards-react";
import PhotoCameraIcon from '@material-ui/icons/PhotoCamera';
import CheckIcon from '@material-ui/icons/Check';
import LoadingSpinner from 'react-loader-spinner'
import AccountCircleIcon from '@material-ui/icons/AccountCircle';


class ProfileEdition extends React.Component {
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
            visible: true,
            cityName: '',
            countryName: '',
            redirect: false,
            showSubmit: false,
            loadig: false,
        };
        this.Auth = new AuthService();
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleCityChange = this.handleCityChange.bind(this);
        this.handleCountryChange = this.handleCountryChange.bind(this);
        this.dismiss = this.dismiss.bind(this);
        this.cityChanged = this.cityChanged.bind(this)
    }

    async componentDidMount() {
        if (await this.Auth.loggedIn()) {
            this.Auth.fetch('http://hulapp.pythonanywhere.com/auth/users/me/')
                .then((res) => {
                    this.setState({
                        name: res.first_name,
                        userId: res.id,
                        surname: res.last_name,
                        email: res.email,
                        countryId: res.country,
                        fileUploaded: null,
                        city: res.city,
                        src: res.profile_img,
                        endSprinner: false,
                    });
                    this.Auth.fetch(`http://hulapp.pythonanywhere.com/api/cities/${res.city}`)
                        .then((r) => {
                            this.setState({
                                cityName: r.name,
                            });
                        });
                    this.Auth.fetch(`http://hulapp.pythonanywhere.com/api/countries/${res.country}`)
                        .then((r) => {
                            this.setState({
                                countryName: r.name,
                            });
                        });

                })
                .catch((error) => {
                    console.log({ message: "ERROR " + error });
                });
        }
        else {
            this.setState({ auth: false });
        }
    }

    handleSubmit = (event) => {
        event.preventDefault();
        this.Auth.fetch('http://hulapp.pythonanywhere.com/auth/users/me/', {
            method: 'PATCH',

            body: JSON.stringify({
                first_name: this.state.name,
                last_name: this.state.surname,
                country: this.state.countryId,
                city: this.state.city
            })
        })
            .then((response) => {
                if (response.status >= 200 && response.status < 300) {
                    return response.json();
                }
            })
        { setTimeout(() => { window.location.reload() }, 3500) }
    };

    handleChange(event) {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    handleCityChange(val) {
        console.log("city handleCityChange autocomplete changed " + val);
        this.setState({ city: val.id })
    }

    cityChanged(val) {
        console.log("city cityChanged autocomplete changed " + val);
    }

    handleCountryChange(val) {
        this.setState({ countryId: val.id })
    }

    fileSelectedHandler = (event) => {
        this.setState({
            fileUploaded: event.target.files[0],
            showSubmit: true
        })
    }

    fileUploadHandler = () => {
        const fd = new FormData();
        fd.append('profile_img', this.state.fileUploaded, this.state.fileUploaded.name);
        fd.append('username', this.state.email);
        this.setState({
            loading: true,
            showSubmit: false
        })

        axios(
            {
                method: 'PATCH',
                url: 'http://hulapp.pythonanywhere.com/auth/users/me/',
                headers: { 'content-type': 'multipart/form-data', 'Authorization': "Bearer " + localStorage.getItem('id_token') },
                data: fd
            })
            .then(res => {
                this.setState({
                    src: res.profile_img,
                    loading: false
                });
                window.location.reload();
            }
            );
    }

    submitHandlerAlertShow = () => {
        this.setState({
            showAlert: true
        })
    }

    render() {
        return (
            <div>
                <Navbarex />
                <div className="offset-md-1 col-12 col-md-10">
                    {(this.state.auth) ? '' : <Redirect to="/" />}
                    <div className="edit-container">
                        <h1>EDYTUJ PROFIL</h1>
                        <hr />
                        <div className="row">
                            <div className="col-4">
                                <div className="button-photo-wrapper">
                                    <label className="new-button" htmlFor="upload"><PhotoCameraIcon /> zdjęcie </label>
                                    <input name="myfile" id="upload" type='file' onChange={this.fileSelectedHandler} accept="image/*" />
                                </div>
                                {this.state.loading ? <div className="spinner"><LoadingSpinner /></div> : <Avatar size='300' round="300px" name="H" src={this.state.src} />}
                                {this.state.showSubmit && <button className="submit-photo-button" onClick={this.fileUploadHandler}><CheckIcon /> Załaduj! </button>}

                            </div>
                            <div className='col-8'>
                                <form className="input-in-form" onSubmit={this.handleSubmit}>
                                    <Form.Group controlId="formEditName">
                                        <Form.Label>Imię:</Form.Label>
                                        <InputGroup.Prepend>
                                            <div className="inputgrouptext">
                                                <InputGroup.Text>
                                                    <AccountCircleIcon />
                                                </InputGroup.Text>
                                            </div>
                                            <Form.Control name="name" type="text" value={this.state.name} onChange={this.handleChange} required />
                                        </InputGroup.Prepend>
                                    </Form.Group>
                                    <Form.Group controlId="formEditSurname">
                                        <Form.Label>Nazwisko:</Form.Label>
                                        <InputGroup.Prepend>
                                            <div className="inputgrouptext">
                                                <InputGroup.Text>
                                                    <AccountCircleIcon />
                                                </InputGroup.Text>
                                            </div>
                                            <Form.Control name="surname" type="text" value={this.state.surname} onChange={this.handleChange} required />
                                        </InputGroup.Prepend>
                                    </Form.Group>
                                    <Form.Group>
                                        <div className="nameMiasto"> Miasto: </div>
                                        <AutoComplete
                                            controlId="formEditCity"
                                            label="Miasto" dest="cities"
                                            name="city" required="true"
                                            onSelect={this.handleCityChange}
                                            onChange={this.cityChanged}
                                            value={this.state.city}
                                            defVal={this.state.cityName} />
                                        <div className="name"> Kraj: </div>
                                        <AutoComplete
                                            controlId="formEditCountry"
                                            label="Kraj" dest="countries"
                                            name="countryId" required="true"
                                            value={this.state.countryId}
                                            defVal={this.state.countryName}
                                            onSelect={this.handleCountryChange} />
                                    </Form.Group>

                                    {this.state.showAlert &&
                                        <div className="y">
                                            <Alert theme="warning" dismissible={this.dismiss} open={this.state.visible} >
                                                Dane zostały zmienione
                                            </Alert>
                                        </div>
                                    }
                                    <button type="submit" onClick={this.submitHandlerAlertShow} className="button-login btn-red edytuj">
                                        Edytuj
                                    </button>
                                </form>
                            </div>
                        </div>
                        <div className="result">{this.state.message}</div>
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
