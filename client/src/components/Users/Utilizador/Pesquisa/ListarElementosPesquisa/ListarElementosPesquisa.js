import React, { Component } from 'react';
import moment from 'moment';

import './ListarElementosPesquisa.css';

class ListarElementosPesquisa extends Component {

    constructor(props) {
        super(props);
        this.state = {};
    };

    handleFollow = (evento) => {
        // Mark event as being followed/unfollowed

        const { listar } = this.props;
        const { eventos, espacos, aSeguir } = listar;

        if (!aSeguir.includes(evento)) {
            aSeguir.push(evento);
            eventos.splice(eventos.indexOf(evento), 1);
        }
        else {
            aSeguir.splice(aSeguir.indexOf(evento), 1);
            eventos.push(evento);
        }
    };

    render() {
        const { listar } = this.props;

        const { eventos, espacos, aSeguir } = listar;

        return (
            <div>
                <div>
                    <table className="table ec-table">
                        <tbody>
                            {eventos && eventos.map(event => (
                                <tr>
                                    <td>
                                        <p>{event.nome}</p>
                                    </td>
                                    <td>
                                        <i className="material-icons individual-icon">
                                            location_on
                                        </i>
                                        <p>{event.local}</p>
                                    </td>
                                    <td>
                                        <i className="material-icons individual-icon">
                                            calendar_today
                                        </i>
                                        <p>{moment(event.data).format('DD/MM')} {moment(event.start).format('HH:mm')} - {moment(event.end).format('HH:mm')}</p>
                                    </td>
                                    <td>
                                        <i className="material-icons individual-icon">
                                            person
                                        </i>
                                        <p>{event.responsavel}</p>
                                    </td>
                                    <td>
                                        {
                                            !aSeguir.includes(event.nome) &&
                                            <a href="#" className="alert-danger" onClick={() => this.handleFollow(event)}>
                                                <i className="material-icons individual-icon">
                                                    star_border
                                                </i>
                                            </a>
                                        }
                                        {
                                            aSeguir.includes(event.nome) &&
                                            <a href="#" className="alert-danger" onClick={() => this.handleFollow(event)}>
                                                <i className="material-icons individual-icon">
                                                    star
                                                </i>
                                            </a>
                                        }
                                    </td>
                                </tr>
                            ))}

                            {this.followedEvents && this.followedEvents.map(event => (
                                <tr>
                                    <td>
                                        <p>{event.nome}</p>
                                    </td>
                                    <td>
                                        <i className="material-icons individual-icon">
                                            location_on
                                        </i>
                                        <p>{event.local}</p>
                                    </td>
                                    <td>
                                        <i className="material-icons individual-icon">
                                            calendar_today
                                        </i>
                                        <p>{moment(event.data).format('DD-MM')} {moment(event.start).format('HH:mm')} - {moment(event.end).format('HH:mm')}</p>
                                    </td>
                                    <td>
                                        <i className="material-icons individual-icon">
                                            person
                                        </i>
                                        <p>{event.responsavel}</p>
                                    </td>
                                    <td>
                                        {
                                            !aSeguir.includes(event.nome) &&
                                            <a href="#" className="alert-danger" onClick={() => this.handleFollow(event)}>
                                                <i className="material-icons individual-icon">
                                                    star_border
                                                </i>
                                            </a>
                                        }
                                        {
                                            aSeguir.includes(event.nome) &&
                                            <a href="#" className="alert-danger" onClick={() => this.handleFollow(event)}>
                                                <i className="material-icons individual-icon">
                                                    star
                                                </i>
                                            </a>
                                        }
                                    </td>
                                </tr>
                            ))}

                            {espacos && espacos.map(espaco => (
                                <tr>
                                    <td>
                                        {espaco}
                                    </td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}

export default ListarElementosPesquisa;
