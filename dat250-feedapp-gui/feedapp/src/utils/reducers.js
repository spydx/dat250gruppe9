import { combineReducers } from "redux";

const pollinitialState = {
    error: null,
    isLoaded: false,
    pollData:[] 
}

const userinitialState = {
    error: null,
    isLoaded: false,
    isLoggedin: false,
    id: null,
    firstname: null,
    lastname: null,
    email: null,
    pollData: [],
    token: null
}

const resultinitialState = {
    error: null, 
    isLoaded: false, 
    resultData: [],
}

const userReducer = (state = userinitialState, action) => {
    switch (action.type) {
        case "SET_USER_ERROR":
            state = {
                ...state,
                error: action.error,
                isLoggedin: action.isLoggedin,
                isLoaded: action.isLoaded
            }
            break;
        case "SET_LOGIN":
            state = {
                ...state,
                isLoggedin: action.isLoggedin,
                isLoaded: action.isLoaded,
                id: action.id,
                firstname: action.firstname,
                lastname: action.lastname,
            }
            break;
        case "AUTHORIZE":
            state = {
                ...state,
                token: action.token,
                id: action.id
            }
            break;
        case "RESET_USER":
            state = {
                error: null,
                isLoaded: false,
                isLoggedin: false,
                id: null,
                firstname: null,
                lastname: null,
                email: null,
                pollData: [],
                token: null
            }
            break;
        case "SET_EMAIL":
            state = {
                ...state,
                email: action.email
            }
            break;
        case "SET_USER_POLLDATA":
            state = {
                ...state,
                pollData: action.pollData
            }
            break;
        default:
            break;
    }
    return state;
}

const pollReducer = (state = pollinitialState, action) => {
    switch (action.type) {
        case "SET_POLL_ERROR":
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
        case "RESET_POLL_DATA":
            state = {
                error: null,
                isLoaded: false,
                pollData:[] 
            }
            break;
        default:
            break; 
    }
    return state;
}

const resultReducer = (state = resultinitialState, action) => {
    switch (action.type) {
        case "SET_RESULT_ERROR":
            state = {
                ...state, 
                error: action.error,
                isLoaded: action.isLoaded,
            }
            break;
        
        case "SET_RESULTDATA":
            state = {
                ...state, 
                isLoaded: action.isLoaded,
                resultData: action.resultData,
            }
            break;
        case "RESET_RESULT":
            state = {
                error: null, 
                isLoaded: false, 
                resultData: []
            }
        default:
            break;
    }
    return state

}

export const rootReducers = combineReducers({poll: pollReducer, user: userReducer, result: resultReducer}); 