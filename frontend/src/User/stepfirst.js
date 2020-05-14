import React from 'react'
import '../Styles/App.css'
import '../Styles/Registration.css'
import '../Styles/Login.css'
import EmailOutlinedIcon from '@material-ui/icons/EmailOutlined'
import { Form, InputGroup } from 'react-bootstrap'
import VisibilityOutlinedIcon from '@material-ui/icons/VisibilityOutlined'
import VisibilityOffOutlinedIcon from '@material-ui/icons/VisibilityOffOutlined'
import Tooltip from '@material-ui/core/Tooltip'

class RegistrationFirstStep extends React.Component {
  constructor (props) {
    super(props)
    this.state = {
      email: '',
      password: '',
      repeatedPassword: '',
      message: '',
      match: false,
      visible: false,
      success: false,
      passwordHidden: true,
      counterRepeated: 1,
      passwordRepeatedHidden: true,
      counter: 1,
      errors: {
        repeatedPassword: 'should be filled',
        email: 'should be filled',
        password: 'should be filled'
      }
    }

    this.checkPassword = this.checkPassword.bind(this)
    this.handleChange = this.handleChange.bind(this)
    this.validate = this.validate.bind(this)
  }

  componentDidMount () {
    if (this.props.value.email.length > 0) {
      this.setState(prevState => ({
        errors: {
          ...prevState.errors,
          email: ''
        }
      }))
    }
    if (this.props.value.password.length > 0) {
		console.log("password resetting");
      this.setState(prevState => ({
        errors: {
          ...prevState.errors,
          password: ''
        }
      }))
    }
  }

  handleChange = event => {
    this.props.onChange(event)
    const { name, value } = event.target

    let errors = this.state.errors
    const validEmailRegex = RegExp(
      /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i
    )

    switch (name) {
      case 'email':
        errors.email = validEmailRegex.test(value) ? '' : 'Email is not valid!'
        break
      case 'password':
        errors.password =
          value.length < 8 ? 'Password must be 8 characters long!' : ''
        break
      case 'repeatedPassword':
        errors.repeatedPassword =
          value !== this.props.value.password ? 'does not match' : ''
        break
      default:
        break
    }

    this.setState({ errors, [name]: value }, () => {
      console.log(errors)
    })
    this.props.onValidated(this.validate())
  }

  validate = () => {
    return (
      this.state.errors.email.length === 0 &&
      this.state.errors.password.length === 0 &&
      this.state.errors.repeatedPassword.length === 0
    )
  }

  checkPassword = event => {
    if (
      event.target.value &&
      event.target.value !== this.props.value.password
    ) {
      event.target.style.background = 'red'
      event.target.style.border = '1px solid red'
      this.setState({ match: false })
      alert("The passwords don't match")
    } else {
      event.target.style.background = '#E2E2E2'
      event.target.style.border = '1px solid #ced4da'
      this.setState({ match: true })
    }
  }

  showPassword = () => {
    this.setState({ passwordHidden: !this.state.passwordHidden })
  }

  showPasswordRepeated = () => {
    this.setState({ passwordRepeatedHidden: !this.state.passwordRepeatedHidden }) 
  };

  render () {
    return (
      <div>
        <div className='alt-reg-form'>
          <form className='input-in-form' onSubmit={this.handleSubmit}>
            <Form.Group
              className='registration-form'
              controlId='formBasicEmail'
            >
              <InputGroup>
                <InputGroup.Prepend>
                  <InputGroup.Text id='inputGroupPrepend'>
                    <EmailOutlinedIcon />
                  </InputGroup.Text>
                </InputGroup.Prepend>
                <Form.Control
                  type='email'
                  placeholder='Email'
                  name='email'
                  value={this.props.value.email}
                  onChange={this.handleChange}
                  required
                />
              </InputGroup>
            </Form.Group>

            <Form.Group
              className='registration-form'
              controlId='formBasicPassword'
            >
              <InputGroup>
                <InputGroup.Prepend>
                  <InputGroup.Text onClick={this.showPassword}>
                    {this.state.passwordHidden ? (
                      <VisibilityOffOutlinedIcon />
                    ) : (
                      <VisibilityOutlinedIcon />
                    )}
                  </InputGroup.Text>
                </InputGroup.Prepend>
                <Tooltip
                  className='hover-tooltip-register'
                  title={
                    <span className='hover-tooltip-register'>
                      Hasło powinno zawierać conajmniej 8 znaków, w tym: znak
                      specjalny, cyfra, wielka litera
                    </span>
                  }
                  aria-label='add'
                  arrow
                >
                  <Form.Control
                    type={this.state.passwordHidden ? 'password' : 'text'}
                    placeholder='Hasło'
                    name='password'
                    onChange={this.handleChange}
                    value={this.props.value.password}
                    required
                  />
                </Tooltip>
              </InputGroup>
            </Form.Group>

            <Form.Group
              className='registration-form'
              controlId='formRepeatedPassword'
            >
              <InputGroup>
                <InputGroup.Prepend>
                  <InputGroup.Text onClick={this.showPasswordRepeated}>
                    {this.state.passwordRepeatedHidden ? (
                      <VisibilityOffOutlinedIcon />
                    ) : (
                      <VisibilityOutlinedIcon />
                    )}
                  </InputGroup.Text>
                </InputGroup.Prepend>
                <Form.Control
                  type={this.state.passwordRepeatedHidden ? 'password' : 'text'}
                  placeholder='Powtórz hasło'
                  name='repeatedPassword'
                  onBlur={this.checkPassword}
                  value={this.props.value.repeatedPassword}
                  onChange={this.handleChange}
                  required
                />
              </InputGroup>
            </Form.Group>
          </form>
        </div>
      </div>
    )
  }
}

export default RegistrationFirstStep
