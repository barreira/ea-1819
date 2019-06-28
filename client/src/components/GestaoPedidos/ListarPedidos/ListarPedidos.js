import React, { Component } from 'react';
import moment from 'moment';

import './ListarPedidos.css'

const estadoClassName = (estado, processed) => {

    console.log("ESTADO", estado)
    if (estado && processed) {
        if (estado === 'Aceite')
            return 'green';
        else if (estado === 'Recusado')
            return 'red';
    }
    return '';
}

const cancelButton = (estado, processed) => {

    const cancel = <i className="material-icons individual-icon" style={{ color: 'red', cursor: 'pointer', float: 'right' }}> cancel </i>

    if (processed) {
        if (estado === 'Aceite') {
            return cancel;
        } else {
            return '';
        }
    }

    return cancel;
}

const validarPedido = (pedido) => {

}

const ListarPedidos = (props) => {

    const { pedidos, processed } = props;

    return (

        <table className="table ec-table">
            <tbody>
                {pedidos.map(pedido => (

                    <tr className={estadoClassName(pedido.estado, processed)}>
                        <td>
                            <p>{pedido.nome}</p>

                        </td>
                        <td>
                            <i className="material-icons individual-icon" >
                                location_on
                                          </i>
                            <p>{pedido.local}</p>
                        </td>
                        <td>
                            <i className="material-icons individual-icon" >
                                calendar_today
                                          </i>
                            <p>{moment(pedido.horaInicio, 'HH:mm').format('HH:mm')} - {moment(pedido.horaFim, 'HH:mm').format('HH:mm')}</p>
                        </td>
                        <td>
                            <i className="material-icons individual-icon" >
                                person
                                          </i>
                            <p>{pedido.responsavel}</p>
                        </td>

                        <td>
                            {cancelButton(pedido.estado, processed)}
                            {/* <i className="material-icons individual-icon" style={{ color: 'red', cursor: 'pointer', float: 'right' }}> cancel </i> */}
                            {!processed ? <i className="material-icons individual-icon" style={{ color: 'green', cursor: 'pointer', float: 'right' }}> check_circle </i> : ''}
                        </td>
                    </tr>
                ))}
            </tbody>
        </table>

    );
}

export default ListarPedidos;