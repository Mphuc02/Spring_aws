const serverUrl = "http://localhost:8082"
const authenticateUrl = {
    auth : "/api/v1/auth",
    registerUrl(){
        return this.auth + "/register"
    },
    authenticateUrl(){
        return this.auth + "/authenticate"
    }
}
const jwt = 'jwt'