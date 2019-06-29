import axios from 'axios';
import moment from 'moment';
const HOST = 'http://localhost:8080'

let ApiEventos = {}

ApiEventos.fetchEventos = async () => {

    try {
        const req = await axios.post(`${HOST}/public/users/eventos`, {
            "inicio": moment().subtract('30', 'days').format('YYYY-MM-DD'),
            "fim": moment().add('7', 'days').format('YYYY-MM-DD'),
        })

        console.log(req)
        return {
            success: true,
            ...req.data
        };

    } catch (e) {
        console.error(e);

        return {
            success: false,
        }
    }
}

ApiEventos.fetchEventosEspaco = async (espaco) => {

    try {
        const req = await axios.post(`${HOST}/public/users/eventos`, {
            "inicio": moment().subtract('30', 'days').format('YYYY-MM-DD'),
            "fim": moment().add('7', 'days').format('YYYY-MM-DD'),
            espaco
        })

        console.log(req)
        return {
            success: true,
            ...req.data
        };

    } catch (e) {
        console.error(e);
        return {
            success: false,
        }
    }

}

export default ApiEventos;