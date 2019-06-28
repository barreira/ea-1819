import React, { Component } from 'react';

import Pesquisa from '../Pesquisa/Pesquisa';

class HomeGestor extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }

    render() {
        return (

            <div>
                <Pesquisa />
            </div>

        );
    }
}

export default HomeGestor;