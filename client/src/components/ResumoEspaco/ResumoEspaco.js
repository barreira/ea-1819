import React, { Component } from 'react';
import moment from 'moment';

import './ResumoEspaco.css';

import ResumoEspacoEventos from './ResumoEspacoEventos/ResumoEspacoEventos';
import ResumoEspacoHorarios from './ResumoEspacoHorarios/ResumoEspacoHorarios';

import ApiEventos from '../../api/ApiEventos';
import ApiUsers from '../../api/ApiUsers';

class ResumoEspaco extends Component {
    constructor(props) {
        super(props);
        this.state = { activeButton: 'Eventos', eventos: [], eventosHoje: [], eventosAmanha: [], loading: true }
    }

    async componentDidMount() {
        const eventos = await ApiEventos.fetchEventos(moment().format('YYYY-MM-DD'), moment().add('1', 'days').format('YYYY-MM-DD'));

        const dataAtual = moment().format('YYYY-MM-DD')
        const dataAmanha = moment().add('1', 'days').format('YYYY-MM-DD');

        const dataFinal = moment().format('YYYY-MM-DD')

        let eventosHoje = [];
        let eventosAmanha = [];

        (eventos[dataAtual] || []).forEach(evento => {
            eventosHoje.push({
                ...this.formatarEvento(evento)
            })
        });

        (eventos[dataAmanha] || []).forEach(evento => {
            eventosAmanha.push({
                ...this.formatarEvento(evento)
            })
        });

        this.setState({
            eventos,
            eventosHoje,
            eventosAmanha,
            loading: false
        })

    }

    formatarEvento = (evento) => {
        return {
            name: evento.nome,
            start: evento.dateTimeInicial,
            end: evento.dateTimeFinal,
            descricao: evento.descricao,
            responsavel: evento.utilizadorResponsavel,
            espaco: evento.espaco
        }
    }

    changeActiveButton = (pressed) => {
        this.setState({ activeButton: pressed })
    }


    testFunction = () => {
        console.log("TESTE")
        // ApiUsers.calendarPermissions();
        ApiEventos.followEvent(1)
    }


    render() {

        const { activeButton, eventosHoje, eventosAmanha, eventos, loading } = this.state;

        if (loading)
            return <div></div>

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

                {activeButton === 'Eventos' ? <ResumoEspacoEventos eventosHoje={eventosHoje} eventosAmanha={eventosAmanha} /> : ''}
                {activeButton === 'Horarios' ? <ResumoEspacoHorarios eventos={eventos || []} /> : ''}



            </div>
        );
    }
}

export default ResumoEspaco;