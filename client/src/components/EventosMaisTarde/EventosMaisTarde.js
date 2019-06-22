import React from 'react';

import EventoSmall from "../EventoSmall/EventoSmall";

import './EventosMaisTarde.css'

const eventosMaisTarde = (props) => (
    <div className="container">
        <h3 className="maisTardeHeader">Mais Tarde</h3>
        <ul className="list-group">
            { props.items.map(e =>
                <li className="list-group-item" key={e.title}>
                    <EventoSmall title={e.title} start={e.start} />
                </li>
            )}
        </ul>
    </div>
);

export default eventosMaisTarde;
