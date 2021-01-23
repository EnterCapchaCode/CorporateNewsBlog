export default {
    state: {
        name: null,
        description: null,
        initialDate: null,
        endDate: null,
        status: null,
        testMethod: null,
        testCase: null,
        taskType: null,
        taskSeverity: null,
    },
    mutations: {
        setName(state, payload) {
            state.name = payload;
        },
        setDescription(state, payload) {
            state.description = payload;
        },
        setInitialDate(state, payload) {
            state.initialDate = payload;
        },
        setEndDate(state, payload) {
            state.endDate = payload;
        },
        setStatus(state, payload) {
            state.status = payload;
        },
        setTestMethod(state, payload) {
            state.testMethod = payload;
        },
        setTestCase(state, payload) {
            state.testCase = payload;
        },
        setTaskType(state, payload) {
            state.taskType = payload;
        },
        setTaskSeverity(state, payload) {
            state.taskSeverity = payload;
        },
    },
    getters: {
        getName: state => state.name,
        getDescription: state => state.description,
        getInitialDate: state => state.initialDate,
        getEndDate: state => state.endDate,
        getStatus: state => state.status,
        getTestMethod: state => state.testMethod,
        getTestCase: state => state.testCase,
        getTaskType: state => state.taskType,
        getTaskSeverity: state => state.taskSeverity,
    }
};