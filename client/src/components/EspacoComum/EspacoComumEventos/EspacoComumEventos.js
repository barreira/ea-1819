import React, { Component } from 'react';
import moment from 'moment';

import './EspacoComumEventos.css';

class EspacoComumEventos extends Component {
    constructor(props) {
        super(props);
        this.state = { events: [], ongoing: [], later: [], tomorrow: [] }
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
                start: moment().subtract(5, 'days').valueOf(),
                end: moment().subtract(5, 'days').add('5', 'h').valueOf(),
                local: 'DI-01',
                responsavel: 'Maria Carolina'
            },
            {
                title: 'Engenharia Web',
                start: moment().subtract(2, 'days').valueOf(),
                end: moment().subtract(2, 'days').add('1', 'h').valueOf(),
                local: 'DI-01',
                responsavel: 'João Carlos'
            },
            {
                title: 'Programação Avançada',
                start: moment().subtract(3, 'days').subtract('5', 'h').valueOf(),
                end: moment().subtract(3, 'days').subtract('3', 'h').valueOf(),
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
            }
        ];


        this.setState({ events })
    }

    organizeEventsByTime = (events) => {



    }



    render() {

        const { events } = this.state;


        if (!events)
            return <div></div>


        return (
            <div>
                <div className="row">
                    <div className="col-md-12">

                        <h3>A decorrer</h3>
                        <table className="table ec-table">
                            <tbody>
                                {events.map(event => (
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
                                            <p>{moment(event.start).format('HH:mm')} - {moment(event.end).format('HH:mm')}</p>
                                        </td>
                                        <td>
                                            <i className="material-icons individual-icon" >
                                                person
                                          </i>
                                            <p>{event.responsavel}</p>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>

                    </div>
                </div>

                <div className="row">
                    <div className="col-md-6">
                        <h3>Mais tarde</h3>
                        <table className="table ec-table">
                            <tbody>
                                {events.map(event => (
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
                    <div className="col-md-6">
                        <h3>Amanhã</h3>
                        <table className="table ec-table">
                            <tbody>
                                {events.map(event => (
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
