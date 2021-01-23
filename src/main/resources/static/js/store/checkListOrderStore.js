export default {
    state: {
        order: null,
        availableOrders: null,
    },
    mutations: {
        setOrder(state, payload) {
            state.order = payload;
        },
        setAvailableOrders(state, payload) {
            state.availableOrders = payload;
        },
    },
    getters: {
        getOrder: state => state.order,
        getAvailableOrders: state => state.availableOrders,
    }
};