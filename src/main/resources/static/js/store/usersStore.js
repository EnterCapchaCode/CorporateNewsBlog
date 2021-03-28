export default {
    state: {
        count: 0,
        users: []
    },
    mutations: {
        setUsers(state, payload) {
            state.users = payload;
        },
    },
    getters: {
        getUsers: state => state.users,
    }
};