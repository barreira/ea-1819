import React, { Component } from 'react';
import DatePicker, { registerLocale, setDefaultLocale } from "react-datepicker";
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
                horaInicio: moment('00:00', 'hh:mm'),
                horaFim: moment('00:00', 'hh:mm'),
                descricao: ''
            }
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

        console.log(e, optionalName)

        let name;
        let value;

        if (!optionalName) {
            name = e.target.name;
            value = e.target.value;
        } else {
            name = optionalName;
            value = e;
        }

        console.log(name, value)

        this.setState({
            event: {
                ...this.state.event,
                [name]: value
            }
        })
    }



    submeterPedido = () => {
        console.log("Submetendo pedido")

        // TODO : implementar a realização do pedido
        console.log(this.state.event)
    }

    render() {

        const { espacos, event } = this.state;

        return (
            <div className="criar-evento">
                <h3 style={{ textAlign: 'center', padding: '10px 5px' }}>Criar Evento</h3>
                <form onSubmit={this.submeterPedido}>
                    <div className="row">
                        <div className="col-md-2">
                            <p className="p-label">Nome</p>
                        </div>

                        <div className="col-md-10">
                            <input type="text" className="inputs" placeholder="Nome do evento" name="nome" onChange={this.handleChange} required />
                        </div>
                    </div>

                    <div className="row">
                        <div className="col-md-2">
                            <p className="p-label">Espaço</p>
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

                    <div className="row">
                        <div className="col-md-2">
                            <p className="p-label">Data</p>
                        </div>

                        <div className="col-md-6">
                            <DatePicker
                                selected={event.data}
                                onChange={(e) => this.handleChange(e, 'data')}
                                locale={pt}
                                dateFormat="dd/MM/yyyy"
                            />
                        </div>

                        <div className="col-md-4">

                            <p className="p-label">Repete</p>
                            <div>
                                <div className="item">
                                    <input type="radio" className="" name="repete" onChange={this.handleChange} value="Diariamente" />
                                    <label htmlFor="">Diariamente</label>
                                </div>

                                <div className="item">
                                    <input type="radio" className="" name="repete" onChange={this.handleChange} value="Semanalmente" />
                                    <label htmlFor="">Semanalmente</label>
                                </div>

                                <div className="item">
                                    <input type="radio" className="" name="repete" onChange={this.handleChange} value="Mensalmente" />
                                    <label htmlFor="">Mensalmente</label>
                                </div>

                                <div className="item">
                                    <input type="radio" className="" name="repete" onChange={this.handleChange} value="Anualmente" />
                                    <label htmlFor="">Anualmente</label>
                                </div>
                            </div>
                        </div>


                    </div>
                    <div className="row">
                        <div className="col-md-2">
                            <p className="p-label">Hora de Início</p>
                            <TimePicker
                                style={{ width: 100 }}
                                showSecond={false}
                                defaultValue={event.horaInicio}
                                className="timepicker"
                                onChange={(e) => this.handleChange(e, 'horaInicio')}
                            />
                        </div>

                        <div className="col-md-2">
                            <p className="p-label">Hora de Fim</p>
                            <TimePicker
                                style={{ width: 100 }}
                                showSecond={false}
                                defaultValue={event.horaFim}
                                className="timepicker"
                                onChange={(e) => this.handleChange(e, 'horaFim')}
                            />
                        </div>

                    </div>

                    <div className="row" style={{ marginTop: '25px' }}>
                        <div className="col-md-2">
                            <p className="p-label">Descrição</p>
                        </div>
                        <div className="col-md-10">
                            <textarea name="descricao" maxlength="500" className="w-100"
                                value={event.descricao} onChange={this.handleChange}
                                style={{ padding: '10px' }}
                                placeholder="Descrição do evento" id="" rows="8"
                                required></textarea>
                        </div>
                    </div>

                    <div>
                        <button className="btn" onClick={this.submeterPedido}>Submeter pedido</button>
                    </div>
                </form>
            </div>
        );
    }
}

export default CriarEvento;