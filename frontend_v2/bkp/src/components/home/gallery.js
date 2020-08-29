import React from 'react'
import {
  MDBCarousel,
  MDBCarouselInner,
  MDBCarouselItem,
  MDBView,
  MDBContainer
} from 'mdbreact'
import '../../styles/home/gallery.css'
import firstGroup from '../../assets/firstGroup.png'
import secondGroup from '../../assets/secondGroup.jpg'
import thirdGroup from '../../assets/groupThird.jpg'

const CarouselPage = () => {
  return (
    <MDBContainer id='gallery-container'>
      <MDBCarousel
        id='gallery'
        activeItem={1}
        length={3}
        showControls={true}
        showIndicators={true}
        className='z-depth-1'
      >
        <MDBCarouselInner>
          <MDBCarouselItem itemId='1'>
            <MDBView>
              <img
                className='d-block w-100'
                src={firstGroup}
                alt='First slide'
              />
            </MDBView>
          </MDBCarouselItem>
          <MDBCarouselItem itemId='2'>
            <MDBView>
              <img
                className='d-block w-100'
                src={secondGroup}
                alt='Second slide'
              />
            </MDBView>
          </MDBCarouselItem>
          <MDBCarouselItem itemId='3'>
            <MDBView>
              <img
                className='d-block w-100'
                src={thirdGroup}
                alt='Third slide'
              />
            </MDBView>
          </MDBCarouselItem>
        </MDBCarouselInner>
      </MDBCarousel>
    </MDBContainer>
  )
}

export default CarouselPage
