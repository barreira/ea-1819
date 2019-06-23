import React, { Component } from 'react';

import './Login.css';

class Login extends Component {

    state = {
        email: '',
        password: ''
    };

    emailChangeHandler = (event) => {
        this.setState({email: event.target.value});
    };

    passwordChangeHandler = (event) => {
        this.setState({password: event.target.value});
    };

    loginHandler = () => {
        // Substituir por ação correta
        console.log('Email: ' + this.state.email);
        console.log('Password: ' + this.state.password);
    };

    render() {
        return (
            <form className="container">
                <div className="row justify-content-center">
                    <div className="col-xs-12 col-sm-8">
                        <div className="form-group">
                            <label htmlFor="exampleInputEmail1">Endereço de e-mail</label>
                            <input type="email" className="form-control" placeholder="Inserir e-mail"
                                   value={this.state.email} onChange={this.emailChangeHandler} />
                        </div>
                        <div className="form-group">
                            <label htmlFor="exampleInputPassword1">Palavra-passe</label>
                            <input type="password" className="form-control" placeholder="Inserir palavra-passe"
                                   value={this.state.password} onChange={this.passwordChangeHandler} />
                            <small id="passwordHelpBlock" className="form-text text-muted">
                                Your password must be at least 6 characters long and only contain letters and numbers.
                            </small>
                        </div>
                        <button type="submit" className="loginButton btn btn-danger" onClick={this.loginHandler}>
                            Submit
                        </button>
                    </div>
                </div>
            </form>
        );
    }
}

export default Login;