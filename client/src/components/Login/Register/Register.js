import React, { Component } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import ReactNotification from "react-notifications-component";
import "react-notifications-component/dist/theme.css";

import '../Login.css';
import Api from '../../../api/api';
import UserHandler from '../../../utils/userHandler';

class Register extends Component {
    constructor(props) {
        super(props);
        this.state = { userregistered: false, registered: '' }
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

        return (
            <div className="container">
                <ReactNotification ref={this.notificationDOMRef} />
                <h3>Criar conta!</h3>
                <Formik
                    initialValues={{ username: '', name: '', email: '', password: '', cpassword: '' }}
                    validate={values => {

                        let errors = {};
                        if (!values.username) {
                            errors.username = 'Required';
                        }
                        if (!values.name) {
                            errors.name = 'Required';
                        }
                        if (!values.email) {
                            errors.email = 'Required';
                        } else if (
                            !/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i.test(values.email)
                        ) {
                            errors.email = 'Invalid email address';
                        }
                        if (!values.password) {
                            errors.password = 'Required';
                        } if (!values.cpassword) {
                            errors.cpassword = 'Required';
                        } if (values.password && values.cpassword) {
                            if (values.password != values.cpassword) {
                                errors.cpassword = 'Passwords must be the same';
                            }
                        }
                        return errors;
                    }}
                    onSubmit={async (values, { setSubmitting }) => {
                        const req = await Api.register(values.username, values.email, values.name, values.password);

                        if (req.success && req.token !== 'Username already exists') {
                            this.setState({ registered: true });

                            const login = await Api.login(values.username, values.password);

                            if (login.success) {
                                if (login.success) {
                                    UserHandler.save(login.token.token);
                                    window.location.href = "/"
                                }
                            }

                        } else {
                            this.setState({ registered: req });
                            this.addNotification({ title: 'Erro no registo', message: "Este nome de utilizador já se encontra registado", type: 'danger' });
                        }

                        setSubmitting(false);
                    }}
                >
                    {({
                        values,
                        errors,
                        touched,
                        handleChange,
                        handleBlur,
                        handleSubmit,
                        isSubmitting,
                    }) => (
                            <form className="form" onSubmit={handleSubmit}>
                                <div>
                                    <Field className="username input-login" type="username" name="username" placeholder="Username" />
                                    <ErrorMessage name="username" component="div" className="ErrorMessa" />
                                </div>

                                <div>
                                    <Field className="name input-login" type="username" name="name" placeholder="Name" />
                                    <ErrorMessage name="name" component="div" className="ErrorMessa" />
                                </div>

                                <div>
                                    <Field className="email input-login" type="email" name="email" placeholder="Email" />
                                    <ErrorMessage name="email" component="div" className="ErrorMessa" />
                                </div>

                                <div>
                                    <Field className="password input-login" type="password" name="password" placeholder="Password" />
                                    <ErrorMessage name="password" component="div" className="ErrorMessa" />
                                </div>

                                <div>
                                    <Field className="password input-login" type="password" name="cpassword" placeholder="Confirm Password" />
                                    <ErrorMessage name="cpassword" component="div" className="ErrorMessa" />
                                </div>

                                <div className="button-container">
                                    <button className="btn button-login" type="submit" disabled={isSubmitting}>Registar</button>
                                </div>
                            </form>
                        )}
                </Formik>

            </div >
        );

    }
}

export default Register;
