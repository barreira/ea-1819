import React from 'react';
import { Link } from 'react-router-dom';
import moment from 'moment';

import './NavbarVisitante.css'

class NavbarVisitante extends React.Component {

    state = {
        time: Date.now()
    };

    componentDidMount() {
        this.interval = setInterval(() => this.setState({ time: Date.now() }), 5000);
    }

    componentWillUnmount() {
        clearInterval(this.interval);
    }

    render() {
        return (
            <nav className="navbar navbar-expand-md bg-faded">
                <div className="container">
                    <a className="navbar-brand" href="/">GestãoEspaços</a>
                    <div className="collapse navbar-collapse " id="navbarText">
                        <ul className="navbar-nav ml-auto">
                            <li className="nav-item">
                                <div id="current-time">
                                    <span className="nav-link">
                                        {/* <i className="material-icons individual-icon" >
                                            access_time
                                    </i> */}
                                        {moment().format('DD-MM, HH:mm')}
                                    </span>
                                </div>
                            </li>
                            <li className="nav-item active">
                                <Link to="/login">
                                    <span className="nav-link" ref="#">Login</span>
                                </Link>
                            </li>
                            <li className="nav-item active">
                                <Link to="/registar">
                                    <span className="nav-link" ref="#">Registar</span>
                                </Link>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>);
    }
}

export default NavbarVisitante;