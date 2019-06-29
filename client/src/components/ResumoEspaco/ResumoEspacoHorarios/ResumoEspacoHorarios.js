import React, { Component } from 'react';
import moment from 'moment';

import WeeklyCalendar from '../../WeeklyCalendar/WeeklyCalendar';
import FiltrarPorEspaco from '../FiltrarPorEspaco/FiltrarPorEspaco';

class ResumoEspacoHorarios extends Component {
    constructor(props) {
        super(props);
        this.state = { events: [], filteredEvents: [] }

        this.filterByName = this.filterByName.bind(this);
    }

    componentDidMount() {

        const events = [
            {
                title: 'Engenharia Web',
                start: moment().valueOf(),
                end: moment().add(3, 'hours').valueOf(),
                local: 'DI-01',
                responsavel: 'João Carlos'
            },
            {
                title: 'Engenharia Web II',
                start: moment().add(2, 'days').valueOf(),
                end: moment().add(2, 'days').add('1', 'h').valueOf(),
                local: 'DI-02',
                responsavel: 'João Carlos'
            },
            {
                title: 'Engenharia Web III',
                start: moment('15:30').add(1, 'days').valueOf(),
                end: moment('16:30').add('1', 'h').valueOf(),
                local: 'DI-01',
                responsavel: 'João Carlos'
            },
        ]

        const espacos = [...new Set(events.map(event => event.local))];

        this.setState({
            events, espacos, filteredEvents: events
        })

    }

    filterByName = (e) => {
        const locationName = e.target.innerHTML;

        const filteredEvents = this.state.events.filter(event => event.local === locationName);

        this.setState({
            filteredEvents
        })
    }

    render() {
        return (
            <div>
                <FiltrarPorEspaco filter={this.filterByName} espacos={this.state.espacos} />
                <WeeklyCalendar events={this.state.filteredEvents} />
            </div>
        );
    }
}

export default ResumoEspacoHorarios;