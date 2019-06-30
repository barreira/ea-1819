import React from 'react';
import moment from "moment";

import './PedidosUtilizadorCPDR.css';

class PedidosUtilizadorCPDR extends React.Component {
    state = {
        pedidos: [{
            espaco: '',
            nome: '',
            inicio: '',
            fim: '',
            descricao: '',
            periodicidade: '',
            limite: '',
            estado: '' // aceite, em processamento, rejeitado
        }]
    };

    componentDidMount() {
        this.setState({
            pedidos: [
                {
                    espaco: 'DI-0.01',
                    nome: 'Evento 1',
                    inicio: '09:00',
                    fim: '11:00',
                    descricao: 'Descrição do Evento 1',
                    periodicidade: 7,
                    limite: moment().add('14', 'days').format('YYYY-MM-DD'),
                    estado: 'aceite'
                },
                {
                    espaco: 'DI-0.02',
                    nome: 'Evento 2',
                    inicio: '11:00',
                    fim: '13:00',
                    descricao: 'Descrição do Evento 2',
                    periodicidade: 1,
                    limite: moment().add('7', 'days').format('YYYY-MM-DD'),
                    estado: 'emProcessamento'
                },
                {
                    espaco: 'DI-0.03',
                    nome: 'Evento 3',
                    inicio: '14:00',
                    fim: '16:00',
                    descricao: 'Descrição do Evento 3',
                    periodicidade: 3,
                    limite: moment().add('9', 'days').format('YYYY-MM-DD'),
                    estado: 'rejeitado'
                },
            ]
        });
    }

    render() {
        return (
            <div>
                <h4>Pedidos UtilizadorCPDR</h4>

                <table className="table ec-table">
                    <thead>
                        <tr>
                            <th scope="col">Espaço</th>
                            <th scope="col">Nome</th>
                            <th scope="col">Inicio</th>
                            <th scope="col">Fim</th>
                            <th scope="col">Descrição</th>
                            <th scope="col">Periodicidade</th>
                            <th scope="col">Data limite</th>
                            <th scope="col">Estado</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.pedidos.map(pedido => (
                            <tr>
                                <td>{pedido.espaco}</td>
                                <td>{pedido.nome}</td>
                                <td>{pedido.inicio}</td>
                                <td>{pedido.fim}</td>
                                <td>{pedido.descricao}</td>
                                <td>{pedido.periodicidade}</td>
                                <td>{pedido.limite}</td>
                                <td>
                                    {pedido.estado === 'aceite' &&
                                        <i className="material-icons individual-icon pucpdrIconAceite">
                                            check_circle
                                        </i>
                                    }
                                    {pedido.estado === 'emProcessamento' &&
                                        <i className="material-icons individual-icon pucpdrIconEmProcessamento">
                                            timelapse
                                        </i>
                                    }
                                    {pedido.estado === 'rejeitado' &&
                                        <i className="material-icons individual-icon pucpdrIconRejeitado">
                                            cancel
                                        </i>
                                    }
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        );
    }
}

export default PedidosUtilizadorCPDR;
