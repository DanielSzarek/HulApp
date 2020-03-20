import decode from 'jwt-decode';

export default class AuthService {
    constructor(domain) {

        this.fetch = this.fetch.bind(this)
        this.login = this.login.bind(this)
        this.getProfile = this.getProfile.bind(this)
    }

    login(username, password) {
        return this.fetch('http://hulapp.pythonanywhere.com/auth/jwt/create/', {
            method: 'POST',
            body: JSON.stringify({
                email: username,
                password: password
            })
        }).then(res => {
            this.setToken(res.access)
            this.setRefreshidToken(res.refresh)
            return Promise.resolve(res);
        })
    }

    refresh() {
        return fetch('http://hulapp.pythonanywhere.com/auth/jwt/refresh/', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                refresh: this.getRefreshToken()
            })
        })
            .then(res => res.json())
            .then(res => {
                this.setToken(res.access)
                return Promise.resolve(res);
            })
            .catch((error) => {
                console.log({ message: "ERROR " + error });
            });
    }

    async loggedIn() {
        const token = this.getToken()
        if (!!token && token !== 'undefined' && !this.isTokenExpired(token)) {
            return true;
        }
        else {
            const refreshToken = this.getRefreshToken();
            if (!!refreshToken && !this.isTokenExpired(refreshToken)) {
                await this.refresh();
                return true;
            }
        }
        return false;
    }

    isTokenExpired(token) {
        try {
            const decoded = decode(token);
            if (decoded.exp < Date.now() / 1000) {
                return true;
            }
            else
                return false;
        }
        catch (err) {
            return false;
        }
    }

    setToken(idToken) {
        localStorage.setItem('id_token', idToken)
    }

    setRefreshidToken(idToken) {
        localStorage.setItem('id_refresh', idToken)
    }

    getToken() {
        return localStorage.getItem('id_token')
    }

    getRefreshToken() {
        return localStorage.getItem('id_refresh')
    }

    logout() {
        localStorage.removeItem('id_token');
        localStorage.removeItem('id_refresh');
    }

    getProfile() {
        return decode(this.getToken());
    }

    async fetch(url, options) {
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }

        if (await this.loggedIn()) {
            headers['Authorization'] = 'Bearer ' + this.getToken()
        }

        return fetch(url, {
            headers,
            ...options
        })
            .then(this._checkStatus)
            .then(response => response.json())
    }

    _checkStatus(response) {
        if (response.status >= 200 && response.status < 300) { // Success status lies between 200 to 300
            return response
        }
        else {
            var error = new Error(response.statusText)
            error.response = response
            throw error
        }
    }
}