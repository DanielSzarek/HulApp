import React from 'react'
import { MDBIcon } from 'mdbreact'

function StartInfo () {
  return (
    <div className='info-container'>
      <div className='u-center-text u-margin-bottom-medium'>
        <h2 className='heading-secondary'>poznajmy się</h2>
      </div>
      <div className='info-container__text u-margin-bottom-medium'>
        Czas poruszyć świat! Hulając przez miasta, badamy możliwości i promujemy
        Hybrydową Mobilność Miejską, a także podejmujemy kwestie innych aspektów
        i problemów urbanistyki i architektury, jak np. zrównoważony rozwój,
        przemiany rewitalizacyjne, architektura uniwersalna, kształtowanie
        tkanki w kontekście historii i współczesności. W poszukiwaniu
        najlepszych wzorców i doświadczeń podbijamy nie tylko Trójmiasto, ale
        także inne miasta Polski i Europy!
      </div>
      <div className='grid-row info-container__grid'>
        <div className='grid-row--col-1-of-3 info-container__grid--card'>
          <div className='info-container__grid--card--text u-center-text '>
            <h3 heading-tertiary>#obchodzi_nas</h3>
            <MDBIcon icon='dove' size='5x' className='icon' />
            <div className='info-container__grid--card--text--sub'>
              Promujemy hybrydową mobilność miejską i architekturę uniwersalną
            </div>
          </div>
        </div>
        <div className='grid-row--col-1-of-3 info-container__grid--card'>
          <div className='info-container__grid--card--text u-center-text '>
            <h3 heading-tertiary>#odkrywcy </h3>
            <MDBIcon far icon='map' size='5x' className='icon' />
            <div className='info-container__grid--card--text--sub'>
              W poszukiwaniu wzorców podbijamy 3miasto i Europę!
            </div>
          </div>
        </div>
        <div className='grid-row--col-1-of-3 info-container__grid--card'>
          <div className='info-container__grid--card--text u-center-text '>
            <h3 heading-tertiary>#studenci</h3>
            <MDBIcon icon='university' size='5x' className='icon' />
            <div className='info-container__grid--card--text--sub'>
              Jesteśmy studentami koła naukowego Wydziału Architektury
              Politechniki Gdańskiej
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
export default StartInfo
