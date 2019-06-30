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

        console.log("Recieving props")
        console.log("props", this.props.eventos)
        console.log(typeof this.props.eventos)

        let finalEvents = [];

        const eventos = this.props.eventos;

        for (let day in eventos) {
            const dailyEvents = eventos[day];

            console.log("DAILY EVENTS", dailyEvents)

            if (Array.isArray(dailyEvents)) {

                dailyEvents.forEach(event => {
                    console.log(event)
                    finalEvents.push({ ...this.formatEvent(event, day) });

                    // finalEvents.push(...this.formatEvent(event, day))
                })
            }
        }



        console.log("FINAL EVENTS", finalEvents)
        this.setState({
            events: finalEvents, espacos, filteredEvents: finalEvents
        })

    }

    filterByName = (e) => {
        const locationName = e.target.innerHTML;

        const filteredEvents = this.state.events.filter(event => event.local === locationName);

        this.setState({
            filteredEvents
        })
    }

    formatEvent = (event, day) => {
        const startTime = moment(event.dateTimeInicial).format('HH:mm')
        const endTime = moment(event.dateTimeFinal).format('HH:mm')

        const newStart = moment(`${day} ${startTime}`, 'YYYY-MM-DD HH:mm');
        const newEnd = moment(`${day} ${endTime}`, 'YYYY-MM-DD HH:mm');

        return {
            title: event.nome,
            start: newStart.valueOf(),
            end: newEnd.valueOf(),
            local: event.espaco.designacao,
            responsavel: event.utilizadorResponsavel.nome
        }
    }


    render() {

        const { eventos } = this.props.eventos;

        console.log(eventos)


        return (
            <div>
                <FiltrarPorEspaco filter={this.filterByName} espacos={this.state.espacos} />
                <WeeklyCalendar events={this.state.filteredEvents} />
            </div>
        );
    }
}

export default ResumoEspacoHorarios;