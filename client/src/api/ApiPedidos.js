import axios from 'axios';
import moment from 'moment';
import UserHandler from '../utils/userHandler';

const HOST = 'http://localhost:8080';
let ApiPedidos = {};

// axios.defaults.headers.post['Authorization'] = `Bearer ${UserHandler.getToken()}`;
const token = {
    "headers": {
        "Authorization": `Bearer ${UserHandler.getToken()}`
    }
}

const userData = UserHandler.get();

ApiPedidos.novoPedido = async (pedido) => {

    try {
        const data = {
            "id": userData.id,
            "alocacao": {
                "nome": pedido.nome,
                "descricao": pedido.descricao,
                "periocidade": pedido.periocidade,
                "dateTimeInicial": pedido.dateTimeInicial,
                "dateTimeFinal": pedido.dateTimeFinal,
                "limite": pedido.limite,
                "espaco": {
                    "id": pedido.espaco.id
                }
            }
        }
        console.log("API pedido antes", data)

        const req = await axios.post(`${HOST}/usercpdr/alocar/`, {
            ...data
        }, token)

        console.log("Pedido criado", req.data)

        return req.data;

    } catch (e) {
        console.error(e);

        return {
            success: false,
        }
    }
}
ApiPedidos.fetchPedidosPendentes = async () => {

    try {
        const req = await axios.get(`${HOST}/usercpdr/pedidos/pendentes/${userData.id}`, token);

        console.log(`Pedidos pendentes do user ${userData.id}`, req.data)

        return req.data;

    } catch (e) {
        console.error(e);

        return {
            success: false,
        }
    }
}

ApiPedidos.fetchPedidosAtendidos = async () => {

    try {
        const req = await axios.get(`${HOST}/usercpdr/pedidos/atendidos/${userData.id}`, token);

        console.log(`Pedidos atendidos do user ${userData.id}`, req.data)

        return req.data;

    } catch (e) {
        console.error(e);

        return {
            success: false,
        }
    }
}

ApiPedidos.cancelarPedido = async (idPedido) => {

    try {
        const req = await axios.post(`${HOST}/usercpdr/pedidos/cancelar`, {
            "id": userData.id,
            "nr": idPedido
        }, token);

        console.log(`A cancelar pedido ${idPedido} do user ${userData.id}`, req.data)

        return req.data;

    } catch (e) {
        console.error(e);

        return {
            success: false,
        }
    }
}

ApiPedidos.consultarPedidosPendentesPeloGestor = async () => {
    try {
        const req = await axios.get(`${HOST}/gestor/pedidos/view`, token);

        console.log(`A consultar pedidos pendentes `, req.data)
        return req.data;

    } catch (e) {
        console.error(e);
        return {
            success: false,
        }
    }
}

ApiPedidos.aceitarPedido = async (idPedido) => {
    try {
        const req = await axios.post(`${HOST}/gestor/pedidos/aceitar/${idPedido}`, {}, token);
        return req.data;

    } catch (e) {
        console.error(e);
        return {
            success: false,
        }
    }
}

ApiPedidos.rejeitarPedido = async (idPedido) => {
    try {
        const req = await axios.post(`${HOST}/gestor/pedidos/rejeitar`, {
            "nr": idPedido,
            "justificacao": "És feio"
        },
            token);
        return req.data;

    } catch (e) {
        console.error(e);
        return {
            success: false,
        }
    }
}

export default ApiPedidos;