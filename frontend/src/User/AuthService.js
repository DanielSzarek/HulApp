import decode from 'jwt-decode';
export default class AuthService {
    // Initializing important variables
    constructor(domain) {

        this.fetch = this.fetch.bind(this) // React binding stuff
        this.login = this.login.bind(this)
        this.getProfile = this.getProfile.bind(this)
    }

    login(username, password) {
        // Get a token from api server using the fetch api
        console.log(username);
        console.log(password);
        return this.fetch('http://hulapp.pythonanywhere.com/auth/jwt/create/', {
            method: 'POST',
            body: JSON.stringify({
                // username,
                // password
                email: username,
                password: password
            })
        }).then(res => {
			console.log(res);
            this.setToken(res.access) // Setting the token in localStorage
			this.setRefreshidToken(res.refresh)
            return Promise.resolve(res);
        })
    }
	
	refresh(){
		console.log("refreshing token");
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
			console.log(res);
			console.log("Setting new acc token")
            this.setToken(res.access) // Setting the token in localStorage
            return Promise.resolve(res);
        })
		.catch((error) => {
                console.log({message: "ERROR " + error});
            });
	}

    async loggedIn() {
        // Checks if there is a saved token and it's still valid
        const token = this.getToken() // GEtting token from localstorage
		//check if tokem expired
		if(!!token && token !== 'undefined' && !this.isTokenExpired(token)){
			console.log("token valid");
			return true;
		}
		else{
			console.log("checking");
			//check if refresh token not expired
			const refreshToken = this.getRefreshToken();
			console.log(refreshToken);
			if(!!refreshToken && !this.isTokenExpired(refreshToken)){
				await this.refresh();
				return true;
			}
		}
		
        return false;
    }

    isTokenExpired(token) {
        try {
            const decoded = decode(token);
            if (decoded.exp < Date.now() / 1000) { // Checking if token is expired. N
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
        // Saves user token to localStorage
        localStorage.setItem('id_token', idToken)
    }
	
	setRefreshidToken(idToken) {
        // Saves user token to localStorage
        localStorage.setItem('id_refresh', idToken)
    }

    getToken() {
        // Retrieves the user token from localStorage
        return localStorage.getItem('id_token')
    }
	
	getRefreshToken() {
        // Retrieves the refresh token from localStorage
        return localStorage.getItem('id_refresh')
    }

    logout() {
        // Clear user token and profile data from localStorage
        localStorage.removeItem('id_token');
    }

    getProfile() {
        // Using jwt-decode npm package to decode the token
        return decode(this.getToken());
    }


    async fetch(url, options) {
        // performs api calls sending the required authentication headers
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }

        // Setting Authorization header
        // Authorization: Bearer xxxxxxx.xxxxxxxx.xxxxxx
        if (await this.loggedIn()) {
            headers['Authorization'] = 'Bearer ' + this.getToken()
			console.log("authorization header added");
        }
		else{
			console.log("not logged in");
		}

        return fetch(url, {
            headers,
            ...options
        })
            .then(this._checkStatus)
            .then(response => response.json())
    }

    _checkStatus(response) {
        // raises an error in case response status is not a success
        if (response.status >= 200 && response.status < 300) { // Success status lies between 200 to 300
            return response
        } else {
            var error = new Error(response.statusText)
            error.response = response
            throw error
        }
    }
}