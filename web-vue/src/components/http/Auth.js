import axios from 'axios';

const API_URL = (import.meta.env.VUE_APP_AUTH_URL ? import.meta.env.VUE_APP_AUTH_URL : "/");
const LOGIN_URL = API_URL + 'login';

export const TOKEN_KEY = "jwt";
export const DETAILS_KEY = "auth-details";

export default {

    login(credentials) {
        this.clearToken();
        return axios.post(LOGIN_URL, credentials)
            .then(
                (response) => {
                    localStorage.setItem(TOKEN_KEY, response.data.access_token);
                    localStorage.setItem(DETAILS_KEY, JSON.stringify(response.data));
                },
                (error) => {
                    console.info(error);
                }
            )
    },

    logout() {
        this.clearToken();
    },

    clearToken() {
        localStorage.removeItem(TOKEN_KEY);
        localStorage.removeItem(DETAILS_KEY);
    },

    isAuthenticated() {
        return localStorage.getItem(TOKEN_KEY) != null;
    },

    getAuthDetails() {
        if (!this.isAuthenticated())
            return null;

        return JSON.parse(localStorage.getItem(DETAILS_KEY));
    }
}
