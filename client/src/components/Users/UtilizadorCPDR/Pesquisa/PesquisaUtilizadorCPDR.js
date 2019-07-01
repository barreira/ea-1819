import React, { Component } from 'react';
import FiltroPesquisa from './FiltroPesquisa/FIltroPesquisa';
import ListarElementosPesquisa from './ListarElementosPesquisa/ListarElementosPesquisa';

class PesquisaUtilizadorCPDR extends Component {
    constructor(props) {
        super(props);
        this.state = {
            eventos: [],
            espacos: [],
            listar: {},
            filteredListar: {},
            activeFilters: [],
            inputFilter: '',
            loading: true,
            nomeUtilizadorCPDR: ''
        };
    }

    componentDidMount() {
        this.setState({
            eventos: [
                { nome: 'Programacao', local: 'DI-01', horaInicio: '10:00', horaFim: '12:00', responsavel: 'Maria Luisa' },
                { nome: 'Programacao', local: 'DI-01', horaInicio: '10:00', horaFim: '12:00', responsavel: 'Mário Luis' }
            ],
            espacos: ['DI-01', 'DI-02', 'DI-03'],
            loading: false,
            nomeUtilizadorCPDR: 'Maria Luisa'
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

        const { eventos, espacos } = this.state;

        // const newListar = Object.keys(listar).map(e => {

        //     console.log(`Comparaing ${listar[e]} to ${filterString}`)
        //     // if (listar[e].indexOf(filterString.toLowerCase()) > -1) {
        //     //     return listar[e];
        //     // }
        // })

        // Filter events
        const filteredEvents = eventos.filter(e => this.doFilter(e.nome, filterString)
            || this.doFilter(e.local, filterString));

        const filteredEspacos = espacos.filter(e => this.doFilter(e, filterString));

        const newFilteredListar = {
            eventos: filteredEvents,
            espacos: filteredEspacos,
        };

        this.setState({
            filteredListar: newFilteredListar
        });
    };

    doFilter = (str1, str2) => {
        return str1.toLowerCase().indexOf(str2.toLowerCase()) > -1;
    };

    render() {
        const { loading, filteredListar } = this.state;

        if (loading) {
            return <div></div>;
        }

        return (
            <div>
                <h4>Pesquisa UtilizadorCPDR</h4>

                <FiltroPesquisa setActiveFilters={this.setActiveFilters} handleInputFilter={this.handleInputFilter} />
                <ListarElementosPesquisa listar={filteredListar} nomeUtilizadorCPDR={this.state.nomeUtilizadorCPDR} />
            </div>
        );
    }
}

export default PesquisaUtilizadorCPDR;
