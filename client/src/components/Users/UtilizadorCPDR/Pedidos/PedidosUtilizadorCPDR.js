import React from 'react';
import moment from "moment";
import { Redirect, Link } from 'react-router-dom';
import ApiPedidos from '../../../../api/ApiPedidos';

import './PedidosUtilizadorCPDR.css';
import ApiEspacos from '../../../../api/ApiEspacos';
import ApiEventos from '../../../../api/ApiEventos';

class PedidosUtilizadorCPDR extends React.Component {
    state = {
        pedidosAtendidos: [],
        pedidosPendentes: [],
        pedidos: [{
            espaco: '',
            nome: '',
            inicio: '',
            fim: '',
            descricao: '',
            periodicidade: '',
            limite: '',
            estado: '' // aceite, em processamento, rejeitado
        }],
        loading: true
    };

    async componentDidMount() {

        let pedidosAtendidos = await ApiPedidos.fetchPedidosAtendidos();
        let pedidosPendentes = await ApiPedidos.fetchPedidosPendentes();

        pedidosAtendidos = this.formatarPedidos(pedidosAtendidos);
        pedidosPendentes = this.formatarPedidos(pedidosPendentes);

        console.log(`Pedidos atendidos ${pedidosAtendidos}`)
        console.log(`Pedidos pendentes`, pedidosPendentes)

        this.setState({
            pedidosAtendidos: pedidosAtendidos,
            pedidosPendentes: pedidosPendentes,
            loading: false
        });
    }

    formatarPedidos = (pedidos) => {

        if (!pedidos)
            return;

        let pedidosFinais = [];
        pedidos.forEach(pedido => {
            const pedidoFormatado = {
                "id": pedido.id,
                "nome": pedido.nome,
                "espaco": {
                    "id": pedido.espaco.id,
                    "designacao": pedido.espaco.designacao
                },
                "inicio": pedido.dateTimeInicial,
                "fim": pedido.dateTimeFinal,
                "descricao": pedido.descricao,
                "periodicidade": pedido.periodicidade,
                "limite": moment(pedido.limite).format("YYYY-MM-DD"),
                "estado": pedido.aceite,
                "atendido": pedido.atendido,
                "aceite": pedido.aceite
            }

            pedidosFinais.push(pedidoFormatado);
        })

        return pedidosFinais;
    }

    cancelarPedido = async (idPedido) => {
        await ApiPedidos.cancelarPedido(idPedido);
        let pedidosPendentes = this.state.pedidosPendentes.filter(pedido => pedido.id !== idPedido);
        this.setState({
            pedidosPendentes
        })
    }

    render() {


        if (this.state.loading) {
            return <div></div>
        }


        const pedidosPendentesSection = (
            <div>
                <h4 style={{ float: 'left' }}>Pedidos Pendentes</h4>
                <table className="table ec-table">
                    <thead>
                        <tr>
                            <th scope="col">Espaço</th>
                            <th scope="col">Nome</th>
                            <th scope="col">Inicio</th>
                            <th scope="col">Fim</th>
                            {/* <th scope="col">Descrição</th> */}
                            <th scope="col">Periodicidade</th>
                            <th scope="col">Data limite</th>
                            <th scope="col">Estado</th>
                            <th scope="col">Cancelar</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.pedidosPendentes.map(pedido => (
                            <tr>
                                <td>{pedido.espaco.designacao}</td>
                                <td>{pedido.nome}</td>
                                <td>{moment(pedido.inicio).format('HH:mm DD-MM-YYYY')}</td>
                                <td>{moment(pedido.fim).format('HH:mm DD-MM-YYYY')}</td>
                                {/* <td>{pedido.descricao}</td> */}
                                <td>{pedido.periodicidade}</td>
                                <td>{pedido.limite}</td>
                                <td>
                                    {pedido.estado &&
                                        <i className="material-icons individual-icon pucpdrIconAceite">
                                            check_circle
                                        </i>
                                    }
                                    {!pedido.estado &&
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
                                <td>
                                    <i className="material-icons individual-icon pucpdrIconRejeitado" style={{ cursor: 'pointer' }} onClick={() => this.cancelarPedido(pedido.id)}>
                                        cancel
                                        </i>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        )

        const pedidosAtendidosSections = (
            <div>
                <h4 style={{ float: 'left' }}>Pedidos Processados</h4>
                <table className="table ec-table">
                    <thead>
                        <tr>
                            <th scope="col">Espaço</th>
                            <th scope="col">Nome</th>
                            <th scope="col">Inicio</th>
                            <th scope="col">Fim</th>
                            {/* <th scope="col">Descrição</th> */}
                            <th scope="col">Periodicidade</th>
                            <th scope="col">Data limite</th>
                            <th scope="col">Resposta</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.pedidosAtendidos.map(pedido => (
                            <tr>
                                <td>{pedido.espaco.designacao}</td>
                                <td>{pedido.nome}</td>
                                <td>{moment(pedido.inicio).format('HH:mm DD-MM-YYYY')}</td>
                                <td>{moment(pedido.fim).format('HH:mm DD-MM-YYYY')}</td>
                                {/* <td>{pedido.descricao}</td> */}
                                <td>{pedido.periodicidade}</td>
                                <td>{pedido.limite}</td>
                                <td>
                                    {pedido.aceite &&
                                        <i className="material-icons individual-icon pucpdrIconAceite">
                                            check_circle
                                        </i>
                                    }
                                    {/* {!pedido.estado &&
                                        <i className="material-icons individual-icon pucpdrIconEmProcessamento">
                                            timelapse
                                        </i>
                                    } */}
                                    {!pedido.aceite &&
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
        )

        return (
            <div>
                <h3>Gestão de Pedidos</h3>

                <Link to={"/pedido/novo"}>
                    <span className="nav-link btn btn-filter" style={{ float: 'right', margin: '15px' }} >Novo Pedido</span>
                </Link>


                {this.state.pedidosPendentes.length > 0 && pedidosPendentesSection}
                {this.state.pedidosAtendidos.length > 0 && pedidosAtendidosSections}

                {this.state.pedidosAtendidos.length == 0 && this.state.pedidosPendentes == 0 ?
                    <div>
                        <h4>Ainda não realizou qualquer pedido de reserva de espaço.</h4>
                        <p>
                            Faça-o clicando no botão "Novo Pedido"</p>
                    </div> : ''}

            </div>
        );
    }
}

export default PedidosUtilizadorCPDR;
