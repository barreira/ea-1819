import React, { Component } from 'react';
import DatePicker, { registerLocale } from "react-datepicker";
import moment from 'moment';
import pt from 'date-fns/locale/pt';
import TimePicker from 'rc-time-picker';

import "../../../node_modules/react-datepicker/dist/react-datepicker.css";
import "../../../node_modules/rc-time-picker/assets/index.css";
import './CriarEvento.css';

registerLocale('pt', pt);

class CriarEvento extends Component {
    constructor(props) {
        super(props);
        this.state = {
            startDate: new Date(),
            espacos: [],
            loading: true,
            event: {
                nome: '',
                espaco: '',
                data: moment().toDate(),
                repete: '',
                dataLimite: moment().add('7', 'day').toDate(),
                horaInicio: moment('00:00', 'hh:mm'),
                horaFim: moment('00:00', 'hh:mm'),
                descricao: ''
            },
            repete: false
        }
    }

    componentDidMount() {
        // TODO : fetch espacos
        this.setState({
            espacos: ['DI-01', 'DI-02', 'DI-03', 'DI-04'],
            loading: false
        })
    }

    handleChange = (e, optionalName) => {
        console.log(e, optionalName);

        let name;
        let value;

        if (!optionalName) {
            name = e.target.name;
            value = e.target.value;
        } else {
            name = optionalName;
            value = e;
        }

        console.log(name, value);

        this.setState({
            event: {
                ...this.state.event,
                [name]: value
            }
        })
    };

    submeterPedido = () => {
        console.log("Submetendo pedido");

        // TODO : implementar a realização do pedido
        console.log(this.state.event)
    };

    handleRepete = (periodicidade) => {
        if (periodicidade === 'nunca') {
            this.setState({
                repete: false
            });
        }
        else {
            this.setState({
                repete: true
            });
        }
    };

    render() {
        const { espacos, event } = this.state;

        return (
            <div className="criar-evento">
                <h3 style={{ textAlign: 'center', padding: '10px 5px' }}>Criar Evento</h3>
                <form onSubmit={this.submeterPedido}>
                    <div className="row" style={{marginBottom: '15px'}}>
                        <div className="col-md-2 container">
                            <p className="p-label" style={{paddingTop: '12px'}}>Nome</p>
                        </div>

                        <div className="col-md-10">
                            <input type="text" className="inputs" placeholder="Nome do evento" name="nome" onChange={this.handleChange} required />
                        </div>
                    </div>

                    <div className="row" style={{marginBottom: '15px'}}>
                        <div className="col-md-2">
                            <p className="p-label" style={{paddingTop: '12px'}}>Espaço</p>
                        </div>

                        <div className="col-md-10">
                            <select className="browser-default custom-select inputs" name="espaco" onChange={this.handleChange} required>
                                <option selected>Selecionar espaço</option>
                                {espacos.map(espaco => (
                                    <option value={espaco} name='repete' onSelect={this.handleChange}>{espaco}</option>
                                ))}
                            </select>
                        </div>
                    </div>

                    <div className="row" style={{marginBottom: '15px'}}>
                        <div className="col-md-2">
                            <p className="p-label" style={{paddingTop: '12px'}}>Data</p>
                        </div>

                        <div className="col-md-10">
                            <DatePicker
                                selected={event.data}
                                onChange={(e) => this.handleChange(e, 'data')}
                                locale={pt}
                                dateFormat="dd/MM/yyyy"
                            />
                        </div>
                    </div>

                    <div className="row" style={{marginBottom: '15px'}}>
                        <div className="col-md-2">
                            <p className="p-label" style={{paddingTop: '12px'}}>Hora de Início</p>
                        </div>
                        <div className="col-md-10">
                            <TimePicker
                                style={{ width: 100, paddingLeft: '5px' }}
                                showSecond={false}
                                defaultValue={event.horaInicio}
                                className="timepicker"
                                onChange={(e) => this.handleChange(e, 'horaInicio')}
                            />
                        </div>
                    </div>

                    <div className="row" style={{marginBottom: '15px'}}>
                        <div className="col-md-2">
                            <p className="p-label" style={{paddingTop: '12px'}}>Hora de Fim</p>
                        </div>
                        <div className="col-md-10">
                            <TimePicker
                                style={{ width: 100, paddingLeft: '5px'}}
                                showSecond={false}
                                defaultValue={event.horaFim}
                                className="timepicker"
                                onChange={(e) => this.handleChange(e, 'horaFim')}
                            />
                        </div>
                    </div>

                    <div className="row" style={{marginBottom: '20px'}}>
                        <div className="col-md-2">
                            <p className="p-label" style={{paddingTop: '12px'}}>Repete</p>
                        </div>
                        <div className="col-md-10">
                            <div className="item" style={{paddingLeft: '5px'}}>
                                <input type="radio" className="" name="repete" onChange={this.handleChange}
                                       onClick={() => this.handleRepete('diariamente')} value="Diariamente" />
                                <label htmlFor="">Diariamente</label>
                            </div>

                            <div className="item" style={{paddingLeft: '5px'}}>
                                <input type="radio" className="" name="repete" onChange={this.handleChange}
                                       onClick={() => this.handleRepete('semanalmente')} value="Semanalmente" />
                                <label htmlFor="">Semanalmente</label>
                            </div>

                            <div className="item" style={{paddingLeft: '5px'}}>
                                <input type="radio" className="" name="repete" onChange={this.handleChange}
                                       onClick={() => this.handleRepete('mensalmente')} value="Mensalmente" />
                                <label htmlFor="">Mensalmente</label>
                            </div>

                            <div className="item" style={{paddingLeft: '5px'}}>
                                <input type="radio" className="" name="repete" onChange={this.handleChange}
                                       onClick={() => this.handleRepete('anualmente')} value="Anualmente" />
                                <label htmlFor="">Anualmente</label>
                            </div>

                            <div className="item" style={{paddingLeft: '5px'}}>
                                <input type="radio" className="" name="repete"
                                       onClick={() => this.handleRepete('nunca')} value="Anualmente" />
                                <label htmlFor="">Nunca</label>
                            </div>
                        </div>
                    </div>

                    {this.state.repete &&
                        <div className="row" style={{marginBottom: '15px'}}>
                            <div className="col-md-2">
                                <p className="p-label" style={{paddingTop: '12px'}}>Data Limite</p>
                            </div>

                            <div className="col-md-10">
                                <DatePicker
                                    selected={event.dataLimite}
                                    onChange={(e) => this.handleChange(e, 'dataLimite')}
                                    locale={pt}
                                    dateFormat="dd/MM/yyyy"
                                />
                            </div>
                        </div>}

                    <div className="row">
                        <div className="col-md-2">
                            <p className="p-label" style={{paddingTop: '10px'}}>Descrição</p>
                        </div>
                        <div className="col-md-10">
                            <textarea name="descricao" className="w-100"
                                      value={event.descricao} onChange={this.handleChange}
                                      style={{ padding: '10px', height: '100px' }}
                                      placeholder="Descrição do evento" id="" rows="8"
                                      required/>
                        </div>
                    </div>

                    <div>
                        <button className="btn" onClick={this.submeterPedido}>Enviar</button>
                    </div>
                </form>
            </div>
        );
    }
}

export default CriarEvento;
