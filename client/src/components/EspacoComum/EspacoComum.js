import React, { Component } from 'react';

import './EspacoComum.css';

import EspacoComumEventos from './EspacoComumEventos/EspacoComumEventos';
import EspacoComumHorarios from './EspacoComumHorarios/EspacoComumHorarios';

class EspacoComum extends Component {
    constructor(props) {
        super(props);
        this.state = { activeButton: 'Eventos' }
    }

    changeActiveButton = (pressed) => {
        this.setState({ activeButton: pressed })
    }



    render() {

        const { activeButton } = this.state;

        const baseCss = 'btn btn-filter'
        const active = 'btn-filter-active';



        return (
            <div>
                <h2>Espaco Comum</h2>

                <div className="btn-section">
                    <button className={activeButton === 'Eventos' ? `${baseCss} ${active}` : baseCss} onClick={() => this.changeActiveButton('Eventos')}>Eventos</button>
                    <button className={activeButton === 'Horarios' ? `${baseCss} ${active}` : baseCss} onClick={() => this.changeActiveButton('Horarios')}>Horários</button>
                </div>

                {activeButton === 'Eventos' ? <EspacoComumEventos /> : ''}
                {activeButton === 'Horarios' ? <EspacoComumHorarios /> : ''}

            </div>
        );
    }
}

export default EspacoComum;