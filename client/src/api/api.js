import axios from 'axios';
const HOST = 'http://localhost:8080'
let Api = {};

Api.login = async (username, password) => {

    try {
        const req = await axios.post(`${HOST}/public/users/login`, {
            username, password
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

Api.register = async (username, email, name, password) => {

    try {
        const req = await axios.post(`${HOST}/public/users/register`, {
            username, email, name, password
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
export default Api;