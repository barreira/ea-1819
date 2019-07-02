import React from 'react';
import {Route, Switch} from "react-router";

import Navbar from "../Navbar/Navbar";

class Home extends React.Component {

    state = {
        loggedInAs: null // ['Utilizador', 'UtilizadorCPDR', 'Gestor', 'Administrador']
    };

    // state = {
    //     aDecorrer: [
    //         { title: 'Programação Imperativa 1', location: 'DI-0.01', responsible: 'Zé Luís', start: '11:00', end: '13:00', description: 'Isto é a descrição do evento.' },
    //         { title: 'Programação Imperativa 2', location: 'DI-0.01', responsible: 'Zé Luís', start: '11:00', end: '13:00', description: 'Isto é a descrição do evento.' },
    //         { title: 'Programação Imperativa 3 ', location: 'DI-0.01', responsible: 'Zé Luís', start: '11:00', end: '13:00', description: 'Isto é a descrição do evento.' },
    //         { title: 'Programação Imperativa 4', location: 'DI-0.01', responsible: 'Zé Luís', start: '11:00', end: '13:00', description: 'Isto é a descrição do evento.' }
    //     ],
    //     maisTarde: [
    //         { title: 'Programação Imperativa 1', location: 'DI-0.01', responsible: 'Zé Luís', start: '16:00', end: '18:00', description: 'Isto é a descrição do evento.' },
    //         { title: 'Programação Imperativa 2', location: 'DI-0.01', responsible: 'Zé Luís', start: '16:00', end: '18:00', description: 'Isto é a descrição do evento.' },
    //         { title: 'Programação Imperativa 3', location: 'DI-0.01', responsible: 'Zé Luís', start: '16:00', end: '18:00', description: 'Isto é a descrição do evento.' },
    //         { title: 'Programação Imperativa 4', location: 'DI-0.01', responsible: 'Zé Luís', start: '16:00', end: '18:00', description: 'Isto é a descrição do evento.' }
    //     ],
    //     amanha: [
    //         { title: 'Programação Imperativa 1', location: 'DI-0.01', responsible: 'Zé Luís', start: '09:00', end: '11:00', description: 'Isto é a descrição do evento.' },
    //         { title: 'Programação Imperativa 2', location: 'DI-0.01', responsible: 'Zé Luís', start: '09:00', end: '11:00', description: 'Isto é a descrição do evento.' },
    //         { title: 'Programação Imperativa 3', location: 'DI-0.01', responsible: 'Zé Luís', start: '09:00', end: '11:00', description: 'Isto é a descrição do evento.' },
    //         { title: 'Programação Imperativa 4', location: 'DI-0.01', responsible: 'Zé Luís', start: '09:00', end: '11:00', description: 'Isto é a descrição do evento.' }
    //     ],
    // };

    setLoggedInAs(user) {
        this.setState({
            loggedInAs: user
        });
        console.log('Home: Utilizador');
    }

    render() {
        return (
            <div>
                <Navbar setLoggedInAs={this.setLoggedInAs} />

                <Switch>
                    {!this.state.loggedInAs && <Route exact path="/" component="HomeVisitante" />}
                    {this.state.loggedInAs === 'Utilizador' && <Route exact path="/" component="HomeUtilizador" />}
                </Switch>
            </div>
        );
    }
}

export default Home;
