import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import moment from 'moment';
import UserHandler from '../../utils/userHandler';

import './Navbar.css'
import { jsxClosingElement } from '@babel/types';

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
    }

    render() {
        const user = UserHandler.get();
        const loginRegisterSection = (
            <React.Fragment>
                <li className="nav-item active">
                    <Link to={{
                        pathname: "/login",
                        state: { setLoggedInAs: this.props.setLoggedInAs }
                    }}>
                        <span className="nav-link" href="#">Login</span>
                    </Link>
                </li>

                {/* <li className="nav-item active">
                    <Link to="/registar">
                        <span className="nav-link" href="#">Registar</span>
                    </Link>
                </li> */}
            </React.Fragment>
        )

        const loggedInMenus = (
            <React.Fragment>

                <i className="material-icons individual-icon"
                    style={{ float: 'right', position: 'absolute', top: '22px', left: '-25px' }}>
                    person
                   </i>

                <li className="nav-item active">
                    <span>{user.username} </span>
                </li>

                <li className="nav-item active">
                    <span className="nav-link" onClick={() => this.logoutUser()}>Logout</span>
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
                                    <span className="nav-link">
                                        {/* <i className="material-icons individual-icon" >
                                            access_time
                                    </i> */}
                                        {moment().format('HH:mm DD/MM')}
                                    </span>
                                </div>
                            </li>

                            {!user && loginRegisterSection}

                            {user && loggedInMenus}
                        </ul>
                    </div>
                </div>
            </nav >);
    }
}

export default Navbar;