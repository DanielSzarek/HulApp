import React from 'react';
import './App.css';
import FooterPage from './components/skeleton/footer'
import NavbarPage from './components/skeleton/header'
import CarousselPage from './components/home/gallery'
import { Form, InputGroup, Container, Row, Col } from 'react-bootstrap'
import husquad from './assets/husquad.jpg'
import Info from './components/home/info'


function App() {
  return (
    <div className="App">
      <header className="App-header">
      <NavbarPage/>
      </header>
      <body>
      <div>
      <Info/>
      {/* <img src={husquad}/>
      <div>Czas poruszyć świat! Hulając przez miasta, badamy możliwości i promujemy Hybrydową Mobilność Miejską, a także podejmujemy kwestie innych aspektów i problemów urbanistyki i architektury, jak np. zrównoważony rozwój, przemiany rewitalizacyjne, architektura uniwersalna, kształtowanie tkanki w kontekście historii i współczesności! W poszukiwaniu najlepszych wzorców i doświadczeń podbijamy nie tylko Trójmiasto, ale także inne miasta Polski i Europy!</div> */}
          <CarousselPage/>
          </div>
      </body>
      <footer>
      <FooterPage/>
      </footer>
    </div>
  );
}

export default App;
