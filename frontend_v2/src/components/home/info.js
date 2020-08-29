import React from 'react'
import '../../styles/home/info.css'
import husquad from '../../assets/husquad.jpg'

export default function Info () {
  return (
    <div id='container'>
      <div id='item-container'>
        <div id='item'>
          <div id='info-title'>Czas poruszyć świat!</div>
          <div id='info-capacity'>
            Hulając przez miasta, badamy możliwości i promujemy Hybrydową
            Mobilność Miejską, a także podejmujemy kwestie innych aspektów i
            problemów urbanistyki i architektury. W poszukiwaniu najlepszych
            wzorców i doświadczeń podbijamy nie tylko Trójmiasto, ale także inne
            miasta Polski i Europy!
          </div>
        </div>
      </div>
      <div id='item-logo'>
        <img src={husquad} />
      </div>
    </div>
  )
}
