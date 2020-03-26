import React, { useState } from 'react'
import PropTypes from 'prop-types'
import { makeStyles, withStyles } from '@material-ui/core/styles'
import clsx from 'clsx'
import Stepper from '@material-ui/core/Stepper'
import Step from '@material-ui/core/Step'
import StepLabel from '@material-ui/core/StepLabel'
import Check from '@material-ui/icons/Check'
import StepConnector from '@material-ui/core/StepConnector'
import Button from '@material-ui/core/Button'
import Typography from '@material-ui/core/Typography'
import RegistrationFirstStep from './stepfirst'
import RegistrationSecondStep from './RegSecondStep'
import { Redirect } from 'react-router-dom'
import logo from '../Images/logo.png'
import '../Styles/altReg.css'
import { Link } from 'react-router-dom'
import RegistrationThirdStep from './RegThirdStep'

const QontoConnector = withStyles({
  alternativeLabel: {
    top: 10,
    left: 'calc(-50% + 16px)',
    right: 'calc(50% + 16px)'
  },
  active: {
    '& $line': {
      borderColor: '#DA190B'
    }
  },
  completed: {
    '& $line': {
      borderColor: '#DA190B'
    }
  },
  line: {
    borderColor: '#eaeaf0',
    borderTopWidth: 3,
    borderRadius: 1
  }
})(StepConnector)

const useQontoStepIconStyles = makeStyles({
  root: {
    color: '#eaeaf0',
    display: 'flex',
    height: 22,
    alignItems: 'center'
  },
  active: {
    color: '#DA190B'
  },
  circle: {
    width: 8,
    height: 8,
    borderRadius: '50%',
    backgroundColor: 'currentColor'
  },
  completed: {
    color: '#DA190B',
    zIndex: 1,
    fontSize: 18
  }
})

function QontoStepIcon (props) {
  const classes = useQontoStepIconStyles()
  const { active, completed } = props

  return (
    <div
      className={clsx(classes.root, {
        [classes.active]: active
      })}
    >
      {completed ? (
        <Check className={classes.completed} />
      ) : (
        <div className={classes.circle} />
      )}
    </div>
  )
}

QontoStepIcon.propTypes = {
  active: PropTypes.bool,
  completed: PropTypes.bool
}

const useStyles = makeStyles(theme => ({
  root: {
    width: '100%'
  },
  button: {
    marginRight: theme.spacing(1)
  },
  instructions: {
    marginTop: theme.spacing(1),
    marginBottom: theme.spacing(1)
  },
  center: {
    textAlign: 'center'
  }
}))

function getSteps () {
  return ['E-mail i hasło', 'Informacje', 'Dołącz do nas!']
}

function getStepContent (
  step,
  value,
  handleChange,
  handleSelect,
  validate,
  name,
  surname,
  cityName,
  countryName,
  email,
  message,
  registerSuccess,
  // emptyCounter
) {
  switch (step) {
    case 0:
      return (
        <div>
          <RegistrationFirstStep
            onChange={handleChange}
            value={value}
            onValidated={validate}
          />
        </div>
      )
    case 1:
      return (
        <div>
          <RegistrationSecondStep
            onChange={handleChange}
            value={value}
            onSelect={handleSelect}
            onValidated={validate}
          />
        </div>
      )

    case 2:
      return (
        <div>
          {!registerSuccess ? (
            <div>
              <div className='error-message'>{message}</div>
              <RegistrationThirdStep
                name={name}
                surname={surname}
                city={cityName}
                country={countryName}
                email={email}
              />
            </div>
          ) : (
            <Redirect to={'/success/'+email}/>
          )}
        </div>
      )

    default:
      return 'Unknown step'
  }
}

export default function CustomizedSteppers () {
  const classes = useStyles()
  const [activeStep, setActiveStep] = React.useState(0)
  const steps = getSteps()
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [name, setName] = useState('')
  const [surname, setSurname] = useState('')
  const [city, setCity] = useState('')
  const [countryId, setCountry] = useState('')
  const [countryName, setCountryName] = useState('')
  const [cityName, setCityName] = useState('')
  const [message, setMessage] = useState('')
  const [registerSuccess, setRegisterSuccess] = useState(false)
  const [isEnabled, setEnabled] = useState(false)
  const [emptyCounter, setEmptyCounter] = useState(2)
  const values = {
    email,
    password,
    name,
    surname,
    city,
    countryId,
    cityName,
    countryName
  }

  const handleInputChange = e => {
    switch (e.currentTarget.name) {
      case 'email':
        setEmail(e.currentTarget.value)
        break

      case 'password':
        setPassword(e.currentTarget.value)
        break

      case 'name':
        setName(e.currentTarget.value)
        if(e.currentTarget.value.length>0){
          setEmptyCounter(emptyCounter-1)
        }
        break
      case 'surname':
        setSurname(e.currentTarget.value)
        if(e.currentTarget.value.length>0){
          setEmptyCounter(emptyCounter-1)
        }
        break
      default:
        break
    }
  }

  const handleSelectChange = e => {
    console.log(e)
    switch (e.propname) {
      case 'city':
        setCity(e.id)
        setCityName(e.value)
        if(e.propname.length<1){
          setEmptyCounter(emptyCounter+1)
        }
        break
      case 'countryId':
        setCountry(e.id)
        setCountryName(e.value)
         if(e.propname.length<1){
          setEmptyCounter(emptyCounter+1)
        }
        break
      default:
        break
    }
  }

  const handleNext = () => {
    setActiveStep(prevActiveStep => prevActiveStep + 1)
    // if(activeStep===1){
    //   // setEmptyCounter(4)
    //   setEnabled(false)
    // }
  }

  const validate = isValid => {
    console.log('isValid: ' + isValid)
    setEnabled(isValid)
    // setEmptyCounter(4)
  }

  const handleSubmit = event => {
    event.preventDefault()
    fetch('http://hulapp.pythonanywhere.com/auth/users/', {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        username: email,
        email: email,
        password: password,
        first_name: name,
        last_name: surname,
        country: countryId,
        city: city
      })
    }).then(response => {
      if (response.status >= 200 && response.status < 300) {
        setMessage('')
        setRegisterSuccess(true)
      } else {
        response.json().then(obj => {
          var allPropertyNames = Object.keys(obj)
          var err = ''
          for (var j = 0; j < allPropertyNames.length; j++) {
            var name = allPropertyNames[j]
            var value = obj[name]
            switch (name) {
              case 'email':
                err += email + value + ' '
                break
              default:
                err += value + ' '
            }
          }
          setMessage('Rejestracja nie możliwa: ' + err)
        })
      }
    })
  }

  const handleBack = () => {
    setActiveStep(prevActiveStep => prevActiveStep - 1)
  }

  const handleReset = () => {
    setActiveStep(0)
  }

  return (
    <div className={classes.root}>
      <div className='offset-md-4 col-12 col-md-4'>
        <div className='registration-container'>
          <img src={logo} alt={'logo'} />
        </div>
      </div>
      <Stepper
        alternativeLabel
        activeStep={activeStep}
        connector={<QontoConnector />}
      >
        {steps.map(label => (
          <Step key={label}>
            <StepLabel StepIconComponent={QontoStepIcon}>{label}</StepLabel>
          </Step>
        ))}
      </Stepper>

      <div>
        {activeStep === steps.length ? (
          <div>
            <Typography className={classes.instructions}>
              All steps completed - you&apos;re finished
            </Typography>
            <Button onClick={handleReset} className={classes.button}>
              Anuluj
            </Button>
          </div>
        ) : (
          <div>
            <div className={classes.instructions}>
              {getStepContent(
                activeStep,
                values,
                handleInputChange,
                handleSelectChange,
                validate,
                name,
                surname,
                cityName,
                countryName,
                email,
                message,
                registerSuccess,
                // emptyCounter
              )}
            </div>
            <div className={classes.center}>
              <Button
                disabled={activeStep === 0}
                onClick={handleBack}
                className={classes.button}
              >
                Wróć
              </Button>

              {activeStep === steps.length - 1 ? (
                <Button
                  variant='contained'
                  color='primary'
                  onClick={handleSubmit}
                  className={classes.button}
                >
                  Zarejestruj
                </Button>
              ) : (
                <Button
                  disabled={(!isEnabled) }
                  variant='contained'
                  color='primary'
                  onClick={handleNext}
                  className={classes.button}
                >
                  dalej
                </Button>
              )}
              <div className='return-to-log'>
                Masz już konto?{' '}
                <Link className='link-to-log' to='/login'>
                  Zaloguj się!
                </Link>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  )
}
