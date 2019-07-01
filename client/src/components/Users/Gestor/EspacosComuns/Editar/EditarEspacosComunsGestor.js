import React from 'react';

import './EditarEspacosComunsGestor.css';

class EditarEspacosComunsGestor extends React.Component {
    state = {
        nome: '',
        espacos: [],
        outrosEspacos: [],
        selecting: false
    };

    componentDidMount() {
        this.setState({
            nome: 'Piso 0',
            espacos: ['DI-0.01', 'DI-0.02', 'DI-0.03'],
            outrosEspacos: ['DI-1.01', 'DI-1.02', 'DI-1.03']
        });
    }

    handleClickAdd = () => {
        this.setState({
            selecting: true
        });
    };

    handleAddSpace = (espaco) => {
        const e = this.state.espacos;
        const oe = this.state.outrosEspacos;

        oe.splice(oe.indexOf(espaco), 1);
        e.push(espaco);

        this.setState({
            espacos: e,
            outrosEspacos: oe
        });

        // adicionar espaço ao espaço comum
    };

    handleRemoveSpace = (espaco) => {
        const e = this.state.espacos;
        const oe = this.state.outrosEspacos;

        e.splice(e.indexOf(espaco), 1);
        oe.push(espaco);

        this.setState({
            espacos: e,
            outrosEspacos: oe
        });

        // remover espaço do espaço comum
    };

    handleChange = (value) => {
        this.setState({
            nome: value,
        });
    };

    render() {
        return (
            <div>
                <h4 style={{marginBottom: '20px'}}>Espaços Comuns Gestor</h4>

                <div>
                    <div className="row" style={{marginBottom: '15px'}}>
                        <div className="col-md-2 container">
                            <p className="p-label" style={{paddingTop: '12px'}}>Nome</p>
                        </div>

                        <div className="col-md-10">
                            <input type="text" className="inputs" value={this.state.nome} name="nome"
                                   onChange={event => this.handleChange(event.target.value)} required
                                   style={{color: 'black', borderColor: 'black', border: '2px solid black', borderRadius: '0'}}
                            />
                        </div>
                    </div>

                    <div className="row" style={{marginBottom: '15px'}}>
                        <div className="col-md-2">
                            <p className="p-label" style={{paddingTop: '12px'}}>Espaços</p>
                        </div>

                        <div className="col-md-4" style={{marginLeft: '5px'}}>
                            <table className="table ec-table">
                                {this.state.espacos.map(espaco => (
                                    <tr>
                                        <td className="col-md-11">
                                            {espaco}
                                        </td>
                                        <td className="col-md-11">
                                            <a href="#" className="espacoComumGestorAdicionarEditar alert-danger"
                                               onClick={() => this.handleRemoveSpace(espaco)}>
                                                <i className="material-icons individual-icon">
                                                    indeterminate_check_box
                                                </i>
                                            </a>
                                        </td>
                                    </tr>
                                ))}
                            </table>

                            {!this.state.selecting &&
                                <a href="#" className="espacoComumGestorAdicionarEditar alert-success"
                                   onClick={this.handleClickAdd}>
                                    <i className="material-icons individual-icon">
                                        add_box
                                    </i>
                                </a>
                            }
                            {this.state.selecting &&
                                <select id="selecao" className="browser-default custom-select inputs" name="espaco"
                                        onChange={e => this.handleAddSpace(e.target.value)}>
                                    <option disabled selected>Selecionar espaço</option>

                                    {this.state.outrosEspacos.map(espaco => (
                                        <option name="repete">{espaco}</option>
                                    ))}
                                </select>
                            }
                        </div>
                    </div>

                    <div>
                        <button type="button" className="btn btn-danger" onClick={this.submeterPedido}>
                            Guardar Alterações
                        </button>
                    </div>
                </div>
            </div>
        );
    }
}

export default EditarEspacosComunsGestor;
