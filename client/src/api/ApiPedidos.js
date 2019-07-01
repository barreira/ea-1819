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


        console.log("USERDATA", userData)

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

        console.log("API pedido depois", req.data)

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


export default ApiPedidos;