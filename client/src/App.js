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
import NovoPedido from './components/NovoPedido/NovoPedido';
import GestaoPedidos from './components/GestaoPedidos/GestaoPedidos';
import PrivateRoute from './components/PrivateRoute/PrivateRoute';
import PesquisaUtilizadorCPDR from "./components/Users/UtilizadorCPDR/Pesquisa/PesquisaUtilizadorCPDR";
import PesquisaUtilizador from "./components/Users/Utilizador/Pesquisa/PesquisaUtilizador";
import EspacosComunsGestor from "./components/Users/Gestor/EspacosComuns/EspacosComunsGestor";
import PedidosGestor from "./components/Users/Gestor/Pedidos/PedidosGestor";
import PedidosUtilizadorCPDR from "./components/Users/UtilizadorCPDR/Pedidos/PedidosUtilizadorCPDR";
import EditarEspacosComunsGestor from "./components/Users/Gestor/EspacosComuns/Editar/EditarEspacosComunsGestor";
// import EditarEventoGestor from "./components/Users/UtilizadorCPDR/Pesquisa/EditarEvento/EditarEventoGestor";
import ASeguirUtilizador from "./components/Users/Utilizador/ASeguir/ASeguirUtilizador";
import PesquisaGestor from "./components/Users/Gestor/Pesquisa/PesquisaGestor";
import ASeguirUtilizadorCPDR from "./components/Users/UtilizadorCPDR/ASeguir/ASeguirUtilizadorCPDR";

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

            <Route exact path="/pesquisautilizador" component={PesquisaUtilizador} />
            <Route exact path="/aseguirutilizador" component={ASeguirUtilizador} />

            <Route exact path="/pesquisautilizadorcpdr" component={PesquisaUtilizadorCPDR} />
            {/*<Route exact path="/pesquisautilizadorcpdr/editar" component={EditarEventoUtilizadorCPDR} />*/}
            <Route exact path="/pedidosutilizadorcpdr" component={PedidosUtilizadorCPDR} />
            <Route exact path="/aseguirutilizadorcpdr" component={ASeguirUtilizadorCPDR} />

            <Route exact path="/pesquisagestor" component={PesquisaGestor} /> {/* editar evento */}
            {/*<Route exact path="/pesquisagestor/editar" component={EditarEventoGestor} />*/}
            <Route exact path="/pedidosgestor" component={PedidosGestor} />
            <Route exact path="/espacoscomunsgestor" component={EspacosComunsGestor} />
            <Route exact path="/espacoscomunsgestor/editar" component={EditarEspacosComunsGestor} />

            <Route exact path="/espaco-comum" component={ResumoEspaco} />
            <Route exact path="/pedido/novo" component={NovoPedido} />
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
