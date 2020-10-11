import React from 'react'
import { BrowserRouter, Switch, Route } from 'react-router-dom'
import Start from './pages/Start'
import LoggingForm from './pages/LoggingForm'

class App extends React.Component {
  render () {
    return (
      <div>
        <BrowserRouter>
          <Switch>
            <Route exact path='/' component={Start} />
            <Route path='/home' component={Start} />
             <Route path='/signin' component={LoggingForm} />
          </Switch>
        </BrowserRouter>
      </div>
    )
  }
}

export default App
