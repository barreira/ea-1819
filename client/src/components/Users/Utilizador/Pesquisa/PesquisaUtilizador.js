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
            loading: true,
            eventosASeguir: []
        };
    }

    async componentDidMount() {
        this.fetchStuff();
    }

    fetchStuff = async () => {

        console.log("Fetching data from PesquisaUtilizador")

        const eventos = await ApiEventos.fetchEventos(moment().format('YYYY-MM-DD'), moment().add('5', 'days').format('YYYY-MM-DD'));

        const eventosASeguir = await ApiEventos.eventosASeguir();

        let finalEvents = [];
        let finalEspacos = [];

        for (let day in eventos) {
            const currDayEvents = eventos[day];
            if (Array.isArray(currDayEvents)) {
                currDayEvents.forEach(evento => {

                    const espaco = evento.espaco.designacao;
                    finalEvents.push({
                        "id": evento.id,
                        "nome": evento.nome,
                        "data": day,
                        "local": espaco,
                        "horaInicio": evento.localDate,
                        "horaInicio": evento.dateTimeInicial,
                        "horaFim": evento.dateTimeFinal,
                        "responsavel": evento.utilizadorResponsavel.nome,
                        "aSeguir": (eventosASeguir.filter(e => e.nome === evento.nome).length > 0 ? true : false)
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

        const activeFilters = this.state.activeFilters;

        console.log("ACTIVE FILTERS", activeFilters)
        // Filter events
        const filteredEvents = activeFilters.includes('Eventos') ? eventos.filter(e => this.doFilter(e.nome, filterString)
            || this.doFilter(e.local, filterString) || this.doFilter(e.responsavel, filterString)) : [];

        const filteredEspacos = activeFilters.includes('Espaços') ? espacos.filter(e => this.doFilter(e, filterString)) : [];

        const newFilteredListar = {
            eventos: filteredEvents,
            espacos: filteredEspacos,
            aSeguir: []
        };

        this.setState({
            filteredListar: newFilteredListar
        });
    };

    updateParent = () => {

    }

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
                <ListarElementosPesquisa listar={filteredListar} updateParent={this.fetchStuff} />
            </div>
        );
    }
}

export default PesquisaUtilizador;
