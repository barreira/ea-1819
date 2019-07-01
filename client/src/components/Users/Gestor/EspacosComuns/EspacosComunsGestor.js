import React from 'react';

import './EspacosComunsGestor.css';

class EspacosComunsGestor extends React.Component {
    state = {
        espacosComuns: [{
            nome: '',
            espacos: []
        }]
    };

    componentDidMount() {
        this.setState({
            espacosComuns: [
                { nome: 'Piso 0', espacos: ['DI-0.01', 'DI-0.02', 'DI-0.03'] },
                { nome: 'Piso 1', espacos: ['DI-1.01', 'DI-1.02', 'DI-1.03'] }
            ],
        });
    }

    handleEdit = () => {
        // Editar espaço comum
    };

    render() {
        return (
            <div>
                <h4>Espaços Comuns Gestor</h4>

                {this.state.espacosComuns.map(espacoComum => (
                    <div className="espacoComumGestor">
                        <div className="espacoComumGestorHeader">
                            <h5 className="espacoComumGestorTitle">{espacoComum.nome}</h5>

                            <a href="#" className="espacoComumGestorEdit alert-danger" onClick={this.handleEdit}>
                                <i className="material-icons individual-icon">
                                    edit
                                </i>
                            </a>
                        </div>

                        <table className="table ec-table">
                            {espacoComum.espacos.map(espaco => (
                                <tr>
                                    <td>{espaco}</td>
                                </tr>
                            ))}
                        </table>
                    </div>
                ))}

            </div>
        );
    }
}

export default EspacosComunsGestor;
