import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import moment from 'moment';
import UserHandler from '../../utils/userHandler';

import './Navbar.css'

class Navbar extends Component {
    constructor(props) {
        super(props);
        this.state = { time: Date.now() }
    }

    componentDidMount() {
        this.interval = setInterval(() => this.setState({ time: Date.now() }), 10000);
    }

    componentWillUnmount() {
        clearInterval(this.interval);
    }

    logoutUser = () => {
        UserHandler.remove();
        this.setState({ ...this.state });
        window.location.href = "/"
    };

    render() {
        const user = UserHandler.get();

        const loginRegisterSection = (
            <React.Fragment>
                {/* <li className="nav-item">
                    <Link to={"/pesquisautilizador"}>
                        <span className="nav-link">Pesquisar</span>
                    </Link>
                </li> */}
                <li className="nav-item active">
                    <Link to={{
                        pathname: "/login",
                        state: { setLoggedInAs: this.props.setLoggedInAs }
                    }}>
                        <span className="nav-link">Login</span>
                    </Link>
                </li>
            </React.Fragment>
        );

        const loggedIn = (
            <React.Fragment>

                <li className="nav-item active">
                    <span>{user.username} </span>
                </li>

                <li className="nav-item active">
                    <span className="nav-link" onClick={() => this.logoutUser()}>Logout</span>
                </li>

            </React.Fragment>
        );

        const loggedInUtilizador = (
            <React.Fragment>
                <li className="nav-item active">
                    <Link to={"/aseguirutilizador"}>
                        <span className="nav-link">A Seguir</span>
                    </Link>
                </li>
                <li className="nav-item active">
                    <Link to={"/pesquisautilizador"}>
                        <span className="nav-link">Pesquisar</span>
                    </Link>
                </li>
            </React.Fragment>
        );

        const loggedInUtilizadorCPDR = (
            <React.Fragment>
                <li className="nav-item active">
                    <Link to={"/pedidosutilizadorcpdr"}>
                        <span className="nav-link">Pedidos</span>
                    </Link>
                </li>
                {/* <li className="nav-item active">
                    <Link to={"/aseguirutilizadorcpdr"}>
                        <span className="nav-link">A Seguir</span>
                    </Link>
                </li> */}
                <li className="nav-item active">
                    <Link to={"/pesquisautilizadorcpdr"}>
                        <span className="nav-link">Pesquisar</span>
                    </Link>
                </li>
            </React.Fragment>
        );

        const loggedInGestor = (
            <React.Fragment>
                <li className="nav-item active">
                    <Link to={"/pesquisagestor"}>
                        <span className="nav-link">Pesquisar</span>
                    </Link>
                </li>
                <li className="nav-item active">
                    <Link to={"/pedidosgestor"}>
                        <span className="nav-link">Pedidos</span>
                    </Link>
                </li>
                <li className="nav-item active">
                    <Link to={"/espacoscomunsgestor"}>
                        <span className="nav-link">Espaços Comuns</span>
                    </Link>
                </li>
            </React.Fragment>
        );

        return (
            <nav className="navbar navbar-expand-md bg-faded">
                <div className="container">
                    <a className="navbar-brand" href="/">GestaoEspaços</a>
                    <div className="collapse navbar-collapse " id="navbarText">
                        <ul className="navbar-nav ml-auto">

                            <li className="nav-item" style={{ position: 'relative' }}>
                                <i className="material-icons individual-icon"
                                    style={{ float: 'left', position: 'absolute', top: '22px', left: '-25px' }}>
                                    access_time
                                    </i>
                                <div id="current-time">
                                    <span className="nav-link" className="no-hover">
                                        {/* <i className="material-icons individual-icon" >
                                            access_time
                                    </i> */}
                                        {moment().format('DD MMM HH:mm')}
                                    </span>
                                </div>
                            </li>

                            {!user && loginRegisterSection}

                            {user.role === 'Utilizador' && loggedInUtilizador}
                            {user.role === 'UtilizadorCPDR' && loggedInUtilizadorCPDR}
                            {user.role === 'GestorEspacos' && loggedInGestor}

                            {user && loggedIn}
                        </ul>
                    </div>
                </div>
            </nav >);
    }
}

export default Navbar;