import React, { Component } from 'react';
import FiltroPesquisa from './FiltroPesquisa/FIltroPesquisa';
import ListarElementosPesquisa from './ListarElementosPesquisa/ListarElementosPesquisa';
import ApiEventos from '../../../../api/ApiEventos';
import moment from 'moment';

class PesquisaUtilizador extends Component {
    constructor(props) {
        super(props);
        this.state = {
            eventos: [],
            espacos: [],
            aSeguir: [],
            listar: {},
            filteredListar: {},
            activeFilters: [],
            inputFilter: '',
            loading: true
        };
    }

    async componentDidMount() {

        const eventos = await ApiEventos.fetchEventos(moment().format('YYYY-MM-DD'), moment().add('15', 'days').format('YYYY-MM-DD'));

        let finalEvents = [];
        let finalEspacos = [];

        for (let day in eventos) {
            const currDayEvents = eventos[day];
            if (Array.isArray(currDayEvents)) {
                currDayEvents.forEach(evento => {

                    const espaco = evento.espaco.designacao;
                    finalEvents.push({
                        "nome": evento.nome,
                        "data": day,
                        "local": espaco,
                        "horaInicio": evento.localDate,
                        "horaInicio": evento.dateTimeInicial,
                        "horaFim": evento.dateTimeFinal,
                        "responsavel": evento.utilizadorResponsavel.nome,
                    })

                    finalEspacos.push(espaco)
                })
            }
        }

        finalEspacos = finalEspacos.slice(0, 40);
        finalEvents = finalEvents.slice(0, 40);

        this.setState({
            eventos: finalEvents,
            espacos: [... new Set(finalEspacos)],
            loading: false
        });
    }

    setActiveFilters = (activeFilters) => {
        let listar = {};

        activeFilters.forEach(filter => {
            switch (filter) {
                case 'Eventos':
                    listar.eventos = this.state.eventos;
                    break;
                case 'Espaços':
                    listar.espacos = this.state.espacos;
                    break;
                case 'ASeguir':
                    listar.aSeguir = this.state.aSeguir;
                    break;
            }
        });

        this.setState({
            activeFilters,
            listar,
            filteredListar: listar
        });
    };

    handleInputFilter = (filterString) => {
        console.log(filterString);

        const { eventos, espacos, aSeguir } = this.state;

        // Filter events
        const filteredEvents = eventos.filter(e => this.doFilter(e.nome, filterString)
            || this.doFilter(e.local, filterString) || this.doFilter(e.responsavel, filterString));

        const filteredEspacos = espacos.filter(e => this.doFilter(e, filterString));

        const filteredASeguir = aSeguir.filter(e => this.doFilter(e.nome, filterString)
            || this.doFilter(e.local, filterString));

        const newFilteredListar = {
            eventos: filteredEvents,
            espacos: filteredEspacos,
            aSeguir: filteredASeguir
        };

        this.setState({
            filteredListar: newFilteredListar
        });
    };

    doFilter = (str1, str2) => {

        return (str1 || '').toLowerCase().indexOf((str2 || '').toLowerCase()) > -1;
    };

    render() {
        const { loading, filteredListar } = this.state;

        if (loading) {
            return <div></div>;
        }

        return (
            <div>
                <h4>Pesquisa Utilizador</h4>

                <FiltroPesquisa setActiveFilters={this.setActiveFilters} handleInputFilter={this.handleInputFilter} />
                <ListarElementosPesquisa listar={filteredListar} />
            </div>
        );
    }
}

export default PesquisaUtilizador;
