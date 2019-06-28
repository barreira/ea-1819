import jwt from 'jwt-decode';

let UserHandler = {};

const userData = 'userData';
const token = 'token';

UserHandler.save = (data) => {
    localStorage.setItem(token, data);
    localStorage.setItem(userData, JSON.stringify(jwt(data)));
}

UserHandler.get = () => {
    const data = localStorage.getItem(userData);

    if (data)
        return JSON.parse(data);

    return data;
}

UserHandler.typeOfUser = () => {
    const data = localStorage.getItem(userData);

    // TODO remover hardcode, verificar tipo de user
    return 'Utilizador'
}

UserHandler.isUserLogged = () => {

    const data = localStorage.getItem(userData);

    if (data) {
        return true;
    }

    return false;
}

UserHandler.remove = () => {
    localStorage.clear();
}

export default UserHandler;