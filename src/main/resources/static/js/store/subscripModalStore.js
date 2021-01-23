export default {
  state: {
    type: null,
    users: [],
  },
  mutations: {
    setType(state, payload) {
      state.type = payload;
    },
    setUsers(state, payload) {
      state.users = payload;
    },
  },
  getters: {
    getType: state => state.type,
    getUsers: state => state.users,
  }
};