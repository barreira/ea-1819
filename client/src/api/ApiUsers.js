import axios from 'axios';
import UserHandler from '../utils/userHandler';

const HOST = 'http://localhost:8080'
let ApiUsers = {};

const token = {
    "headers": {
        "Authorization": `Bearer ${UserHandler.getToken()}`
    }
}

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
            "code": accessCode
        }, token)

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