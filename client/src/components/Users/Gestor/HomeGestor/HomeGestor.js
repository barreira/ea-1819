import React, { Component } from 'react';

import PesquisaGestor from '../Pesquisa/PesquisaGestor';

class HomeGestor extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }

    render() {
        return (

            <div>
                <PesquisaGestor />
            </div>

        );
    }
}

export default HomeGestor;