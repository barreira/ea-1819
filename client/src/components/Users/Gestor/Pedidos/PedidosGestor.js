import React from 'react';
import moment from "moment";

import './PedidosGestor.css';

class PedidosGestor extends React.Component {
    state = {
        pedidos: [{
            espaco: '',
            nome: '',
            inicio: '',
            fim: '',
            descricao: '',
            periodicidade: '',
            limite: ''
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
                },
                {
                    espaco: 'DI-0.02',
                    nome: 'Evento 2',
                    inicio: '11:00',
                    fim: '13:00',
                    descricao: 'Descrição do Evento 2',
                    periodicidade: 1,
                    limite: moment().add('7', 'days').format('YYYY-MM-DD'),
                },
                {
                    espaco: 'DI-0.03',
                    nome: 'Evento 3',
                    inicio: '14:00',
                    fim: '16:00',
                    descricao: 'Descrição do Evento 3',
                    periodicidade: 3,
                    limite: moment().add('9', 'days').format('YYYY-MM-DD'),
                },
            ]
        });
    }

    handleAccept = (pedidoIndex) => {
        const pedidosRemoved = this.state.pedidos.slice(0, pedidoIndex).concat(this.state.pedidos.slice(pedidoIndex + 1));

        this.setState({
            pedidos: pedidosRemoved
        });

        // TODO: Aceitar pedido
    };

    handleReject = (pedidoIndex) => {
        const pedidosRemoved = this.state.pedidos.slice(0, pedidoIndex).concat(this.state.pedidos.slice(pedidoIndex + 1));

        this.setState({
            pedidos: pedidosRemoved
        });

        // TODO: Rejeitar pedido
    };

    render() {
        return (
            <div>
                <h4>Pedidos Gestor</h4>

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
                            <th scope="col"/>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.pedidos.map((pedido, index) => (
                            <tr>
                                <td>{pedido.espaco}</td>
                                <td>{pedido.nome}</td>
                                <td>{pedido.inicio}</td>
                                <td>{pedido.fim}</td>
                                <td>{pedido.descricao}</td>
                                <td>{pedido.periodicidade}</td>
                                <td>{pedido.limite}</td>
                                <td>
                                    <a className="alert-success" onClick={() => this.handleAccept(index)}>
                                        <i className="material-icons individual-icon">
                                            check
                                        </i>
                                    </a>
                                    <a className="alert-danger" onClick={() => this.handleReject(index)}>
                                        <i className="material-icons individual-icon">
                                            close
                                        </i>
                                    </a>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        );
    }
}

export default PedidosGestor;
