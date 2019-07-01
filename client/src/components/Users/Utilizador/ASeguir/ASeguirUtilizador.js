import React from 'react';
import moment from "moment";

class ASeguirUtilizador extends React.Component {
    state = {
        eventosASeguir: [{
            nome: '',
            inicio: '',
            fim: '',
            descricao: '',
            responsavel: '',
            espaco: '',
            aSeguir: true
        }]
    };

    componentDidMount() {
        this.setState({
            eventosASeguir: [
                {
                    nome: 'Evento 1',
                    inicio: moment().add('1', 'days').format('YYYY-MM-DD'),
                    fim: moment().add('7', 'days').format('YYYY-MM-DD'),
                    descricao: 'Descrição do Evento 1',
                    responsavel: 'Responsável 1',
                    espaco: 'DI-0.01'
                },
                {
                    nome: 'Evento 2',
                    inicio: moment().add('1', 'days').format('YYYY-MM-DD'),
                    fim: moment().add('7', 'days').format('YYYY-MM-DD'),
                    descricao: 'Descrição do Evento 2',
                    responsavel: 'Responsável 2',
                    espaco: 'DI-0.02'
                },
                {
                    nome: 'Evento 3',
                    inicio: moment().add('1', 'days').format('YYYY-MM-DD'),
                    fim: moment().add('7', 'days').format('YYYY-MM-DD'),
                    descricao: 'Descrição do Evento 3',
                    responsavel: 'Responsável 3',
                    espaco: 'DI-0.03'
                },
            ]
        });
    }

    handleUnfollow = (nomeEvento) => {
        const filtered = this.state.eventosASeguir.filter(evento => evento.nome !== nomeEvento);

        this.setState({
            eventosASeguir: filtered
        });

        // TODO: integrar unfollow do evento
    };

    render() {
        return (
            <div>
                <h4>A Seguir Utilizador</h4>

                {this.state.eventosASeguir.length >= 1 &&
                    <table className="table ec-table">
                        <thead>
                        <tr>
                            <th scope="col">Espaço</th>
                            <th scope="col">Nome</th>
                            <th scope="col">Início</th>
                            <th scope="col">Fim</th>
                            <th scope="col">Descrição</th>
                            <th scope="col">Responsável</th>
                            <th scope="col"/>

                        </tr>
                        </thead>
                        <tbody>
                        {this.state.eventosASeguir.map(evento => (
                            <tr>
                                <td>{evento.espaco}</td>
                                <td>{evento.nome}</td>
                                <td>{evento.inicio}</td>
                                <td>{evento.fim}</td>
                                <td>{evento.descricao}</td>
                                <td>{evento.responsavel}</td>
                                <td>
                                    <a className="alert-warning" onClick={() => this.handleUnfollow(evento.nome)}>
                                        <i className="material-icons individual-icon pucpdrIconAceite">
                                            star
                                        </i>
                                    </a>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                }
                {this.state.eventosASeguir.length < 1 &&
                    <p style={{textAlign: 'center'}}>Não se encontra a seguir nenhum evento</p>
                }
            </div>
        );
    }
}

export default ASeguirUtilizador;
