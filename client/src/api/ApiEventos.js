import axios from 'axios';
import moment from 'moment';
import UserHandler from '../utils/userHandler';

const HOST = 'http://localhost:8080';
let ApiEventos = {};

// axios.defaults.headers.post['Authorization'] = `Bearer ${UserHandler.getToken()}`;
const token = {
    "headers": {
        "Authorization": `Bearer ${UserHandler.getToken()}`
    }
}

ApiEventos.fetchEventos = async (moment1, moment2) => {

    try {

        // TODO : REMOVE HARDCODE, a solucao sem o espaco não está funcional

        const data = {
            "inicio": moment1,
            "fim": moment2
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

ApiEventos.fetchEventosUtilizadorCPDR = async () => {

    try {

        const userData = UserHandler.get();

        const req = await axios.get(`${HOST}/usercpdr/eventos/view/${userData.id}`, token)
        return req.data;

    } catch (e) {
        console.error(e);
        return {
            success: false,
        }
    }
}

ApiEventos.editar = async (idEvent, novoEvento) => {

    try {

        // TODO : REMOVE HARDCODE, a solucao sem o espaco não está funcional


        const req = await axios.post(`${HOST}/gestor/eventos/update`, {
            id: idEvent,
            novoEvento
        }, token)

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

ApiEventos.cancelar = async (eventId) => {

    try {

        const req = await axios.post(`${HOST}/gestor/eventos/cancelar`, {
            evento: eventId,
            justificacao: 'De manhã está-se bem é na caminha'
        }, token)

        return req.data;

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

        return req.data;

    } catch (e) {
        console.error(e);
        return {
            success: false,
        }
    }

}

ApiEventos.fetchEvento = async (nameEvent) => {

    try {
        const req = await axios.post(`${HOST}/public/users/evento`, {
            "nome": nameEvent
        })

        console.log("Fetching event", req.data)
        return req.data;

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
        }, token)

        return req.data;

    } catch (e) {
        console.error(e);

        return {
            success: false,
        }
    }
}

ApiEventos.unfollowEvent = async (eventId) => {

    try {
        const userData = UserHandler.get();
        const req = await axios.post(`${HOST}/user/unfollow`, {
            "id_user": userData.id,
            "id_evt": eventId
        }, token)

        return req.data;

    } catch (e) {
        console.error(e);

        return {
            success: false,
        }
    }
}

ApiEventos.eventosASeguir = async () => {
    try {
        const userData = UserHandler.get();
        const req = await axios.get(`${HOST}/user/following/${userData.id}`, token)

        return req.data;

    } catch (e) {
        console.error(e);

        return {
            success: false,
        }
    }
}

export default ApiEventos;