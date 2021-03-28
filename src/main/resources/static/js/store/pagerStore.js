export default {
  state: {
    pageInfo: {
      totalPages: null,
      totalDocs: null,
      currentPage: 0,
      pageSize: 10,
    }
  },
  mutations: {
    setPageInfo(state, payload) {
      state.pageInfo = payload;
    },
  },
  getters: {
    getPageInfo: state => state.pageInfo,
  }
};