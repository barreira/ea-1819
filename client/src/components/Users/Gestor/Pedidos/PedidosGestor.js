import React from 'react';
import moment from "moment";

import './PedidosGestor.css';
import ApiPedidos from '../../../../api/ApiPedidos';

class PedidosGestor extends React.Component {
    state = {
        pedidosPendentes: [],
        pedidosAtendidos: []
    };

    async componentDidMount() {

        let pedidosFinais = [];

        let pedidosPendentes = [];
        let pedidosAtendidos = [];

        const pedidos = await ApiPedidos.consultarPedidosPendentesPeloGestor();

        pedidos.forEach(p => {
            if (p.atendido) {
                pedidosAtendidos.push(p);
            } else {
                pedidosPendentes.push(p);
            }
        })

        console.log(pedidosPendentes)
        console.log(pedidosAtendidos)

        this.setState({
            pedidosPendentes: pedidosPendentes,
            pedidosAtendidos: pedidosAtendidos,
            loading: false
        })


        // this.setState({
        //     pedidos: [
        //         {
        //             espaco: 'DI-0.01',
        //             nome: 'Evento 1',
        //             inicio: '09:00',
        //             fim: '11:00',
        //             descricao: 'Descrição do Evento 1',
        //             periodicidade: 7,
        //             limite: moment().add('14', 'days').format('YYYY-MM-DD'),
        //         },
        //         {
        //             espaco: 'DI-0.02',
        //             nome: 'Evento 2',
        //             inicio: '11:00',
        //             fim: '13:00',
        //             descricao: 'Descrição do Evento 2',
        //             periodicidade: 1,
        //             limite: moment().add('7', 'days').format('YYYY-MM-DD'),
        //         },
        //         {
        //             espaco: 'DI-0.03',
        //             nome: 'Evento 3',
        //             inicio: '14:00',
        //             fim: '16:00',
        //             descricao: 'Descrição do Evento 3',
        //             periodicidade: 3,
        //             limite: moment().add('9', 'days').format('YYYY-MM-DD'),
        //         },
        //     ]
        // });
    }

    handleAccept = async (pedidoIndex) => {

        await ApiPedidos.aceitarPedido(pedidoIndex);

        const pedidosRemoved = this.state.pedidosPendentes.filter(p => p.id != pedidoIndex)

        this.setState({
            pedidosPendentes: pedidosRemoved
        });

        // TODO: Aceitar pedido
    };

    handleReject = async (pedidoIndex) => {
        await ApiPedidos.rejeitarPedido(pedidoIndex);
        const pedidosRemoved = this.state.pedidosPendentes.filter(p => p.id != pedidoIndex)
        this.setState({
            pedidosPendentes: pedidosRemoved
        });

        // TODO: Rejeitar pedido
    };

    render() {

        if (this.state.loading)
            return <div></div>


        const pedidosPendentesSection = (
            <div>
                <h4>Pedidos Pendentes</h4>
                <table className="table ec-table">
                    <thead>
                        <tr>
                            <th scope="col">Espaço</th>
                            <th scope="col">Nome</th>
                            <th scope="col">Data</th>
                            <th scope="col">Inicio</th>
                            <th scope="col">Fim</th>
                            <th scope="col">Periodicidade</th>
                            <th scope="col">Data limite</th>
                            <th scope="col" />
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.pedidosPendentes.map((pedido, index) => (
                            <tr>
                                <td>{pedido.espaco.designacao}</td>
                                <td>{pedido.nome}</td>
                                <td>{moment(pedido.dateTimeInicial).format('DD-MM-YYYY')}</td>
                                <td>{moment(pedido.dateTimeInicial).format('HH:mm')}</td>
                                <td>{moment(pedido.dateTimeFinal).format('HH:mm')}</td>
                                <td>{pedido.periodicidade}</td>
                                <td>{moment(pedido.limite).format('DD-MM-YYYY')}</td>
                                <td>
                                    <a className="alert-success" style={{ cursor: 'pointer' }} onClick={() => this.handleAccept(pedido.id)}>
                                        <i className="material-icons individual-icon">
                                            check
                                        </i>
                                    </a>
                                    <a className="alert-danger" style={{ cursor: 'pointer' }} onClick={() => this.handleReject(pedido.id)}>
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
        )

        const pedidosAtendidosSection = (
            <div>
                <h4>Pedidos Atendidos</h4>
                <table className="table ec-table">
                    <thead>
                        <tr>
                            <th scope="col">Espaço</th>
                            <th scope="col">Nome</th>
                            <th scope="col">Data</th>
                            <th scope="col">Inicio</th>
                            <th scope="col">Fim</th>
                            <th scope="col">Periodicidade</th>
                            <th scope="col">Data limite</th>
                            <th scope="col" />
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.pedidosAtendidos.map((pedido, index) => (
                            <tr>
                                <td>{pedido.espaco.designacao}</td>
                                <td>{pedido.nome}</td>
                                <td>{moment(pedido.dateTimeInicial).format('DD-MM-YYYY')}</td>
                                <td>{moment(pedido.dateTimeInicial).format('HH:mm')}</td>
                                <td>{moment(pedido.dateTimeFinal).format('HH:mm')}</td>
                                <td>{pedido.periodicidade}</td>
                                <td>{moment(pedido.limite).format('DD-MM-YYYY')}</td>
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
        )

        return (
            <div>
                <h3>Gestão de Pedidos</h3>
                {this.state.pedidosAtendidos.length > 0 && pedidosAtendidosSection}
                {this.state.pedidosPendentes.length > 0 && pedidosPendentesSection}
                {this.state.pedidosAtendidos.length === 0 && this.state.pedidosPendentes.length === 0 ?
                    <p>Não existem quaisquer pedidos para tratamento de momento!</p> : ''}

            </div>
        );
    }
}

export default PedidosGestor;
