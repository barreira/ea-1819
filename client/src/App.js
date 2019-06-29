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
import PrivateRoute from './components/PrivateRoute/PrivateRoute';

function App() {

  const testFunction = () => {
    console.log("Testing from App !!")
  };

  const updateNavBar = () => {
    console.log("Should update NavBar")
    testFunction();
  }

  return (
    <Router>
      <div>
        <Navbar testFunction={testFunction} />
        <div className="container mt-4">
          <Switch>
            <PrivateRoute exact path="/" component={Home} />
            <PrivateRoute exact path="/espaco" component={Espaco} testFunction={testFunction} />
            <Route exact path="/espaco-comum" component={EspacoComum} />
            <Route exact path="/evento/criar" component={CriarEvento} />
            <Route exact path="/gestaopedidos" component={GestaoPedidos} />
            <Route exact path="/login" render={(props) => <Login {...props} updateNavBar={updateNavBar} />} />
            <Route component={Home} />
          </Switch>
        </div>
      </div>

    </Router >
  );
}

export default App;
