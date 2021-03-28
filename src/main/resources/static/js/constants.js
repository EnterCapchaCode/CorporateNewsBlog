export default {
    REQUIRED_FIELD: 'Required field',
    MIN_3_CHARS_FIELD: 'Min 3 characters',
    MIN_100_CHARS_FIELD: 'Min 100 characters',
    STATUS_VALUES: [ 'TO_DO', 'IN_PROGRESS', 'IN_ANALYSIS', 'NEED_CLARIFY', 'READY_FOR_QA', 'IN_TEST', 'DONE', 'CANCELLED' ],
    TASK_TYPE_VALUES: [ 'TASK', 'IMPROVEMENT', 'NEW_FEATURE', 'INCIDENT', 'CHANGE_REQUEST', 'BUG' ],
    TASK_SEVERITY_VALUES: [ 'TRIVIAL', 'MINOR', 'AVERAGE', 'MAJOR', 'CRITICAL', 'BLOCKER' ],
    getProjectDTO: function({name, description, initialDate, endDate, id = null, status = 'TO_DO'}) {
        return {
            id,
            name,
            description,
            status,
            initialDate: new Date(initialDate).toISOString(),
            endDate: new Date(endDate).toISOString(),
        }
    },
    getModuleDTO: function({name, description, initialDate, endDate, id = null, status = 'TO_DO', projectId}) {
        return {
            id,
            name,
            description,
            status,
            initialDate: new Date(initialDate).toISOString(),
            endDate: new Date(endDate).toISOString(),
            projectId
        }
    },
    getTestPlanDTO: function({name, description, initialDate, endDate, id = null, status = 'TO_DO', moduleId, testMethod}) {
        return {
            id,
            name,
            description,
            status,
            initialDate: new Date(initialDate).toISOString(),
            endDate: new Date(endDate).toISOString(),
            testMethod,
            moduleId,
        }
    },
    getRequirementDTO: function({name, description, initialDate, endDate, id = null, status = 'TO_DO', moduleId}) {
        return {
            id,
            name,
            description,
            status,
            initialDate: new Date(initialDate).toISOString(),
            endDate: new Date(endDate).toISOString(),
            moduleId,
        }
    },
    getTestCaseDTO: function({name, description, testCase, initialDate, endDate, id = null, status = 'TO_DO', requirementId,}) {
        return {
            id,
            name,
            description,
            testCase,
            status,
            initialDate: new Date(initialDate).toISOString(),
            endDate: new Date(endDate).toISOString(),
            requirementId,
        }
    },
    getTaskDTO: function({name, description, initialDate, endDate, id = null, status = 'TO_DO', taskType, severity, testCaseId}) {
        return {
            id,
            name,
            description,
            status,
            taskType,
            severity,
            initialDate: new Date(initialDate).toISOString(),
            endDate: new Date(endDate).toISOString(),
            testCaseId,
        }
    },
    getCheckListDTO: function({name, description, initialDate, endDate, id = null, status = 'TO_DO', moduleId}) {
        return {
            id,
            name,
            description,
            status,
            initialDate: new Date(initialDate).toISOString(),
            endDate: new Date(endDate).toISOString(),
            moduleId,
        }
    },
    getCheckListTestCaseDTO: function({name, description, id = null, status = 'TO_DO', testCaseOrder}) {
        return {
            id,
            name,
            description,
            status,
            testCaseOrder,
        }
    },
    normaliseDateString: str => {
      const dateAndTime = new Date(str).toISOString();
      const delimiter = dateAndTime.indexOf('T');
      const date = dateAndTime.substr(0, delimiter);
      const time = dateAndTime.substr(delimiter + 1, 5);
      return `${date} ${time}`;
    },
}