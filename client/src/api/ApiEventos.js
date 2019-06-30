import axios from 'axios';
import moment from 'moment';
import UserHandler from '../utils/userHandler';

const HOST = 'http://localhost:8080';
let ApiEventos = {};

axios.defaults.headers.post['Authorization'] = `Bearer ${UserHandler.getToken()}`;

ApiEventos.fetchEventos = async () => {

    try {

        // TODO : REMOVE HARDCODE, a solucao sem o espaco não está funcional

        const data = {
            "inicio": moment().subtract('300', 'days').format('YYYY-MM-DD'),
            "fim": moment().add('300', 'days').format('YYYY-MM-DD'),
            "espaco": 88,
        }

        const req = await axios.post(`${HOST}/public/users/eventos`, {
            ...data
        })

        console.log("Data", data)
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



ApiEventos.followEvent = async (eventId) => {

    try {
        const userData = UserHandler.get();
        const req = await axios.post(`${HOST}/user/follow`, {
            "id_user": userData.id,
            "id_evt": eventId
        })

        console.log(req.data)

        return {
            success: true,
            data: req.data
        };

    } catch (e) {
        console.error(e);

        return {
            success: false,
        }
    }
}

export default ApiEventos;