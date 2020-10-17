import { createStore, combineReducers } from "redux";

const initialState = {
    error: null,
    isLoaded: false,
    pollData:[]
}

const pollReducer = (state = initialState, action) => {
    console.log("action type:", action.type)
    console.log("Action poll data:", action.pollData) 
    switch (action.type) {
        case "SET_ERROR":
            state = {
                ...state,
                error: action.error,
                isLoaded: action.isLoaded,
            }
            break;
        case "SET_DATA":
            state = {
                ...state,
                isLoaded: action.isLoaded,
                pollData: action.pollData
            }
            break;
        default:
            state = {
                ...state
            }
    }
    return state;
}    

export const store = createStore(combineReducers({poll: pollReducer})); 