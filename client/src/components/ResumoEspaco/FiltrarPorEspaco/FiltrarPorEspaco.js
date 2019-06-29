import React, { Component } from 'react';
import './FiltrarPorEspaco.css';

const filter = (e) => {
    console.log(e.target)
}

const FiltrarPorEspaco = (props) => {

    const { espacos } = props;

    if (!espacos) {
        return <div></div>
    }

    console.log(props.espacos)

    return (

        <div>
            <h4>Locais</h4>
            <div className="filter-espacos-container">
                {espacos.map(espaco => (
                    <button className="btn" onClick={(e) => props.filter(e)}>{espaco}</button>
                ))}
            </div>
        </div>

    );
}

export default FiltrarPorEspaco;