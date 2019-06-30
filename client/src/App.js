import React from 'react';
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import './App.css';
import './style/global.css'
import './style/calendar.css';

// Components
import Home from './components/Home/Home';
import Navbar from './components/Navbar/Navbar';
import Espaco from './components/Espaco/Espaco';
import ResumoEspaco from './components/ResumoEspaco/ResumoEspaco';
import Login from "./components/Login/Login";
import CriarEvento from './components/CriarEvento/CriarEvento';
import GestaoPedidos from './components/GestaoPedidos/GestaoPedidos';
import PrivateRoute from './components/PrivateRoute/PrivateRoute';
import PesquisaGestor from "./components/Users/Gestor/Pesquisa/PesquisaGestor";
import PesquisaUtilizador from "./components/Users/Utilizador/Pesquisa/PesquisaUtilizador";
import EspacosComunsGestor from "./components/Users/Gestor/EspacosComuns/EspacosComunsGestor";
import PedidosGestor from "./components/Users/Gestor/Pedidos/PedidosGestor";
import PedidosUtilizadorCPDR from "./components/Users/UtilizadorCPDR/Pedidos/PedidosUtilizadorCPDR";

function App() {

  const testFunction = () => {
    console.log("Testing from App !!")
  };

  const updateNavBar = () => {
    console.log("Should update NavBar");
    testFunction();
  };

  return (
    <Router>
      <div>
        <Navbar testFunction={testFunction} />
        <div className="container mt-4">
          <Switch>
            <PrivateRoute exact path="/" component={Home} />
            <PrivateRoute exact path="/espaco" component={Espaco} testFunction={testFunction} />
            {/*<PrivateRoute exact path="/pesquisar" component={Pesquisar} />*/}
            <Route exact path="/pesquisagestor" component={PesquisaGestor} />
            <Route exact path="/pesquisautilizador" component={PesquisaUtilizador} />
            <Route exact path="/espacoscomunsgestor" component={EspacosComunsGestor} />
            <Route exact path="/pedidosutilizadorcpdr" component={PedidosUtilizadorCPDR} />
            <Route exact path="/pedidosgestor" component={PedidosGestor} />
            <Route exact path="/espaco-comum" component={ResumoEspaco} />
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
