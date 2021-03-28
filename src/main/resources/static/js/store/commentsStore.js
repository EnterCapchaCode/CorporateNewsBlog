export default {
    state: {
        count: 0,
        comments: []
    },
    mutations: {
        setComments(state, payload) {
            state.comments = payload;
        },
    },
    getters: {
        getComments: state => state.comments,
    }
};