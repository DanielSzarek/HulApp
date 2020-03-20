import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { makeStyles, withStyles } from '@material-ui/core/styles';
import clsx from 'clsx';
import Stepper from '@material-ui/core/Stepper';
import Step from '@material-ui/core/Step';
import StepLabel from '@material-ui/core/StepLabel';
import Check from '@material-ui/icons/Check';
import StepConnector from '@material-ui/core/StepConnector';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import RegistrationFirstStep from './Stepfirst';
import RegistrationSecondStep from './RegSecondStep';
import { Redirect } from 'react-router-dom';
import logo from '../Images/logo.png';
import '../Styles/altReg.css';
import { Link } from 'react-router-dom';
import RegistrationThirdStep from './RegThirdStep';


const QontoConnector = withStyles({
  alternativeLabel: {
    top: 10,
    left: 'calc(-50% + 16px)',
    right: 'calc(50% + 16px)',
  },
  active: {
    '& $line': {
      borderColor: '#DA190B',
    },
  },
  completed: {
    '& $line': {
      borderColor: '#DA190B',
    },
  },
  line: {
    borderColor: '#eaeaf0',
    borderTopWidth: 3,
    borderRadius: 1,
  },
})(StepConnector);

const useQontoStepIconStyles = makeStyles({
  root: {
    color: '#eaeaf0',
    display: 'flex',
    height: 22,
    alignItems: 'center',
  },
  active: {
    color: '#DA190B',
  },
  circle: {
    width: 8,
    height: 8,
    borderRadius: '50%',
    backgroundColor: 'currentColor',
  },
  completed: {
    color: '#DA190B',
    zIndex: 1,
    fontSize: 18,
  },
});

function QontoStepIcon(props) {
  const classes = useQontoStepIconStyles();
  const { active, completed } = props;

  return (
    <div
      className={clsx(classes.root, {
        [classes.active]: active,
      })}
    >
      {completed ? <Check className={classes.completed} /> : <div className={classes.circle} />}
    </div>
  );
}

QontoStepIcon.propTypes = {
  active: PropTypes.bool,
  completed: PropTypes.bool,
};

const useStyles = makeStyles(theme => ({
  root: {
    width: '100%',
  },
  button: {
    marginRight: theme.spacing(1),
  },
  instructions: {
    marginTop: theme.spacing(1),
    marginBottom: theme.spacing(1),
  },
  center: {
    textAlign: 'center',
  }
}));

function getSteps() {
  return ['E-mail i hasło', 'Informacje', 'Dołącz do nas!'];
}

function getStepContent(step, value, handleChange, handleSelect, name, surname, cityName, countryName, email, message, error, registerSuccess) {
  switch (step) {
    case 0:
      return (

        <div>
          <RegistrationFirstStep
            onChange={handleChange}
            value={value}
          // passwordOk={handlePasswordCorrectness}
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
          />
        </div>
      )

    case 2:
      return (
        <div>
          {!registerSuccess ? (
            <div>
              <div className="error-message">{message}</div>
              <RegistrationThirdStep
                name={name}
                surname={surname}
                city={cityName}
                country={countryName}
                email={email} />
            </div>
          ) : (
              <Redirect to='/success' />
            )
          }
        </div>
      )

    default:
      return 'Unknown step';
  }
}

export default function CustomizedSteppers() {

  const classes = useStyles();
  const [activeStep, setActiveStep] = React.useState(0);
  const steps = getSteps();
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [name, setName] = useState("")
  const [surname, setSurname] = useState("")
  const [city, setCity] = useState("")
  const [countryId, setCountry] = useState("")
  const [countryName, setCountryName] = useState("")
  const [cityName, setCityName] = useState("")
  const [repeatedPassword, setRepeatedPassword] = useState("")

  const [message, setMessage] = useState("")
  const [visible, setVisible] = useState(false)
  const [success, setSuccess] = useState(false)
  const [error, setError] = useState(true)
  const [passwordOk, setPasswordOk] = useState(false)

  // const [redirect, setRedirect] = useState(false)

  const [registerSuccess, setRegisterSuccess] = useState(false)


  const values = { email, password, name, surname, city, countryId, cityName, countryName }

  //  const handlePasswordCorrectness = () = {
  //     setPasswordOk(true)
  //   }


  const handleInputChange = (e) => {
    switch (e.currentTarget.name) {
      case "email":
        setEmail(e.currentTarget.value)
        return
      case "password":
        setPassword(e.currentTarget.value)
        return
      case "name":
        setName(e.currentTarget.value)
        return
      case "surname":
        setSurname(e.currentTarget.value)
        return
      case "repeatedPassword":
        setRepeatedPassword(e.currentTarget.value)
        return
    }
  }

  const handleSelectChange = (e) => {
    console.log(e);
    switch (e.propname) {
      case "city":
        setCity(e.id);
        setCityName(e.value);
        return
      case "countryId":
        setCountry(e.id);
        setCountryName(e.value);
        return
    }
  }

  const handleNext = () => {
    setActiveStep(prevActiveStep => prevActiveStep + 1);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    fetch('http://hulapp.pythonanywhere.com/auth/users/', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
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
    })
      .then((response) => {
        if (response.status >= 200 && response.status < 300) {
          setMessage("");
          setRegisterSuccess(true)
        } else {
          var json = response.json().then((obj) => {
            var allPropertyNames = Object.keys(obj);
            var err = "";
            for (var j = 0; j < allPropertyNames.length; j++) {
              var name = allPropertyNames[j];
              var value = obj[name];
              switch (name) {
                case "email":
                  err += email + value + " ";
                default:
                  err += value + " ";
              }
              setError(false);
            }
            setMessage("Rejestracja nie możliwa: " + err);
          });
        }
      })
  }


  const handleBack = () => {
    setActiveStep(prevActiveStep => prevActiveStep - 1);
  };

  const handleReset = () => {
    setActiveStep(0);
  };

  return (
    <div className={classes.root}>
      {/* {window.location.reload()} */}
      <div className="offset-md-4 col-12 col-md-4">
        <div className="registration-container">
          <img src={logo} alt={"logo"} />
        </div>
      </div>
      <Stepper alternativeLabel activeStep={activeStep} connector={<QontoConnector />}>
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
              <div className={classes.instructions}>{getStepContent(activeStep, values, handleInputChange, handleSelectChange, name, surname, cityName, countryName, email, message, error, registerSuccess)}</div>
              <div className={classes.center}>
                <Button disabled={activeStep === 0} onClick={handleBack} className={classes.button} >
                  Wróć
              </Button>

                {/* if registerSuccess===true it should not be possible to click on "register" button again */}

                {activeStep === steps.length - 1 ? (
                  <Button
                    variant="contained"
                    color="primary"
                    onClick={handleSubmit}
                    className={classes.button}>
                    Zarejestruj
                  </Button>

                ) : (
                    <Button
                      variant="contained"
                      color="primary"
                      onClick={handleNext}
                      className={classes.button}>
                      dalej
                    </Button>
                  )}
                <div className="return-to-log">
                  Masz już konto?  <Link className="link-to-log" to="/login">Zaloguj się!</Link>
                </div>
              </div>
            </div>
          )}
      </div>
    </div>
  );
}
