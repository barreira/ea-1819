import React, { Component } from 'react';
import moment from 'moment';

import './EspacoComumEventos.css';

class EspacoComumEventos extends Component {
    constructor(props) {
        super(props);
        this.state = { events: [], ongoing: [], later: [], tomorrow: [], loading: true }
    }

    componentDidMount() {
        // Fetch da lista de eventos
        const events = [
            {
                title: 'Computação Gráfica',
                start: moment().subtract(3, 'days').valueOf(),
                end: moment().subtract(3, 'days').add('3', 'h').valueOf(),
                local: 'DI-01',
                responsavel: 'João Carlos'
            },
            {
                title: 'Arquiteturas Aplicacionais',
                start: moment().add(3, 'hours').valueOf(),
                end: moment().add(5, 'hours').valueOf(),
                local: 'DI-01',
                responsavel: 'Maria Carolina'
            },
            {
                title: 'Engenharia Web',
                start: moment().add(1, 'days').valueOf(),
                end: moment().add(1, 'days').add('1', 'h').valueOf(),
                local: 'DI-01',
                responsavel: 'João Carlos'
            },
            {
                title: 'Programação Avançada II',
                start: moment().add(1, 'days').subtract('5', 'h').valueOf(),
                end: moment().add(1, 'days').subtract('3', 'h').valueOf(),
                local: 'DI-01',
                responsavel: 'João Carlos'
            },
            {
                title: 'Programação Avançada III',
                start: moment().valueOf(),
                end: moment().add(5, 'h').valueOf(),
                local: 'DI-01',
                responsavel: 'João Carlos'
            },
            {
                title: 'Programação Avançada',
                start: moment().valueOf(),
                end: moment().add(5, 'h').valueOf(),
                local: 'DI-01',
                responsavel: 'João Carlos'
            },
            {
                title: 'Programação Avançada II',
                start: moment().valueOf(),
                end: moment().add(3, 'h').valueOf(),
                local: 'DI-02',
                responsavel: 'Luis Miguel'
            },
            {
                title: 'Programação Avançada III',
                start: moment().add(2, 'h').valueOf(),
                end: moment().add(3, 'h').valueOf(),
                local: 'DI-02',
                responsavel: 'Luis Miguel'
            }
        ];

        this.organizeEventsByTime(events)
    }

    organizeEventsByTime = (events) => {
        let ongoing = [];
        let later = [];
        let tomorrow = [];

        const current = moment().format('DD-MM-YYYY hh:mm:ss');

        events.forEach(event => {

            const start = moment(event.start).format('DD-MM-YYYY hh:mm:ss');
            const end = moment(event.end).format('DD-MM-YYYY hh:mm:ss');

            if (current > end) {
                return
            }

            const mStart = moment(start, 'DD-MM-YYYY')
            const mCurr = moment(current, 'DD-MM-YYYY')


            if (current < start) {

                if (moment(current, 'DD-MM-YYYY').isSame(moment(start, 'DD-MM-YYYY'), 'day')) {
                    later.push(event);
                } else if (mStart.diff(mCurr, 'days') === 1) {
                    tomorrow.push(event)
                }

            } else if (current >= start && current < end) {
                ongoing.push(event);
            }

        })

        this.setState({
            ongoing, later, tomorrow, events, loading: false
        })
    }



    render() {

        const { ongoing, later, tomorrow, loading } = this.state;


        if (loading)
            return <div></div>


        return (
            <div>
                <div className="row">
                    <div className="col-xs-12 col-md-12">

                        <h3>A decorrer</h3>

                    </div>
                </div>

                <div className="row">
                    <div className="col-xs-12 col-md-6">
                        <h3>Mais tarde</h3>
                        <table className="table ec-table">
                            <tbody>
                                {later.map(event => (
                                    <tr>
                                        <td>
                                            <p>{event.title}</p>

                                        </td>
                                        <td>
                                            <i className="material-icons individual-icon" >
                                                location_on
                                          </i>
                                            <p>{event.local}</p>
                                        </td>
                                        <td>

                                            <i className="material-icons individual-icon" >
                                                calendar_today
                                          </i>

                                            <p>{moment(event.start).format('HH:mm')}</p>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>

                    <div className="col-xs-12 col-md-6">
                        <h3>Amanhã</h3>
                        <table className="table ec-table">
                            <tbody>
                                {tomorrow.map(event => (
                                    <tr>
                                        <td>
                                            <p>{event.title}</p>

                                        </td>
                                        <td>
                                            <i className="material-icons individual-icon" >
                                                location_on
                                          </i>
                                            <p>{event.local}</p>
                                        </td>
                                        <td>

                                            <i className="material-icons individual-icon" >
                                                calendar_today
                                          </i>

                                            <p>{moment(event.start).format('HH:mm')}</p>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div >
        );
    }
}

export default EspacoComumEventos;
