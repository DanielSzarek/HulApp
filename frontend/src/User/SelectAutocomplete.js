import React from 'react';
import '../Styles/UserProfile.css';
import AutoComplete from 'react-autocomplete';
import {InputGroup} from 'react-bootstrap';
import LocationCityIcon from '@material-ui/icons/LocationCity';

export default class AutoCompleteForm extends React.Component {

    constructor(props, context) {
        super(props, context);
        this.state = {
            value: "",
            autocompleteData: [],
			selectedValue : ""
        };

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
    retrieveDataAsynchronously(searchText){
        let _this = this;
        let url = `http://hulapp.pythonanywhere.com/api/${this.props.dest}/${searchText}`;
        
        let xhr = new XMLHttpRequest();
        xhr.open('GET', url, true);
        xhr.responseType = 'json';
        xhr.onload = () => {
            let status = xhr.status;

            if (status == 200) {
                _this.setState({
                    autocompleteData: xhr.response
                });
                console.log(xhr.response);
            } else {
                console.error("Cannot load data from remote source");
            }
        };

        xhr.send();
    }
    
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
        this.props.onSelect({
            propname: this.props.name, 
            id: event.split("-")[0], 
            value: event.split("-")[1] });
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
            <div style={{ background: isHighlighted ? 'lightgray' : 'white' }} key={item.id}>
                {item.name}
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
         return `${item.id} - ${item.name}`;
   }

    render() {
        return (
            <div className='form-group'>
                        <InputGroup className='autocomplete-group'>
                        <InputGroup.Prepend className="icon-prepend">
                        <InputGroup.Text> <LocationCityIcon/></InputGroup.Text>
                        </InputGroup.Prepend>
				
				<AutoComplete
					inputProps={{ style: { width:'100%',  border: '1px solid #ced4da', padding:'7px', paddingLeft:'59px'}, placeholder: this.props.defVal }}
                    menuStyle={{borderRadius: '3px',  boxShadow: '0 2px 12px rgba(0, 0, 0, 0.1)',  background: 'rgba(255, 255, 255, 0.9)',  padding: '2px 0',
					fontSize: '90%',  position: 'fixed',  overflow: 'auto',  maxHeight: '50%', zIndex:'2'}}
                    getItemValue={this.getItemValue}
                    items={this.state.autocompleteData}
                    renderItem={this.renderItem}
                    value={this.state.value}
                    onChange={this.onChange}
                    onSelect={this.onSelect}
					name={this.props.name}
                    wrapperStyle={{ width:  '100%'   }}
                />        
                </InputGroup>    
            </div>
        );
    }
}
