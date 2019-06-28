import React, { Component } from 'react';
import moment from 'moment';
import ListarPedidos from './ListarPedidos/ListarPedidos';

class GestaoPedidos extends Component {
    constructor(props) {
        super(props);
        this.state = { pedidos: [], pedidosAProcessar: [], pedidosProcessados: [], loading: true }
    }

    componentDidMount() {

        const pedidos = [
            {
                nome: 'Programação',
                horaInicio: '09:00',
                horaFim: '12:00',
                local: 'DI-01',
                responsavel: 'João Carlos',
                estado: 'A processar'
            },
            {
                nome: 'Engenharia Web',
                horaInicio: '09:00',
                horaFim: '12:00',
                local: 'DI-01',
                responsavel: 'João Carlos',
                estado: 'Recusado'
            },
            {
                nome: 'Engenharia de Requisitos',
                horaInicio: '10:00',
                horaFim: '13:00',
                local: 'DI-01',
                responsavel: 'Maria Raquel',
                estado: 'Aceite'
            }
        ]

        let pedidosAProcessar = [];
        let pedidosProcessados = [];

        pedidos.forEach(pedido => {
            if (['Aceite', 'Recusado'].includes(pedido.estado)) {
                pedidosProcessados.push(pedido)
            } else {
                pedidosAProcessar.push(pedido);
            }
        })

        this.setState({
            pedidos, pedidosAProcessar, pedidosProcessados, loading: false
        })
    }

    alterarEstadoPedido = (pedidoAlterar) => {

        // TODO : eventos com o mesmo nome? é possivel? TERMINAR

        const pedidos = this.state.pedidos;

        let pedidosFiltrados = pedidos.filter(pedido => pedido.nome === pedidoAlterar.nome);

        pedidosFiltrados[0].estado = 'Recusado';

        this.setState({

        })

    }

    render() {

        let { pedidos, pedidosAProcessar, pedidosProcessados, loading } = this.state;

        if (loading)
            return <div></div>

        return (
            <div>
                <h3>Gestão de Pedidos de Requisição</h3>

                <div>
                    <p>A processar</p>
                    <ListarPedidos pedidos={pedidosAProcessar} processed={false} />

                    <p>Respondidos</p>
                    <ListarPedidos pedidos={pedidosProcessados} processed={true} />
                </div>

            </div>
        );
    }
}

export default GestaoPedidos;