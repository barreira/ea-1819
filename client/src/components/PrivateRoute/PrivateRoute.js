import React from 'react';
import { Redirect, Route } from 'react-router-dom';
import UserHandler from '../../utils/userHandler';
import HomeUtilizador from '../../components/Users/Utilizador/Home/HomeUtilizador';
import HomeVisitante from '../../components/Users/Visitante/Home/HomeVisitante';
import HomeGestor from '../../components/Users/Gestor/HomeGestor/HomeGestor';

const PrivateRoute = ({ component: Component, ...rest }) => {

    const typeUser = UserHandler.typeOfUser();

    const redirectLogin = (
        <Route {...rest} render={(props) => (
            <Redirect to={{
                pathname: '/login',
                state: { from: props.location }
            }} />
        )} />)

    if (rest.testFunction)
        rest.testFunction();

    let pathLimitations = [];


    // FUNCIONAMENTO
    // Para cada tipo de utilizador definem-se as limitações dos paths
    // E caso seja necessário pode-se adicionar lógica para dar re-route de acordo com o pretendido

    switch (typeUser) {
        case 'Visitante':


            pathLimitations = ['/espaco']

            if (rest.path === '/') {
                return (
                    <Route {...rest} render={(props) => (
                        <HomeVisitante {...props} />
                    )} />
                )
            }
            break;

        case 'Utilizador':

            if (rest.path === '/') {
                return (
                    <Route {...rest} render={(props) => (
                        <HomeUtilizador {...props} />
                    )} />
                )
            }
            break;

        case 'UtilizadorCPDR':

            break;

        case 'Gestor':
            if (rest.path === '/') {
                return (
                    <Route {...rest} render={(props) => (
                        <HomeGestor {...props} />
                    )} />
                )
            }
            break;

        case 'Administrador':
            break;
    }

    if (pathLimitations.includes(rest.path))
        return (redirectLogin)

    return (
        <Route {...rest} render={(props) => (
            <Component {...props} />
        )} />
    )
}

export default PrivateRoute;