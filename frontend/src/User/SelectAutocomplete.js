import React from 'react';
import { Form, Button } from 'react-bootstrap';
import AuthService from './AuthService';

class AutoComplete extends React.Component {
  constructor(props) {
        super(props);
        this.state = {
            value: '',
			options: [],
			selectedOption: '',
			inputValue: ''
        };
		this.Auth = new AuthService();
  
	this.onInputChange = this.onInputChange.bind(this);
	this.onChange = this.onChange.bind(this);
	//this.onSelect = this.onSelect.bind(this);
	/*this.getItemValue = this.getItemValue.bind(this);
	this.renderItem = this.renderItem.bind(this);*/
	this.retrieveDataAsynchronously = this.retrieveDataAsynchronously.bind(this);
  }
  
  componentWillReceiveProps(nextProps){
	  
	  if(typeof nextProps.value !== 'undefined' && nextProps.value ){
		  let url = `http://hulapp.pythonanywhere.com/api/${this.props.dest}/${nextProps.value}`;

		  this.Auth.fetch(url)
			.then((res) => {
					
					if(typeof(res.detail) !== 'undefined'){
						this.setState({
							options: []
						})
					}
					else{
						let list = [];
						if(!Array.isArray(res)){
							list.push(res);
						}
						else{
							list = res;
						}
						if(list.length > 0){
						this.setState({
							options: list,
							value: list[0].id+"-"+list[0].name,
						})
						}
					}
			});
	  }
  }
  
  retrieveDataAsynchronously(searchText){
        if(searchText.length > 0){
			// Url of your website that process the data and returns a
			let url = `http://hulapp.pythonanywhere.com/api/${this.props.dest}/${searchText}`;
			this.Auth.fetch(url)
			.then((res) => {
					if(typeof(res.detail) !== 'undefined'){
						this.setState({
							options: []
						})
					}
					else{
						this.setState({
							options: res
						})
					}
			});
		}
		else{
			this.setState({
				options: []
			});
		}
    }
  
  onInputChange(e){
		this.setState({inputValue: e.target.value});
        this.retrieveDataAsynchronously(e.target.value);
    }
	
	onChange(e) {
        let splitted = e.target.value.split("-");
        this.setState({ value: e.target.value, inputValue: splitted[1] })
        this.props.onSelect(splitted[0]);
  }
  
  render() {
	  let required = this.props.required ? "required" : "";
    return (
	
      <div className="autocomplete-dropdown">
			<Form.Label >{this.props.label}:</Form.Label>
			<Form.Control name={this.props.name+"_input"} type="text"  onChange={this.onInputChange} autoComplete='new-password' placeholder={this.props.placeholder} value={this.state.inputValue} />

			<Form.Control as="select" name={this.props.name} required onChange={this.onChange} value={this.state.value} >
				<option>Choose...</option>
                {
					this.state.options.map(item =>{
						return(
							<option key={item.id} value={item.id+"-"+item.name}>{item.name}</option>
						)
                    })
				}
           
		   </Form.Control>
			
		</div>
    );
  }
}

export default AutoComplete;