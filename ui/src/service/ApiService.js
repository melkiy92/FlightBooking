import axios from 'axios';

const USER_API_BASE_URL = 'http://localhost:8080';

class ApiService {

    getTickets(searchCriterion) {
        return axios.post(USER_API_BASE_URL + '/flights', searchCriterion);
    }

    getMultiCityTickets(MultiCitySearchCriterion) {
        return axios.post(USER_API_BASE_URL + '/flights/multi', MultiCitySearchCriterion);
    }
}

export default new ApiService();