import React, { Component } from 'react';
import FiltroPesquisa from './FiltroPesquisa/FIltroPesquisa';
import ListarElementosPesquisa from './ListarElementosPesquisa/ListarElementosPesquisa';
import moment from 'moment';
import ApiEventos from '../../../../api/ApiEventos';


class PesquisaGestor extends Component {
    constructor(props) {
        super(props);
        this.state = {
            eventos: [],
            espacos: [],
            responsaveis: [],
            listar: {},
            filteredListar: {},
            activeFilters: [],
            inputFilter: '',
            loading: true
        };
    }

    async componentDidMount() {
        const eventos = await ApiEventos.fetchEventos(moment().format('YYYY-MM-DD'), moment().add('15', 'days').format('YYYY-MM-DD'));
        this.refreshFilteredEvents(eventos);
    }

    refreshFilteredEvents = (eventos) => {

        let finalEvents = [];
        let finalEspacos = [];
        let finalResponsaveis = [];

        console.log("LIsta de ventos a friltar", eventos)

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
                    })

                    finalResponsaveis.push(evento.utilizadorResponsavel.nome);
                    finalEspacos.push(espaco);
                })
            }
        }

        // finalEspacos = finalEspacos.slice(0, 40);
        // finalEvents = finalEvents.slice(0, 40);


        const filteredArr = finalEvents.reduce((acc, current) => {
            const x = acc.find(item => item.nome === current.nome);
            if (!x) {
                return acc.concat([current]);
            } else {
                return acc;
            }
        }, []);

        console.log("Eventos filtrados", finalEvents)

        this.setState({
            eventos: filteredArr,
            filteredListar: eventos,
            espacos: [... new Set(finalEspacos)],
            responsaveis: [... new Set(finalResponsaveis)],
            loading: false
        });

    }


    setActiveFilters = (activeFilters) => {
        let listar = {};

        activeFilters.forEach(filter => {
            switch (filter) {
                case 'Eventos':
                    console.log(filter);
                    listar.eventos = this.state.eventos;
                    break;
                case 'Espaços':
                    console.log(filter);
                    listar.espacos = this.state.espacos;
                    break;
                case 'Responsáveis':
                    console.log(filter);
                    listar.responsaveis = this.state.responsaveis;
                    break;
            }
        });

        this.setState({
            activeFilters,
            listar,
            filteredListar: listar
        });
    };

    removeEventById = (eventId) => {
        const eventos = this.state.eventos.filter(e => e.id !== eventId);
        this.refreshFromChild(eventos)
    }

    refreshFromChild = (eventos) => {
        this.setState({
            eventos,
            filteredListar: {
                eventos
            }
        })
    }

    handleInputFilter = (filterString) => {
        console.log(filterString);

        const { eventos, espacos, responsaveis } = this.state;

        // const newListar = Object.keys(listar).map(e => {

        //     console.log(`Comparaing ${listar[e]} to ${filterString}`)
        //     // if (listar[e].indexOf(filterString.toLowerCase()) > -1) {
        //     //     return listar[e];
        //     // }
        // })

        // Filter events
        const filteredEvents = eventos.filter(e => this.doFilter(e.nome, filterString)
            || this.doFilter(e.local, filterString)
            || this.doFilter(e.responsavel, filterString));

        const filteredEspacos = espacos.filter(e => this.doFilter(e, filterString));

        const filteredResponsaveis = responsaveis.filter(e => this.doFilter(e, filterString));

        const newFilteredListar = {
            eventos: filteredEvents,
            espacos: filteredEspacos,
            responsaveis: filteredResponsaveis
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

        console.log("RENDERING AGAIN")
        console.log("FILTRADOS", filteredListar)

        return (
            <div>
                <h4>Pesquisa Gestor</h4>

                <FiltroPesquisa setActiveFilters={this.setActiveFilters} handleInputFilter={this.handleInputFilter} />
                <ListarElementosPesquisa listar={filteredListar} removeEventById={this.removeEventById} />
            </div>
        );
    }
}

export default PesquisaGestor;
