import axios from 'axios';
import moment from 'moment';
import UserHandler from '../utils/userHandler';

const HOST = 'http://localhost:8080';
let ApiEspacos = {};

// axios.defaults.headers.post['Authorization'] = `Bearer ${UserHandler.getToken()}`;
const token = {
    "Authorization": `Bearer ${UserHandler.getToken()}`
}

const userData = UserHandler.get();

ApiEspacos.fetchAll = async () => {

    try {

        const req = await axios.get(`${HOST}/public/users/espacos/viewAll`)

        console.log("ESPACOS")
        console.log(req.data)
        return req.data;

    } catch (e) {
        console.error(e);

        return {
            success: false,
        }
    }
}


export default ApiEspacos;