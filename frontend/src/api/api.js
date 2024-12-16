const APPLICATION = 'application/json'
const URL = 'http://localhost:8080'

const defaultHeader = {
    Accept: APPLICATION,
    'Content-Type': APPLICATION
}

const get = () => ({
    method: 'GET',
    headers: defaultHeader,
    mode: 'cors'
})

const put = (body) => ({
    method: 'PUT',
    headers: defaultHeader,
    mode: 'cors',
    body: JSON.stringify(body)
})

export const endpoints = {
    getEvent: (year, month) => `agenda?year=${year}&month=${month}`,
    updateEvent: 'agenda'
}

const api = async (url, endpoint, action) => {
    try {
        const response = await fetch(`${url}/${endpoint}`, action)

        if (response.status === 200)
            return {
                status: response.status,
                body: response.body != null ? await response.json() : null
            }
        if (response.status === 204)
            return {
                status: response.status
            }

        return { status: response.status, error: true }
    } catch (error) {
        console.log(`Error al ejecutar petición: ${error}`);
    }
}

export const getData = (endpoint) => api(URL, endpoint, get())
export const putData = (endpoint, body) => api(URL, endpoint, put(body));