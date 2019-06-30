import React, { Component } from 'react';
import moment from 'moment';

import './ResumoEspacoEventos.css';

const organizeEventsByTime = (events) => {

    let ongoing = [];
    let later = [];

    const current = moment();

    events.forEach(event => {
        const start = moment(event.start);
        const end = moment(event.end);

        if (current.isAfter(end))
            return

        if (current.isBefore(start)) {
            if (current.isSame(start, 'day')) {
                later.push(event);
            }

        } else if (current.isSameOrAfter(start) && current.isBefore(end)) {
            ongoing.push(event);
        }
    })

    return {
        ongoing, later
    }
}

const isNextDay = (m1, m2) => {

    if ((m1.diff(m2, 'days', true) > -1)) {
        return true
    }
    return false;
}

const ResumoEspacoEventos = (props) => {

    const { eventosHoje, eventosAmanha } = props;

    const organizedEvents = organizeEventsByTime(eventosHoje || [])
    const { ongoing, later, } = organizedEvents;

    const tomorrow = props.eventosAmanha;

    return (
        <div>
            <div className="row">
                <div className="col-xs-12 col-md-12">

                    <h3>A decorrer</h3>

                    <table className="table ec-table">
                        <tbody>
                            {ongoing.map(event => (
                                <tr>
                                    <td>
                                        <p>{event.name}</p>

                                    </td>
                                    <td>
                                        <i className="material-icons individual-icon" >
                                            location_on
                                          </i>
                                        <p>{event.espaco.designacao}</p>
                                    </td>
                                    <td>
                                        <i className="material-icons individual-icon" >
                                            access_time
                                          </i>
                                        <p>{moment(event.start).format('HH:mm')} - {moment(event.end).format('HH:mm')}</p>
                                    </td>
                                    <td>
                                        <i className="material-icons individual-icon" >
                                            person
                                          </i>
                                        <p>{event.responsavel.nome}</p>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>

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
                                        <p>{event.name}</p>

                                    </td>
                                    <td>
                                        <i className="material-icons individual-icon" >
                                            location_on
                                          </i>
                                        <p>{event.espaco.designacao}</p>
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
                                        <p>{event.name}</p>

                                    </td>
                                    <td>
                                        <i className="material-icons individual-icon" >
                                            location_on
                                          </i>
                                        <p>{event.espaco.designacao}</p>
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

export default ResumoEspacoEventos;
