import React, { Component } from 'react';

import EventosMaisTarde from '../EventosMaisTarde/EventosMaisTarde';
import EventosAmanha from '../EventosAmanha/EventosAmanha';
import EventosADecorrer from '../EventosADecorrer/EventosADecorrer';

class Home extends Component {

    state = {
        aDecorrer: [
            { title: 'Programação Imperativa 1', location: 'DI-0.01', responsible: 'Zé Luís', start: '11:00', end: '13:00', description: 'Isto é a descrição do evento.' },
            { title: 'Programação Imperativa 2', location: 'DI-0.01', responsible: 'Zé Luís', start: '11:00', end: '13:00', description: 'Isto é a descrição do evento.' },
            { title: 'Programação Imperativa 3 ', location: 'DI-0.01', responsible: 'Zé Luís', start: '11:00', end: '13:00', description: 'Isto é a descrição do evento.' },
            { title: 'Programação Imperativa 4', location: 'DI-0.01', responsible: 'Zé Luís', start: '11:00', end: '13:00', description: 'Isto é a descrição do evento.' }
        ],
        maisTarde: [
            { title: 'Programação Imperativa 1', location: 'DI-0.01', responsible: 'Zé Luís', start: '16:00', end: '18:00', description: 'Isto é a descrição do evento.' },
            { title: 'Programação Imperativa 2', location: 'DI-0.01', responsible: 'Zé Luís', start: '16:00', end: '18:00', description: 'Isto é a descrição do evento.' },
            { title: 'Programação Imperativa 3', location: 'DI-0.01', responsible: 'Zé Luís', start: '16:00', end: '18:00', description: 'Isto é a descrição do evento.' },
            { title: 'Programação Imperativa 4', location: 'DI-0.01', responsible: 'Zé Luís', start: '16:00', end: '18:00', description: 'Isto é a descrição do evento.' }
        ],
        amanha: [
            { title: 'Programação Imperativa 1', location: 'DI-0.01', responsible: 'Zé Luís', start: '09:00', end: '11:00', description: 'Isto é a descrição do evento.' },
            { title: 'Programação Imperativa 2', location: 'DI-0.01', responsible: 'Zé Luís', start: '09:00', end: '11:00', description: 'Isto é a descrição do evento.' },
            { title: 'Programação Imperativa 3', location: 'DI-0.01', responsible: 'Zé Luís', start: '09:00', end: '11:00', description: 'Isto é a descrição do evento.' },
            { title: 'Programação Imperativa 4', location: 'DI-0.01', responsible: 'Zé Luís', start: '09:00', end: '11:00', description: 'Isto é a descrição do evento.' }
        ],
    };

    render() {
        return (
            <div className="container">
                <div className="row">
                    <div className="col-xs-12 col-md-7">
                        <EventosADecorrer items={this.state.aDecorrer} />
                    </div>
                    <div className="col-xs-12 col-md-5">
                        <EventosMaisTarde items={this.state.maisTarde} />
                        <EventosAmanha items={this.state.amanha} />
                    </div>
                </div>
            </div>
        );
    }
}

export default Home;
