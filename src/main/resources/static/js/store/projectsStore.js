export default {
    state: {
        count: 0,
        projects: [],
        filter: "",
        fullProject: null,
        fullModule: null,
        fullRequirement: null,
        fullTestCase: null,
        fullCheckList: null,
        availableTestCases: null,
    },
    mutations: {
        setProjects(state, payload) {
            state.projects = payload;
        },
        setProjectFilter(state, payload) {
            state.filter = payload;
        },
        setFullProject(state, payload) {
            state.fullProject = payload;
        },
        setFullModule(state, payload) {
            state.fullModule = payload;
        },
        setFullRequirement(state, payload) {
            state.fullRequirement = payload;
        },
        setFullTestCase(state, payload) {
            state.fullTestCase = payload;
        },
        setFullCheckList(state, payload) {
            state.fullCheckList = payload;
        },
        setAvailableTestCases(state, payload) {
            state.availableTestCases = payload;
        },
    },
    getters: {
        getProjects: state => state.projects,
        getProjectFilter: state => state.filter,
        getFullProject: state => state.fullProject,
        getFullModule: state => state.fullModule,
        getFullRequirement: state => state.fullRequirement,
        getFullTestCase: state => state.fullTestCase,
        getFullCheckList: state => state.fullCheckList,
        getAvailableTestCases: state => state.availableTestCases,
    }
};