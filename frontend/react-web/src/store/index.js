// @flow

import {
    combineReducers
} from 'redux'

import articlesReducer from './articles'
import authorsReducer from './authors'

export default combineReducers({
    articles: articlesReducer,
    authors: authorsReducer
})