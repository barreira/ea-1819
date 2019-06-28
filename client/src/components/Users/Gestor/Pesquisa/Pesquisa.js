import React, { Component } from 'react';
import FiltroPesquisa from './FiltroPesquisa/FIltroPesquisa';
import ListarElementosPesquisa from './ListarElementosPesquisa/ListarElementosPesquisa';

class Pesquisa extends Component {
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
        }
    }

    componentDidMount() {

        this.setState({
            eventos: [
                { nome: 'Programacao', local: 'DI-01', horaInicio: '10:00', horaFim: '12:00', responsavel: 'Maria Luisa' },
                { nome: 'Programacao', local: 'DI-01', horaInicio: '10:00', horaFim: '12:00', responsavel: 'Maria Luisa' }
            ],
            espacos: ['DI-01', 'DI-02', 'DI-03'],
            responsaveis: ['Maria Luisa', 'Rute', 'Luis'],
            loading: false
        })

    }

    setActiveFilters = (activeFilters) => {

        let listar = {};


        activeFilters.forEach(filter => {
            switch (filter) {
                case 'Eventos':
                    console.log(filter)
                    listar.eventos = this.state.eventos;
                    break;
                case 'Espaços':
                    console.log(filter)
                    listar.espacos = this.state.espacos;
                    break;
                case 'Responsáveis':
                    console.log(filter)
                    listar.responsaveis = this.state.responsaveis;
                    break;
            }
        })

        this.setState({
            activeFilters,
            listar,
            filteredListar: listar
        })

    }

    handleInputFilter = (filterString) => {
        console.log(filterString)

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
            || this.doFilter(e.responsavel, filterString))

        const filteredEspacos = espacos.filter(e => this.doFilter(e, filterString));

        const filteredResponsaveis = responsaveis.filter(e => this.doFilter(e, filterString))

        const newFilteredListar = {
            eventos: filteredEvents,
            espacos: filteredEspacos,
            responsaveis: filteredResponsaveis
        }

        this.setState({
            filteredListar: newFilteredListar
        })
    }

    doFilter = (str1, str2) => {
        if (str1.toLowerCase().indexOf(str2.toLowerCase()) > -1)
            return true;

        return false;
    }



    render() {

        const { loading, filteredListar } = this.state;

        if (loading)
            return <div></div>

        return (
            <div>
                <h4>Pesquisa Gestor</h4>

                <FiltroPesquisa setActiveFilters={this.setActiveFilters} handleInputFilter={this.handleInputFilter} />
                <ListarElementosPesquisa listar={filteredListar} />

            </div>
        );
    }
}

export default Pesquisa;
