import React from 'react';
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import './App.css';
import './style/calendar.css'
import './style/global.css'

// Components
import Home from './components/Home/Home';
import Navbar from './components/Navbar/Navbar';
import Espaco from './components/Espaco/Espaco';
import EspacoComum from './components/EspacoComum/EspacoComum';
import Login from "./components/Login/Login";
import CriarEvento from './components/CriarEvento/CriarEvento';
import GestaoPedidos from './components/GestaoPedidos/GestaoPedidos';



function App() {
  return (
    <Router>
      <div>
        <Navbar />
        <div className="container mt-4">
          <Switch>
            <Route exact path="/" component={Espaco} />
            <Route exact path="/espaco-comum" component={EspacoComum} />
            <Route exact path="/evento/criar" component={CriarEvento} />
            <Route exact path="/gestaopedidos" component={GestaoPedidos} />
            <Route exact path={"/login"} component={Login} />
            <Route component={Home} />
          </Switch>
        </div>
      </div>

    </Router>
  );
}

export default App;
