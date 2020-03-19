import React from 'react';
import blankprofpic from '../Images/blankprofpic.png';
import '../Styles/altReg.css';

class RegistrationThirdStep extends React.Component{
  
    constructor(props) {
        super(props);
      }

    render(){
        return(
            <div className="third-step-reg">             
                <img className="blank-prof-pic-summary" src={blankprofpic} alt={"blank profile picture"}/>
                <div className="summary-users-data">
                <p>IMIE : {this.props.name} </p>
                <p>NAZWISKO : {this.props.surname}</p>
                <p>MIASTO : {this.props.city}</p>
                <p>PAŃSTWO: {this.props.country}</p>
                <p>EMAIL: {this.props.email}</p> 
                </div>          
            </div>
        
        );
    }
}

export default RegistrationThirdStep;