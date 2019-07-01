import React, { Component } from 'react';
import moment from 'moment';
import {Link} from "react-router-dom";

class ListarElementosPesquisa extends Component {

    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {
        const { listar } = this.props;

        const { eventos, espacos, responsaveis } = listar;

        // Listar Eventos

        // Listar Espaços

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
                                        <p>{moment(event.start).format('HH:mm')} - {moment(event.end).format('HH:mm')}</p>
                                    </td>
                                    <td>
                                        <i className="material-icons individual-icon">
                                            person
                                        </i>
                                        <p>{event.responsavel}</p>
                                    </td>
                                    <td>
                                        { event.responsavel === this.props.nomeUtilizadorCPDR &&
                                            <Link to="/pesquisautilizadorcpdr/editar" className="alert-danger">
                                                <i className="material-icons individual-icon">
                                                    edit
                                                </i>
                                            </Link>
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

                            {responsaveis && responsaveis.map(responsavel => (
                                <tr>
                                    <td>
                                        {responsavel}
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
