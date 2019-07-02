import React, { Component } from 'react';
import moment from 'moment';
import EditarEvento from '../../../../EditarEvento/EditarEvento';
import { Redirect, Route } from 'react-router-dom';
import ApiEventos from '../../../../../api/ApiEventos';

class ListarElementosPesquisa extends Component {

    constructor(props) {
        super(props);
        this.state = {
            editEvent: false,
            eventName: ""
        };
    }

    editEvent = (eventName) => {

        console.log("EDITING EVENT ID", eventName)

        this.setState({
            editEvent: true,
            eventName: eventName
        })
    }

    cancelEvent = async (eventId) => {
        await ApiEventos.cancelar(eventId);
        this.props.removeEventById(eventId);
    }

    exitEdit = () => {
        this.setState({
            editEvent: false
        })
    }

    render() {
        const { listar } = this.props;

        const { eventos, espacos, responsaveis } = listar;

        // Listar Eventos

        // Listar Espaços

        // Listar Responsáveis

        if (this.state.editEvent) {

            const eventoAEditar = eventos.filter(evt => evt.nome === this.state.eventName)[0];

            console.log("EVENTO A EDITAR", eventoAEditar)

            return (
                <Redirect to={{
                    pathname: `/evento/editar/${this.state.eventName}`
                }} />
            )
        }





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
                                        <a href="#" className="alert-danger">
                                            <i className="material-icons individual-icon" onClick={() => this.editEvent(event.nome)}>
                                                edit
                                            </i>
                                        </a>
                                    </td>
                                    <td>
                                        <a href="#" className="alert-danger">
                                            <i className="material-icons individual-icon" onClick={() => this.cancelEvent(event.id)}>
                                                cancel
                                            </i>
                                        </a>
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
                                    <td></td>
                                </tr>
                            ))}

                            {responsaveis && responsaveis.map(responsavel => (
                                <tr>
                                    <td>
                                        {responsavel}
                                    </td>
                                    <td></td>
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
