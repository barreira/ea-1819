import React from 'react';

import './EventoFull.css'

const eventoFull = (props) => {

    const id = props.title.replace(/\s/g, '');

    return (
        <div className="card">
            <div className="card-header">
                <div className="row">
                    <button className="aDecorrerButton btn btn-link col-xs-12 col-sm-7" data-toggle="collapse" data-target={'#' + id}>
                        <p className="aDecorrerTitle">{props.title}</p>
                    </button>
                    <div className="aDecorrerInfo col-xs-12 col-sm-5">
                        <p className="aDecorrerLocation">{props.location}</p>
                        <p className="aDecorrerStartEnd">{props.start} - {props.end}</p>
                    </div>
                </div>
            </div>

            <div id={id} className="collapse hide">
                <div className="row card-body">
                    <div className="col-xs-12 col-sm-9 justify-content-end">
                        <p className="aDecorrerDescription">{props.description}</p>
                    </div>
                    <div className="col-xs-12 col-sm-3">
                        <p className="aDecorrerResponsible">{props.responsible}</p>
                    </div>
                </div>
            </div>
            {console.log(id)}
        </div>
    );
};

export default eventoFull;