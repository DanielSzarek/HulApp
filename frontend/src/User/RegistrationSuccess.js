import React from 'react'
import { Link } from 'react-router-dom'
import logo from '../Images/logo_hulapp.png'
import '../Styles/altReg.css'

class RegistrationSuccess extends React.Component {
  constructor (props) {
    super(props)
    this.state = {
      email: this.props.match.params.email,
      enableResendActivation: true
    }
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  handleSubmit = event => {
    this.setState({ enableResendActivation: false })
    event.preventDefault()
    fetch('http://hulapp.pythonanywhere.com/auth/users/resend_activation/', {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        email: this.state.email
      })
    })
  }

  render () {
    return (
      <div>
        <img src={logo} alt={'logo'} />
        <div className='registration-success-message'>
          <p>Pozostał Ci ostatni krok.</p>
          <p>
            {' '}
            Aby ukończyć rejestrację sprawdź skrzynkę pocztową i kliknij w link
            aktywacyjny.
          </p>
          <p> Czekamy na Ciebie!</p>
        </div>
        <form onSubmit={this.handleSubmit} className='form-reactivate'>
          {this.state.enableResendActivation ? (
            <span className='span-reactivate'>
              {' '}
              Email aktywacyjny nie przyszedł? Kliknij, spróbujmy jeszcze raz!{' '}
            </span>
          ) : (
            <span className='span-reactivate'>
              {' '}
              Sprawdź skrzynkę pocztową. Dalej nic? Daj znać naszemu zespołowi
              pod adresem: deviation.squad@gmail.com{' '}
            </span>
          )}
          <button
            type='submit'
            className='button-reactivate-disabled'
            disabled={!this.state.enableResendActivation}
          >
            Wyślij ponownie
          </button>
        </form>

        <div className='return-to-log return-to-log-incase'>
          Masz już konto?{' '}
          <Link className='link-to-log' to='/login'>
            Zaloguj się!
          </Link>
        </div>
      </div>
    )
  }
}

export default RegistrationSuccess
