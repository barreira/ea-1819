import React from 'react';
import { Redirect, Route } from 'react-router-dom';

import UserHandler from '../../utils/userHandler';
import HomeUtilizador from '../../components/Users/Utilizador/Home/HomeUtilizador';
import HomeVisitante from '../../components/Users/Visitante/Home/HomeVisitante';
import HomeUtilizadorCPDR from "../Users/UtilizadorCPDR/Home/HomeUtilizadorCPDR";
import HomeGestor from "../Users/Gestor/HomeGestor/HomeGestor";
import HomeAdministrador from "../Users/Administrador/Home/HomeAdministrador";
import PesquisaGestor from "../Users/Gestor/Pesquisa/PesquisaGestor";

const PrivateRoute = ({ component: Component, ...rest }) => {

    const typeUser = UserHandler.typeOfUser();

    const redirectLogin = (
        <Route {...rest} render={(props) => (
            <Redirect to={{
                pathname: '/login',
                state: { from: props.location }
            }} />
        )} />);

    if (rest.testFunction) {
        rest.testFunction();
    }

    let pathLimitations = [];

    // FUNCIONAMENTO
    // Para cada tipo de utilizador definem-se as limitações dos paths
    // E caso seja necessário pode-se adicionar lógica para dar re-route de acordo com o pretendido

    switch (typeUser) {
        case 'Visitante':
            pathLimitations = ['/', '/login', '/registar', '/espacos', '/pesquisar'];

            if (rest.path === '/') {
                return (
                    <Route {...rest} render={(props) => (
                        <HomeVisitante {...props} />
                    )} />
                )
            }

            break;

        case 'Utilizador':
            pathLimitations = ['/', '/eventos', '/notificacoes', '/pesquisar'];

            if (rest.path === '/') {
                return (
                    <Route {...rest} render={(props) => (
                        <HomeUtilizador {...props} />
                    )} />
                )
            }
            break;

        case 'UtilizadorCPDR':
            pathLimitations = ['/', '/espacos', '/eventos', '/pedidos', '/notificacoes', '/pesquisar'];

            if (rest.path === '/') {
                return (
                    <Route {...rest} render={(props) => (
                        <HomeUtilizadorCPDR {...props} />
                    )} />
                )
            }

            break;

        case 'GestorEspacos':
            pathLimitations = ['/', '/espacos', '/eventos', '/eventos/criar', '/eventos/modificar', '/espacos-comuns',
                '/espacos-comuns/criar', '/espacos-comuns/modificar', '/pedidos', '/notificacoes', '/pesquisar'];

            if (rest.path === '/') {
                return (
                    <Route {...rest} render={(props) => (
                        <HomeVisitante {...props} />
                    )} />
                )
            }

            if (rest.path === '/pesquisar') {
                return (
                    <Route {...rest} render={(props) => (
                        <PesquisaGestor {...props} />
                    )} />
                );
            }

            break;

        case 'Administrador':
            pathLimitations = ['/', '/espacos', '/espacos/carregar', '/eventos', '/eventos/carregar', '/utilizadores',
                'utilizadores/carregar', '/espacos-comuns', '/pesquisar'];

            if (rest.path === '/') {
                return (
                    <Route {...rest} render={(props) => (
                        <HomeAdministrador {...props} />
                    )} />
                )
            }

            break;

        default:

            if (rest.path === '/') {
                return (
                    <Route {...rest} render={(props) => (
                        <HomeVisitante {...props} />
                    )} />
                )
            }
            break;
    }

    if (!pathLimitations.includes(rest.path)) {
        return (redirectLogin);
    }

    return (
        <Route {...rest} render={(props) => (
            <Component {...props} />
        )} />
    )
};

export default PrivateRoute;