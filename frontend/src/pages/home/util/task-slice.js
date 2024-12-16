import { createSlice } from "@reduxjs/toolkit"

const initialState = {
    tasks: {},
    modalTasks: [],
    selectedDate: null,
    actualYear: null,
    actualMonth: null
}

const taskSlice = createSlice({
    name: 'task',
    initialState: initialState,
    reducers: {
        runGetTasks(state) {
            return state;
        },
        runPatchTasks(state) {
            return state
        },
        setTasks(state, action) {      
            state.tasks = {...action.payload}
        },
        setModalTasks(state, action) {
            state.modalTasks = [...action.payload]
        },
        setSelectedDate(state, action) {
            state.selectedDate = action.payload
        },
        setActualYear(state, action) {
            state.actualYear = action.payload
        },
        setActualMonth(state, action) {
            state.actualMonth = action.payload
        }
    }
})

export const { 
    runGetTasks, 
    runPatchTasks,
    setTasks,
    setModalTasks,
    setSelectedDate,
    setActualYear,
    setActualMonth
} = taskSlice.actions;
export default taskSlice.reducer;

