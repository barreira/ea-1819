import React, { Component } from 'react';

import './ResumoEspaco.css';

import ResumoEspacoEventos from './ResumoEspacoEventos/ResumoEspacoEventos';
import ResumoEspacoHorarios from './ResumoEspacoHorarios/ResumoEspacoHorarios';

import ApiEventos from '../../api/ApiEventos';
import ApiUsers from '../../api/ApiUsers';

class ResumoEspaco extends Component {
    constructor(props) {
        super(props);
        this.state = { activeButton: 'Eventos' }
    }

    changeActiveButton = (pressed) => {
        this.setState({ activeButton: pressed })
    }


    testFunction = () => {
        console.log("TESTE")

        ApiUsers.register();
    }


    render() {

        const { activeButton } = this.state;

        const baseCss = 'btn btn-filter'
        const active = 'btn-filter-active';

        return (
            <div>

                <button type="text" onClick={this.testFunction}>TEST BUTTON</button>

                <h3 style={{ textAlign: 'center' }}>{this.props.title}</h3>

                <div className="btn-section">
                    <button className={activeButton === 'Eventos' ? `${baseCss} ${active}` : baseCss} onClick={() => this.changeActiveButton('Eventos')}>Eventos</button>
                    <button className={activeButton === 'Horarios' ? `${baseCss} ${active}` : baseCss} onClick={() => this.changeActiveButton('Horarios')}>Horários</button>
                </div>

                {activeButton === 'Eventos' ? <ResumoEspacoEventos /> : ''}
                {activeButton === 'Horarios' ? <ResumoEspacoHorarios /> : ''}



            </div>
        );
    }
}

export default ResumoEspaco;