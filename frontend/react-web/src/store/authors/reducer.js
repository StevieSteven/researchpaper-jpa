// @flow

import type Action from '../../types/reduxTypes'


import {
    actionTypes
} from '../articles/'
const {
    LOAD_AUTHORS_FOR_ARTICLE_FINISHED
} = actionTypes

const initialState = {
    byId: {}
}

const reducer = (state:any = initialState, action:Action) => {
    switch (action.type) {
        case LOAD_AUTHORS_FOR_ARTICLE_FINISHED: {

            const loadedAuthors = action.payload.authors

            let newById = {...state.byId}

            loadedAuthors.forEach(author => {
                newById[author.id] = author
            })

            return {...state, byId: newById}
        }

        default: return state
    }
}

export default reducer