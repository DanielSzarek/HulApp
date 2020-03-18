import React from 'react';
import AutoComplete from 'react-autocomplete';
// import SearchIcon from '@material-ui/icons/Search';
// import InputAdornment from "@material-ui/core/InputAdornment";
import AuthService from './AuthService';
import '../Styles/UserProfile.css';
import Avatar from 'react-avatar';
import '../Styles/Navbar.css';
import '../Styles/InputProps.css';



export default class PersonAutoselect extends React.Component {

    constructor(props, context) {
        super(props, context);
        this.state = {
            value: "",
            autocompleteData: [],
			selectedValue : "",
            auth: true,

        };
        this.Auth = new AuthService();
        this.onChange = this.onChange.bind(this);
        this.onSelect = this.onSelect.bind(this);
        this.getItemValue = this.getItemValue.bind(this);
        this.renderItem = this.renderItem.bind(this);
        this.retrieveDataAsynchronously = this.retrieveDataAsynchronously.bind(this);
    }


    /**
     * Updates the state of the autocomplete data with the remote data obtained via AJAX.
     * 
     * @param {String} searchText content of the input that will filter the autocomplete data.
     * @return {Nothing} The state is updated but no value is returned
     */

     async retrieveDataAsynchronously(searchText){
		  if ( await this.Auth.loggedIn()){
            this.Auth.fetch(`http://hulapp.pythonanywhere.com/api/users/?search=${searchText}`)
		     .then((res) => {
 					if(typeof(res.detail) !== 'undefined'){
						console.log("not undef");
 						this.setState({
							autocompleteData: []
 						})
 					}
 					else{
						
						console.log(res);
						console.log("====");
 						this.setState({
 							autocompleteData: res
 						})
 					}
	  })}};
    
    /**
     * Callback triggered when the user types in the autocomplete field
     * 
     * @param {Event} e JavaScript Event
     * @return {Event} Event of JavaScript can be used as usual.
     */



    onChange(e){
        this.setState({
           value: e.target.value
        });

        this.retrieveDataAsynchronously(e.target.value);

    }

    /**
     * Callback triggered when the autocomplete input changes.
     * 
     * @param {Object} event Value returned by the getItemValue function.
     * @return {Nothing} No value is returned
     */
    onSelect(event){
        this.setState({
            value: event.split("-")[1]
        });
		this.props.onSelect(event.split("-")[0]);
    }

	

    /**
     * Define the markup of every rendered item of the autocomplete.
     * 
     * @param {Object} item Single object from the data that can be shown inside the autocomplete
     * @param {Boolean} isHighlighted declares wheter the item has been highlighted or not.
     * @return {Markup} Component
     */
    renderItem(item, isHighlighted){
        return (
            <div style={{ background: isHighlighted ? 'lightgray' : 'white' }} key={item.id} className="searchAvatar">
               <Avatar  size='50' round="300px" name="H"  src={item.profile_img}  /> {item.first_name} {item.last_name}
            </div>   
        ); 
    }

    /**
     * Define which property of the autocomplete source will be show to the user.
     * 
     * @param {Object} item Single object from the data that can be shown inside the autocomplete
     * @return {String} val
     */
    getItemValue(item){
         return `${item.id} - ${item.first_name} ${item.last_name}`;
                //  return `${item.id}`;

   }

    render() {
        return (
            <div className='form-group  person-autoselect'>

				
				<AutoComplete
					inputProps={{ style: { width:'350px' ,border: '1px solid #ced4da', padding:'.390rem' },
                     placeholder:'Szukaj...'
                     }}
                    getItemValue={this.getItemValue}
                    items={this.state.autocompleteData}
                    renderItem={this.renderItem}
                    value={this.state.value}
                    onChange={this.onChange}
                    onSelect={this.onSelect}
					name={this.props.name}
                />            
            </div>
        );
    }
}

