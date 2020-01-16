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
	  console.log("next props");
	  console.log(nextProps);
	  
	  if(typeof nextProps.value !== 'undefined' && nextProps.value ){
		  let url = `http://hulapp.pythonanywhere.com/api/${this.props.dest}/${nextProps.value}`;
			console.log(url);

		  this.Auth.fetch(url)
			.then((res) => {
					
					if(typeof(res.detail) !== 'undefined'){
						console.log("empty sugg");
						this.setState({
							options: []
						})
					}
					else{
						console.log("set sugg on componentWillReceiveProps");
						console.log(res);
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
			console.log("retrieveDataAsynchronously");
			// Url of your website that process the data and returns a
			let url = `http://hulapp.pythonanywhere.com/api/${this.props.dest}/${searchText}`;
			console.log(url);
			this.Auth.fetch(url)
			.then((res) => {
				console.log(typeof(res.detail) !== 'undefined');
					
					if(typeof(res.detail) !== 'undefined'){
						console.log("empty sugg");
						this.setState({
							options: []
						})
					}
					else{
						console.log("set sugg state");
						console.log(res);
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

        console.log("The Input Text has changed to ", e.target.value);
    }
	
	onChange(e) {
		console.log("onChange");
        console.log(e.target);
        let splitted = e.target.value.split("-");
        console.log(splitted[0]);
        console.log(splitted[1]);
        this.setState({ value: e.target.value, inputValue: splitted[1] })
        this.props.onSelect(splitted[0]);
  }
  
  render() {
	  let required = this.props.required ? "required" : "";
		console.log("Render");
		console.log(this.state.options);
		console.log(this.props.value);
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