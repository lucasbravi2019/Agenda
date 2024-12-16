import { combineReducers } from "redux";
import taskSlice from "./pages/home/util/task-slice";

const rootReducer = combineReducers({
    task: taskSlice
})

export default rootReducer;