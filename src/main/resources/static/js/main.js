
async function authentcicate(){
    document.querySelector("#error").innerHTML = ''

    const authenticateData = {
        userName: document.querySelector('#userName').value,
        passWord: document.querySelector('#passWord').value
    }
    console.log(authenticateData)

    const response = await fetch(serverUrl + authenticateUrl.authenticateUrl(), {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(authenticateData)
    })

    const data = await response.json()

    if(response.ok){
        if(data.code == 200){
            document.cookie = `jwt=${data.data}; max-age=${3600}; path=/`
        }else{
            document.querySelector("#error").innerHTML = 'User name or password incorrect'
        }
    }
}


// Tạo một interceptor
const interceptor = axios.interceptors.request.use(config => {
    // Lấy JWT từ cookie hoặc localStorage
    const jwtToken = getJwtFromCookieOrLocalStorage();

    // Nếu có JWT, thêm Authorization header
    if (jwtToken) {
        config.headers.Authorization = `Bearer ${jwtToken}`;
    }

    return config;
}, error => {
    return Promise.reject(error);
});

// Hàm lấy JWT từ cookie hoặc localStorage
function getJwtFromCookieOrLocalStorage() {
    // Lấy từ cookie
    const cookies = document.cookie.split(';');
    for (const cookie of cookies) {
        const [name, value] = cookie.trim().split('=');
        if (name === 'jwt') {
            return value;
        }
    }

    return null;
}