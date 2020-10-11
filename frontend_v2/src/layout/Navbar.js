import React from 'react'

class Navbar extends React.Component {
  render () {
    return (
      <div>
        <div className='navbar-header scrolled z-index-above'>
          <div className='navbar-header__logo'>
            <h1 className='navbar-header__logo--text'>hulapp</h1>
          </div>
          <div className='navbar-header__link'>
            <a href='#!' className='navbar-header__link--register'>zarejestruj</a>
            <a href='/signin' className='navbar-header__link--login above'>zaloguj</a>
          </div>
        </div>
      </div>
    )
  }
}

export default Navbar
