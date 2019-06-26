import React from 'react';

import EventoFull from "../EventoFull/EventoFull";

import './EventosADecorrer.css'

const eventosADecorrer = (props) => (
    <div>
        <h2 className="aDecorrerHeader">A Decorrer</h2>
        <div id="accordion">
            { props.items.map(e =>
                <EventoFull
                    title={e.title}
                    location={e.location}
                    responsible={e.responsible}
                    start={e.start}
                    end={e.end}
                    description={e.description}
                />
            )}
        </div>
    </div>
);

export default eventosADecorrer;
