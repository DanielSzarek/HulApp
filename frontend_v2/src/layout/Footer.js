import React from 'react'
import { MDBIcon } from 'mdbreact'

function Footer () {
  return (
    <div className='footer'>
      <div className='grid-row'>
        <div className='grid-row '>
          <span className='footer__text--small-screen-email u-center-text'>
            deviation.squad@gmail.com
          </span>
        </div>
        <div className=' footer__text-left grid-row--col-1-of-2'>
          <span className='footer__text-left--info'>znajdź nas</span>
          <div className='footer__text-left--icons'>
            <a href='https://www.instagram.com/hulajurbansquad/'>
              <MDBIcon
                fab
                icon='instagram'
                size='2x'
                className='icon icon--button button'
              />
            </a>
            <a href='https://www.instagram.com/hulajurbansquad/'>
              <MDBIcon
                fab
                icon='facebook'
                size='2x'
                className='icon icon--button button'
              />
            </a>
          </div>
        </div>
        <div className='footer__text-right grid-row--col-1-of-2'>
          <div>masz pomysł na ulepszenie strony?</div>
          <div> a może coś nie działa? Daj nam znać! </div>
          <span className='footer__text-right--mail'>
            deviation.squad@gmail.com
          </span>
        </div>
      </div>
      <div className='brand'>
        <div className='brand__text'>
          &copy;
          <a href='http://localhost:5000' className='brand__text--link'>
            Hulapp
          </a>
        </div>
      </div>
    </div>
  )
}
export default Footer
