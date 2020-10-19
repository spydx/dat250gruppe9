import { createStore, combineReducers } from "redux";

const pollinitialState = {
    error: null,
    isLoaded: false,
    pollData:[] 
}

const userinitialState = {
    error: null,
    isLoaded: false,
    id: null,
    firstname: null,
    lastname: null,
    email: null,
    role: null,
    pollData: []
}

const userReducer = (state = userinitialState, action) => {
    switch (action.type) {
        case "SET_ERROR":
            state = {
                ...state,
                error: action.error,
                isLoggedin: false
            }
            break;
        case "SET_LOGGIN":
            state = {
                ...state,
                isLoggedin: true,
                id: action.id,
                firstname: action,
                lastname: action.lastname,
                email: action.email,
                role: action.role,
                polls: action.polls
            }
            break;
        default:
            break;
    }
    return state;
}

const pollReducer = (state = pollinitialState, action) => {
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
            break; 
    }
    return state;
}    

export const store = createStore(combineReducers({poll: pollReducer, user: userReducer})); 