import React from 'react';

class AccountActivation extends React.Component{

    constructor(props) {
        super(props);
        this.state = {
                uid: this.props.match.params.uid,
                token: this.props.match.params.token,
                message: '',
                resp: ''
        };
      }

     componentDidMount(){
       // alert("tutaj 2");
         console.log("uid "+this.state.uid);
         console.log("token "+this.state.token)
         //walidacja
         fetch('http://hulapp.pythonanywhere.com/auth/users/activation/', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                uid: this.state.uid,
                token: this.state.token
               
            })
            })
            .then((response) => {
                if(response.status === 204 ){
                    console.log("SUCCESSS")
                    this.setState({message: "Dziekujemy, Twoje konto zostało aktywowane "});
                    //return response.json();     
                }
                else if(response.status >= 400){
                    console.log("FAILED: ")
                    this.setState({message: "Aktywacja nie możliwa: "});
                }
                else{
                    console.log("SOMETHING WENT WRONG")
                    this.setState({ message: "Something went wrong."});
                }
                return response.json();
            
            })
            .then((data) => {
            this.setState({ res: data.detail })
            console.log(data)
        })
            .catch((error) => {
                this.setState({message: "ERROR " + error});
            });
    };

    render(){
        return(
            <div>
                <div className="result">{ this.state.message }</div>
                <div>{ this.state.res }</div>
            </div>
        );
  }  
}
export default AccountActivation;