import React, { Component } from 'react';
import ResumoEspaco from '../../../ResumoEspaco/ResumoEspaco';

class HomeVisitante extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }
    render() {
        return (
            <div>
                <ResumoEspaco />
            </div>
        );
    }
}

export default HomeVisitante;