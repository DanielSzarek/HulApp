import React from 'react'
import { MDBCol, MDBContainer, MDBRow, MDBFooter, MDBIcon } from 'mdbreact'

const FooterPage = () => {
  return (
    <MDBFooter color='white' className='font-small pt-4 mt-4 text-dark'>
      <MDBContainer fluid className='text-center text-md-left text-dark'>
      <hr/>
        <MDBRow>
          <MDBCol md='4'>
            <ul>
              <li className='list-unstyled'>
                <a className='text-dark' href='#!'>
                  kim jestesmy
                </a>
              </li>
              <li className='list-unstyled'>
                <a className='text-dark' href='#!'>
                  dołącz do nas
                </a>
              </li>
              <li className='list-unstyled'>
                <a className='text-dark' href='#!'>
                  Link 3
                </a>
              </li>
              <li className='list-unstyled'>
                <a href='#!'>Link 4</a>
              </li>
            </ul>
          </MDBCol>
          <MDBCol md='6'>
            <p>
              Coś nie działa? Może masz pomysł na ulepszenie strony? Daj nam
              znać! Napisz na adres: <div>deviationsquad@gmail.com</div>
            </p>
          </MDBCol>
        </MDBRow>
        <MDBRow>
          <MDBCol md='4'>
            <MDBIcon fab icon='instagram' size='2x' className='text-danger'/>
            <MDBIcon fab icon='facebook-square' size='2x' className='text-danger'/>
          </MDBCol>
        </MDBRow>
      </MDBContainer>
      <hr />
      <div className='footer-copyright text-left py-3 text-dark white'>
        <MDBContainer fluid>
          &copy; {new Date().getFullYear()} Copyright:{' '}
          <a href='https://www.mdbootstrap.com' className='text-dark'> MDBootstrap.com </a>
        </MDBContainer>
      </div>
    </MDBFooter>
  )
}

export default FooterPage
