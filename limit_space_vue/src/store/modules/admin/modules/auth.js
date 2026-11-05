export default {
    namespaced: true,
    state: {
        buttonStatus: false
    },
    mutations: {
        setButtonStatus(state, buttonStatus) {
            state.buttonStatus = buttonStatus
        }
    }
}
