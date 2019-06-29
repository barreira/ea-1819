import React, { Component } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import { Redirect, Link } from 'react-router-dom';
import ReactNotification from "react-notifications-component";
import "react-notifications-component/dist/theme.css";
import UserHandler from '../../utils/userHandler';

import './Login.css';
import Api from '../../api/api';
import Register from './Register/Register';


class Login extends Component {
    constructor(props) {
        super(props);
        this.state = { logged: false, token: "", showRegisterSlip: false }
        this.addNotification = this.addNotification.bind(this);
        this.notificationDOMRef = React.createRef();
    }

    addNotification(notification) {
        this.notificationDOMRef.current.addNotification({
            title: notification.title || "Awesomeness",
            message: notification.message || "Awesome Notifications!",
            type: notification.type || "success",
            insert: "top",
            container: "top-right",
            animationIn: ["animated", "fadeIn"],
            animationOut: ["animated", "fadeOut"],
            dismiss: { duration: 5000 },
            dismissable: { click: true }
        });
    }


    render() {
        const { showRegisterSlip } = this.state;

        const registerSlipSection = (
            <div className="login-container shadow">
                <Register />
            </div>);

        const loginPresentationSection = (
            <div className="login-container shadow">
                <h3>Bem Vindo!</h3>
                <p>
                    Para aceder a funcionalidade exclusiva, realize o log in.
                </p>
                <div className="button-container">
                    <button className="btn button-login" onClick={() => this.setState({ showRegisterSlip: false })}>Log In</button>
                </div>
            </div>
        );

        const registerPresentationSection = (
            <div className="login-container shadow">
                <h3>Bem Vindo!</h3>
                <p>
                    Crie uma conta caso ainda não se encontre registado.
                </p>
                <div className="button-container">
                    <button className="btn button-login" onClick={() => this.setState({ showRegisterSlip: true })}>Registar</button>
                </div>
            </div>
        );

        const loginSlipSection = (
            <div className="login-container shadow">
                <h3>Log In!</h3>
                <Formik
                    initialValues={{ username: '', password: '' }}
                    validate={values => {
                        let errors = {};
                        if (!values.username) {
                            errors.username = 'Required';
                        } if (!values.password) {
                            errors.password = 'Required';
                        }
                        return errors;
                    }}
                    onSubmit={async (values, { setSubmitting }) => {
                        const req = await Api.login(values.username, values.password);

                        if (req.success) {
                            this.setState({ logged: true, registered: req });
                            UserHandler.save(req.token.token);
                            console.log(UserHandler.get());
                            window.location.href = '/';

                        } else {
                            this.setState({ registered: req });
                            this.addNotification({ title: 'Erro no login', message: 'Credenciais inválidas', type: 'danger' })
                        }


                        setSubmitting(false);
                    }}
                >
                    {({ isSubmitting }) => (
                        <Form className="form">
                            <div>

                                <Field className="input-login username" type="username" name="username" placeholder="Username" />
                                <ErrorMessage name="username" component="div" className="ErrorMessa" />
                            </div>

                            <div>

                                <Field className="input-login password" type="password" name="password" placeholder="Password" />
                                <ErrorMessage name="password" component="div" className="ErrorMessa" />
                            </div>

                            <div className="button-container">
                                <button className="btn button-login" type="submit" disabled={isSubmitting}>
                                    Log In
                            </button>
                            </div>
                        </Form>
                    )}
                </Formik>
            </div>
        );

        return (
            <div className="container">
                <ReactNotification ref={this.notificationDOMRef} />
                <div className="row">
                    <div className="col-md-2 col-xs-0"></div>
                    <div className={showRegisterSlip ? "col-md-3 col-xs-12" : "col-md-5 col-xs-12"}>
                        {!showRegisterSlip ? loginSlipSection : ''}
                        {showRegisterSlip ? loginPresentationSection : ''}
                    </div>
                    <div className={showRegisterSlip ? "col-md-7 col-xs-12" : "col-md-3 col-xs-12"}>
                        {showRegisterSlip ? registerSlipSection : ''}
                        {!showRegisterSlip ? registerPresentationSection : ''}
                    </div>
                </div>
            </div >
        );

    }
}

export default Login;
