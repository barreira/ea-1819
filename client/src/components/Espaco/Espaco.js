import React, { Component } from 'react';
import WeeklyCalendar from '../WeeklyCalendar/WeeklyCalendar';

import moment from 'moment';

class Espaco extends Component {
    constructor(props) {
        super(props);
        this.state = { events: [] }
    }

    componentDidMount() {

        console.log(moment().valueOf())
        console.log(Date.now())
        const events = [
            {
                title: 'Computação Gráfica',
                start: moment().subtract(3, 'days').valueOf(),
                end: moment().subtract(3, 'days').add('3', 'h').valueOf()
            },
            {
                title: 'Arquiteturas Aplicacionais',
                start: moment().subtract(5, 'days').valueOf(),
                end: moment().subtract(5, 'days').add('5', 'h').valueOf()
            },
            {
                title: 'Engenharia Web',
                start: moment().subtract(2, 'days').valueOf(),
                end: moment().subtract(2, 'days').add('1', 'h').valueOf()
            },
            {
                title: 'Programação Avançada',
                start: moment().subtract(3, 'days').subtract('5', 'h').valueOf(),
                end: moment().subtract(3, 'days').subtract('3', 'h').valueOf()
            }
        ];

        this.setState({ events });

    }

    render() {

        const { nomeEspaco } = this.props;

        return (
            <div>
                <p>{nomeEspaco} </p>
                <WeeklyCalendar events={this.state.events} />
            </div>
        );
    }
}

export default Espaco;