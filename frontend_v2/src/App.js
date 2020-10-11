import React from 'react'
import './sass/styles.css'
import Navbar from './layout/Navbar'
import Footer from './layout/Footer'
import StartInfo from './components/StartInfo'

function App () {
  return (
    <body>
      <Navbar />
      <header className='header'>
        <div className='header__text-box z-index-under'>
          <h1 className='heading-primary'>
            <span className='heading-primary--main'> hulaj urban squad</span>
            <span className='heading-primary--sub'>czas poruszyć świat!</span>
          </h1>
          <div className='heading-primary--laptop-button'>
            <a href='#' className='button button-white button-info'>
              hulaj z nami
            </a>
          </div>
          <div className='heading-primary__phone-buttons'>
            <a href='#' className='button-log button button-white button-info'>
              zaloguj
            </a>
            <a href='#' className=' button-reg button button-white button-info'>
              zarejestruj
            </a>
          </div>
        </div>
      </header>
      <main>
        <div className='section-info'>
          <StartInfo />
        </div>
      </main>
      <footer>
        <Footer />
      </footer>
    </body>
  )
}

export default App
