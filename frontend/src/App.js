import React from 'react';
//import {button} from 'react-bootstrap';
import './App.css';
import LoggingForm from './LoggingForm';
import Registration from './Registration';
import {  Link, BrowserRouter, Switch, Route } from 'react-router-dom';

class App extends React.Component {
  render(){
    return(
      <div>
        <BrowserRouter>
          <div className="logrej">
           <Link to="/logging">
              <button onClick={this.logSomething} type="button" className="btn btn-primary active btn-lg" >
                Zaloguj
             </button>
           </Link>
           <Link to="/registration">
              <button  type="button" className="btn btn-primary active btn-lg" style={{marginLeft: "26px"}} >
                Zarejestruj
              </button>
            </Link>
             
          </div>
          <Switch>
            <Route path="/logging" component={LoggingForm}/>
            <Route path="/registration" component={Registration}/>
          </Switch>
        </BrowserRouter>


      </div>
    );
  }
}

export default App;
