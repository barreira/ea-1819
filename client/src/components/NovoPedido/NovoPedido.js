import React, { Component } from 'react';
import DatePicker, { registerLocale } from "react-datepicker";
import moment from 'moment';
import pt from 'date-fns/locale/pt';
import TimePicker from 'rc-time-picker';

import ApiEspacos from '../../api/ApiEspacos';

import "../../../node_modules/react-datepicker/dist/react-datepicker.css";
import "../../../node_modules/rc-time-picker/assets/index.css";
import './NovoPedido.css';
import ApiPedidos from '../../api/ApiPedidos';

registerLocale('pt', pt);

class NovoPedido extends Component {
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
                horaInicio: moment('00:00', 'hh:mm'),
                horaFim: moment('00:00', 'hh:mm'),
                descricao: '',
                periodicidade: 'Nunca',
                limite: moment().toDate()
            },
            repete: false
        }
    }

    async componentDidMount() {
        // TODO : fetch espacos

        const espacos = await ApiEspacos.fetchAll();

        console.log("ESPACOS NO CDM", espacos)

        this.setState({
            espacos,
            loading: false
        })
    }

    handleChange = (e, optionalName) => {

        let name;
        let value;

        if (!optionalName) {
            name = e.target.name;
            value = e.target.value;
        } else {
            name = optionalName;
            value = e;
        }


        this.setState({
            event: {
                ...this.state.event,
                [name]: value
            }
        })

        console.log([name], value)
        console.log("STATE", this.state)
    };

    submeterPedido = async () => {
        console.log("Submetendo pedido", this.state.event);

        const event = this.state.event;

        let periodicidade = 0;
        switch (event.periodicidade.toLowerCase()) {
            case 'Diariamente':
                periodicidade = 1;
                break;
            case 'Semanalmente':
                periodicidade = 7;
                break;
            case 'Mensalmente':
                periodicidade = 28;
                break;
            case 'Anualmente':
                periodicidade = 365;
                break;
        }

        const d = moment(event.data).format('YYYY-MM-DD');
        const dateTimeInicio = moment(`${d}T${moment(event.horaInicio).format('HH:mm:ss')}`).format('YYYY-MM-DDTHH:mm:ss')
        const dateTimeFim = moment(`${d}T${moment(event.horaFim).format('HH:mm:ss')}`).format('YYYY-MM-DDTHH:mm:ss')

        const newPedido = {
            "nome": event.nome,
            "descricao": event.descricao,
            "periodicidade": periodicidade,
            "dateTimeInicial": dateTimeInicio,
            "dateTimeFinal": dateTimeFim,
            "limite": moment(event.limite).format('YYYY-MM-DDTHH:mm:ss'),
            "espaco": {
                "id": event.espaco
            }

        }
        console.log("PEDIDO PARA SUBMISSAO", newPedido)
        try {


            const pedidoResponse = await ApiPedidos.novoPedido(newPedido)
            window.location.href = "/pedidosutilizadorcpdr";
            console.log("Pedido Response:", pedidoResponse);

        } catch (e) {

        }


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
                <h3 style={{ textAlign: 'center', padding: '10px 5px' }}>Novo Pedido</h3>
                <form onSubmit={this.submeterPedido}>
                    <div className="row" style={{ marginBottom: '15px' }}>
                        <div className="col-md-2 container">
                            <p className="p-label" style={{ paddingTop: '12px' }}>Nome</p>
                        </div>

                        <div className="col-md-10">
                            <input type="text" className="inputs" placeholder="Nome do evento" name="nome" onChange={this.handleChange} required />
                        </div>
                    </div>

                    <div className="row" style={{ marginBottom: '15px' }}>
                        <div className="col-md-2">
                            <p className="p-label" style={{ paddingTop: '12px' }}>Espaço</p>
                        </div>

                        <div className="col-md-10">
                            <select className="browser-default custom-select inputs" name="espaco" onChange={this.handleChange} required>
                                <option selected>Selecionar espaço</option>
                                {espacos.map(espaco => (
                                    <option value={espaco.id} name='repete' onSelect={this.handleChange}>{espaco.designacao}</option>
                                ))}
                            </select>
                        </div>
                    </div>

                    <div className="row" style={{ marginBottom: '15px' }}>
                        <div className="col-md-2">
                            <p className="p-label" style={{ paddingTop: '12px' }}>Data</p>
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

                    <div className="row" style={{ marginBottom: '15px' }}>
                        <div className="col-md-2">
                            <p className="p-label" style={{ paddingTop: '12px' }}>Hora de Início</p>
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

                    <div className="row" style={{ marginBottom: '15px' }}>
                        <div className="col-md-2">
                            <p className="p-label" style={{ paddingTop: '12px' }}>Hora de Fim</p>
                        </div>
                        <div className="col-md-10">
                            <TimePicker
                                style={{ width: 100, paddingLeft: '5px' }}
                                showSecond={false}
                                defaultValue={event.horaFim}
                                className="timepicker"
                                onChange={(e) => this.handleChange(e, 'horaFim')}
                            />
                        </div>
                    </div>

                    <div className="row" style={{ marginBottom: '20px' }}>
                        <div className="col-md-2">
                            <p className="p-label" style={{ paddingTop: '12px' }}>Repete</p>
                        </div>
                        <div className="col-md-10">
                            <div className="item" style={{ paddingLeft: '5px' }}>
                                <input type="radio" className="" name="periodicidade" onChange={this.handleChange}
                                    onClick={() => this.handleRepete('diariamente')
                                    } value="Diariamente" />
                                <label htmlFor="">Diariamente</label>
                            </div>

                            <div className="item" style={{ paddingLeft: '5px' }}>
                                <input type="radio" className="" name="periodicidade" onChange={this.handleChange}
                                    onClick={() => this.handleRepete('semanalmente')} value="Semanalmente" />
                                <label htmlFor="">Semanalmente</label>
                            </div>

                            <div className="item" style={{ paddingLeft: '5px' }}>
                                <input type="radio" className="" name="periodicidade" onChange={this.handleChange}
                                    onClick={() => this.handleRepete('mensalmente')} value="Mensalmente" />
                                <label htmlFor="">Mensalmente</label>
                            </div>

                            <div className="item" style={{ paddingLeft: '5px' }}>
                                <input type="radio" className="" name="periodicidade" onChange={this.handleChange}
                                    onClick={() => this.handleRepete('anualmente')} value="Anualmente" />
                                <label htmlFor="">Anualmente</label>
                            </div>

                            <div className="item" style={{ paddingLeft: '5px' }}>
                                <input type="radio" className="" name="periodicidade" onChange={this.handleChange}
                                    onClick={() => this.handleRepete('nunca')} value="Nunca" />
                                <label htmlFor="">Nunca</label>
                            </div>
                        </div>
                    </div>

                    {this.state.repete &&
                        <div className="row" style={{ marginBottom: '15px' }}>
                            <div className="col-md-2">
                                <p className="p-label" style={{ paddingTop: '12px' }}>Data Limite</p>
                            </div>

                            <div className="col-md-10">
                                <DatePicker
                                    selected={event.limite}
                                    onChange={(e) => this.handleChange(e, 'limite')}
                                    locale={pt}
                                    dateFormat="dd/MM/yyyy"
                                />
                            </div>
                        </div>}

                    <div className="row">
                        <div className="col-md-2">
                            <p className="p-label" style={{ paddingTop: '10px' }}>Descrição</p>
                        </div>
                        <div className="col-md-10">
                            <textarea name="descricao" className="w-100"
                                value={event.descricao} onChange={this.handleChange}
                                style={{ padding: '10px', height: '100px' }}
                                placeholder="Descrição do evento" id="" rows="8"
                                required />
                        </div>
                    </div>

                    <div>
                        <button type="button" className="btn" onClick={this.submeterPedido}>Enviar</button>
                    </div>
                </form>
            </div>
        );
    }
}

export default NovoPedido;
