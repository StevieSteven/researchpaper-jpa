// @flow

import {
    createStore,
    compose,
    applyMiddleware
} from 'redux'

import thunk from 'redux-thunk'


import reducers from './store'

const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

export default function configureStore(initialState:any) {
    const store = createStore(reducers, initialState, composeEnhancers(
        applyMiddleware(thunk)
    ))

    return store
}