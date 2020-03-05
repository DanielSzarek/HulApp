import React from 'react';
import Navbarex from './Navbar';
import LoadingSpinner from 'react-loader-spinner'
import { Form, Button, InputGroup } from 'react-bootstrap';
import AuthService from './AuthService';
import Avatar from 'react-avatar';
import '../Styles/Navbar.css';
import SearchIcon from '@material-ui/icons/Search';
// import InputAdornment from '@material-ui/core/InputAdornment';







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
                    <Form.Group controlId="formEditName">
                        <Form.Label>Imię:</Form.Label>
                        <InputGroup.Prepend>
                        <div className="inputgrouptext">
                                <InputGroup.Text>
                                    <SearchIcon/>
                                </InputGroup.Text>
                        </div>
                           
                        <Form.Control name="name" type="text" value={this.state.name} readonly="readonly"
          //               startAdornment={
          //                 <InputAdornment position="start">
          //                   <SearchIcon />
          //                   </InputAdornment>
          // }
          />
           </InputGroup.Prepend>
                    </Form.Group>
                    <Form.Group controlId="formEditSurname">
                        <Form.Label>Nazwisko:</Form.Label>
                        <Form.Control name="surname" type="text" value={this.state.surname} readonly="readonly"/>
                    </Form.Group>
                    <Form.Group controlId="formEditSurname">
                        <Form.Label>Miasto:</Form.Label>
                        <Form.Control name="city" type="text" value={this.state.cityName} readonly="readonly"/>
                    </Form.Group>
                    <Form.Group controlId="formEditSurname">
                        <Form.Label>Kraj:</Form.Label>
                        <Form.Control name="country" type="text" value={this.state.countryName} readonly="readonly"/>
                    </Form.Group>
                   
            
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
