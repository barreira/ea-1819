import React from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css'

const Navbar = () => {
    return (
        <nav className="navbar navbar-expand-md bg-faded">
            <div className="container">
                <a className="navbar-brand" href="#">GestaoEspaços</a>
                <div className="collapse navbar-collapse " id="navbarText">
                    <ul className="navbar-nav ml-auto">
                        <li className="nav-item active">
                            <Link to="/">
                                <span className="nav-link" href="#">Menu 1</span>
                            </Link>
                        </li>
                        <li className="nav-item active">
                            <Link to="/">
                                <span className="nav-link" href="#">Menu 2</span>
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link to="/">
                                <span className="nav-link" href="#">Menu 3</span>
                            </Link>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    )
}

export default Navbar;