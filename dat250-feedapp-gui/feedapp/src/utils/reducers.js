import { combineReducers } from "redux";

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
        case "SET_ERROR":
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
                email: action.email,
                pollData: action.pollData
            }
            break;
        case "LOAD_POLLS":
            state = {
                ...state,
                pollData: action.pollData
            }
            break;
        case "AUTHORIZE":
            state = {
                ...state,
                token: action.token
            }
            break;
        case "RESET":
            state = {
                userinitialState
            }
            break;
        case "SET_EMAIL":
            state = {
                ...state,
                email: action.email
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

const resultReducer = (state = resultinitialState, action) => {
    switch (action.type) {
        case "SET_ERROR":
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
        default:
            break;
    }

    return state

}

export const rootReducers = combineReducers({poll: pollReducer, user: userReducer, result: resultReducer}); 