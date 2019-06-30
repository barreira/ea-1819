import React, { Component } from 'react';
import FiltroPesquisa from './FiltroPesquisa/FIltroPesquisa';
import ListarElementosPesquisa from './ListarElementosPesquisa/ListarElementosPesquisa';

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

    componentDidMount() {
        this.setState({
            eventos: [
                { nome: 'Programacao I', local: 'DI-0.01', horaInicio: '10:00', horaFim: '12:00', responsavel: 'Maria Luisa' },
                { nome: 'Programacao II', local: 'DI-0.01', horaInicio: '10:00', horaFim: '12:00', responsavel: 'Maria Luisa' }
            ],
            espacos: ['DI-0.01', 'DI-0.02', 'DI-0.03'],
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
                case 'ASeguir':
                    console.log(filter);
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
            || this.doFilter(e.local, filterString));

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
        return str1.toLowerCase().indexOf(str2.toLowerCase()) > -1;
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
