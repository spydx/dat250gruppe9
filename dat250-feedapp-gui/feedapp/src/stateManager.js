import { applyMiddleware, createStore, combineReducers, compose } from "redux";
import { connectRouter, routerMiddleware } from 'connected-react-router'
import history from './history'

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
                firstname: action,
                lastname: action.lastname,
                email: action.email,
                role: action.role,
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


const createRootReducer = (history) => combineReducers({
    router: connectRouter(history),
    poll: pollReducer,
    user: userReducer
})


export default function configureStore(preloadedState) {
    const store = createStore(
        createRootReducer(history),
        preloadedState,
        compose(
            applyMiddleware(
              routerMiddleware(history), // for dispatching history actions
              // ... other middlewares ...
            ),
        ),
    )
    return store
}
