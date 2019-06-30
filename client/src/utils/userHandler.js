import jwt from 'jwt-decode';

let UserHandler = {};

const userData = 'userData';
const token = 'token';

UserHandler.save = (data) => {
    localStorage.setItem(token, data);
}

UserHandler.get = () => {
    const data = localStorage.getItem(token);

    if (data)
        return jwt(data);

    return false;
}

UserHandler.typeOfUser = () => {
    const userData = UserHandler.get();
    return userData.role;
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