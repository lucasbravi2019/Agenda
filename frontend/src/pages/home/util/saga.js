import { call, put, select, takeLatest } from "redux-saga/effects";
import { runGetTasks, runPatchTasks, setTasks } from "./task-slice";
import { selectActualMonth, selectActualYear, selectSelectedDate } from "./selectors";
import { endpoints, getData, putData } from "../../../api/api";

export function* getTasksSaga() {
    const year = yield select(selectActualYear)
    const month = yield select(selectActualMonth)
    const response = yield call(getData, endpoints.getEvent(year, month))

    console.log(response.body);
    
    const tasks = {
        [year]: {
            [month]: {...response.body}
        }
    }
    yield put(setTasks(tasks))
}

export function* patchTasksSaga(action) {
    try {
        const events = action.payload.events
        const selectedDate = yield select(selectSelectedDate)
    
        const request = {
            year: selectedDate.year,
            month: selectedDate.month,
            day: selectedDate.day,
            events
        }
    
        console.log(request);
        
        const response = yield call(putData, endpoints.updateEvent, request)
        
        console.log(response);
        yield put(runGetTasks())
    } catch (error){
        console.log(error);        
    }
    
}

export default function* defaultSaga() {
    yield takeLatest(runGetTasks.type, getTasksSaga)
    yield takeLatest(runPatchTasks.type, patchTasksSaga)
};