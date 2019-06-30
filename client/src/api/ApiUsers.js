import axios from 'axios';
import UserHandler from '../utils/userHandler';

const HOST = 'http://localhost:8080'
let ApiUsers = {};

axios.defaults.headers.post['Authorization'] = `Bearer ${UserHandler.getToken()}`;

ApiUsers.login = async (username, password) => {

    try {

        const req = await axios.post(`${HOST}/public/users/login`, {
            username,
            password,
            "type": "utilizador"
        })

        return {
            success: true,
            status: req.status,
            token: req.data
        };

    } catch (e) {
        console.error(e);

        return {
            success: false,
        }
    }
}

ApiUsers.register = async (username, email, name, password) => {

    try {

        const req = await axios.post(`${HOST}/public/users/register`, {
            "username": username,
            "email": email,
            "nome": name,
            "password": password,
            "type": "utilizador"
        })

        return {
            success: true,
            status: req.status,
            token: req.data
        };

    } catch (e) {
        console.error(e);

        return {
            success: false,
        }
    }
}

ApiUsers.calendarPermissions = async (accessCode) => {

    try {
        const userData = UserHandler.get();
        const req = await axios.post(`${HOST}/user/calendar`, {
            "id": userData.id,
            "code": "4/eQHaiunq3K0dB2l-TbPkW3nVnc4sbXxUE-AXOzlEzQ3l_ArKL73-4v8"
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


export default ApiUsers;