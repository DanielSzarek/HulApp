import React from 'react';
import Navbarex from './Navbar';
import LoadingSpinner from 'react-loader-spinner'
import { Form, InputGroup } from 'react-bootstrap';
import AuthService from './AuthService';
import Avatar from 'react-avatar';
import '../Styles/Navbar.css';
import LocationCityIcon from '@material-ui/icons/LocationCity';
import PublicIcon from '@material-ui/icons/Public';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import { Redirect } from 'react-router-dom';



class SearchUser extends React.Component {

constructor(props) {
        super(props);
        this.state = {
                userId: this.props.match.params.userId,
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
                visible: true ,
				        cityName: '',
				        countryName: '',
                redirect : false,
                showSubmit : false,
                loadig : false,
        };
		    this.Auth = new AuthService();
      }

       async componentDidMount(){
		  if ( await this.Auth.loggedIn()){
            this.Auth.fetch("http://hulapp.pythonanywhere.com/api/users/"+this.props.match.params.userId)

		    .then((res) => {
			  this.setState({
				  name: res.first_name,
				  userId: res.id,
				  surname: res.last_name,
				  email: res.email,
				  countryId: res.country,
          fileUploaded : null,
          city:res.city,
          src : res.profile_img,
          endSprinner : false,
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
                console.log({message: "ERROR " + error});
            });
		  }
		  else{
			  this.setState({auth: false});
		  }
	  }



  render(){
    return(
      <div>
        <Navbarex/>
       <div className="offset-md-1 col-12 col-md-10">
       {(this.state.auth) ? '' : <Redirect to="/" />}
                <div className="edit-container">
                    <div className="name-search">
                      <h1>{this.state.name} {this.state.surname}</h1>
                    </div>
                      <hr/>
                      <div className="row">
                        <div className="col-4">
                          {this.state.loading ? <div className="spinner"><LoadingSpinner/></div> : <Avatar  
                            size='300' 
                            round="300px" 
                            name="H"  
                            src={this.state.src}  /> }
                        </div>
                      <div className='col-8'>
                        <form className="input-in-form" onSubmit={this.handleSubmit}>
                        <div class="usersform">
                            <Form.Group controlId="formSearch">
                              <Form.Label>Imię:</Form.Label>
                              <InputGroup.Prepend>
                                  <div className="inputgrouptext">
                                      <InputGroup.Text>
                                        <AccountCircleIcon/>
                                      </InputGroup.Text>
                                  </div>
                                <Form.Control name="name" type="text" value={this.state.name} readonly="readonly"/>
                              </InputGroup.Prepend>
                            </Form.Group>
                            <Form.Group controlId="formSearch">
                              <Form.Label>Nazwisko:</Form.Label>
                              <InputGroup.Prepend>
                                  <div className="inputgrouptext">
                                      <InputGroup.Text>
                                        <AccountCircleIcon/>
                                      </InputGroup.Text>
                                  </div>
                                <Form.Control name="surname" type="text" value={this.state.surname} readonly="readonly"/>
                              </InputGroup.Prepend>
                            </Form.Group>
                            <Form.Group controlId="formSearch">
                              <Form.Label>Miasto:</Form.Label>
                              <InputGroup.Prepend>
                                <div className="formSearch">
                                  <InputGroup.Text>
                                      <LocationCityIcon/>
                                  </InputGroup.Text>
                                </div>
                                <Form.Control name="city" type="text" value={this.state.cityName} readonly="readonly"/>
                              </InputGroup.Prepend>

                    </Form.Group>
                    <Form.Group controlId="formSearch">
                        <Form.Label>Kraj:</Form.Label>
                        <InputGroup.Prepend>
                        <div className="inputgrouptext">
                                <InputGroup.Text>
                                    <PublicIcon/>
                                </InputGroup.Text>
                        </div>
                        <Form.Control name="country" type="text" value={this.state.countryName} readonly="readonly"/>
                                            </InputGroup.Prepend>

                    </Form.Group>
                    </div>
                   
            
                </form>
                </div>
                </div>
      </div>
      </div>
      </div>
    );
  }
}

export default SearchUser;
