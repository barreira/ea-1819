import React from 'react';

import './EventoSmall.css'

const eventoSmall = (props) => (
    <div className="container">
        <p className="MaisTardeTitle">{props.title}</p>
        <p className="MaisTardeStart">{props.start}</p>
    </div>
);

export default eventoSmall;
