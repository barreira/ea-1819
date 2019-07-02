import React, { Component } from 'react';
import moment from 'moment';

import './ListarElementosPesquisa.css';
import ApiEventos from '../../../../../api/ApiEventos';

class ListarElementosPesquisa extends Component {

    constructor(props) {
        super(props);
        this.state = {};

    };

    handleFollow = async (evento) => {

        const result = await ApiEventos.followEvent(evento.id)
        const { listar } = this.props;
        this.props.updateParent();

    };

    render() {

        const { listar } = this.props;

        let { eventos, espacos, aSeguir, meusEventos } = listar;

        if (meusEventos && eventos) {
            eventos = eventos.filter(e => {
                if (meusEventos.filter(me => e.id === me.id).length > 0) {
                    return false;
                }
                return true;
            })
        }

        return (
            <div>
                <div>
                    <table className="table ec-table">
                        <tbody>
                            {meusEventos && meusEventos.map(event => (
                                <tr style={{ backgroundColor: "#ddd" }}>
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
                                            !event.aSeguir &&
                                            <a href="#" className="alert-danger" onClick={() => this.handleFollow(event)}>
                                                <i className="material-icons individual-icon">
                                                    star_border
                                                </i>
                                            </a>
                                        }
                                        {
                                            event.aSeguir &&
                                            <a href="#" className="alert-danger" onClick={() => this.handleFollow(event)}>
                                                <i className="material-icons individual-icon">
                                                    star
                                                </i>
                                            </a>
                                        }
                                    </td>
                                </tr>
                            ))}
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
                                            !event.aSeguir &&
                                            <a href="#" className="alert-danger" onClick={() => this.handleFollow(event)}>
                                                <i className="material-icons individual-icon">
                                                    star_border
                                                </i>
                                            </a>
                                        }
                                        {
                                            event.aSeguir &&
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
