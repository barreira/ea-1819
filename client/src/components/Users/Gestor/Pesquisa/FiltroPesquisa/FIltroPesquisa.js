import React, { Component } from 'react';
import './FiltroPesquisa.css';

class FiltroPesquisa extends Component {
    constructor(props) {
        super(props);
        this.state = {
            filters: ['Eventos', 'Espaços', 'Responsáveis'],
            activeFilters: ['Eventos', 'Espaços'],
            inputFilter: ''
        }

        this.props.setActiveFilters(this.state.activeFilters);
    }

    classNameByFilter = (filter) => {

        let className = "btn btn-filter";

        if (this.state.activeFilters.includes(filter))
            className = "btn btn-filter btn-filter-active"

        return className;
    }

    changeFilter = (filter) => {

        let newActiveFilters = [];

        if (this.state.activeFilters.includes(filter)) {
            newActiveFilters = this.state.activeFilters.filter(f => f !== filter)
        } else {
            newActiveFilters = this.state.activeFilters;
            newActiveFilters.push(filter)
        }

        this.setState({
            activeFilters: newActiveFilters
        })

        this.props.setActiveFilters(newActiveFilters);
    }

    handleChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        })

        this.props.handleInputFilter(e.target.value);
    }

    render() {
        const { filters } = this.state;

        return (
            <div>
                <div className="input-filter-container">
                    <div>
                        <i className="material-icons individual-icon" >
                            search
                        </i>
                        <input type="text" placeholder="Termo de pesquisa" value={this.state.inputFilter} onChange={(e) => this.handleChange(e)} name="inputFilter" />
                        {filters.map(filter => (
                            <button className={this.classNameByFilter(filter)} onClick={() => this.changeFilter(filter)}> {filter}</button>
                        ))}
                    </div>
                </div>
            </div>
        );

    }
}

export default FiltroPesquisa;