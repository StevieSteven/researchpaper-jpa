// @flow

import type Action from '../../types/reduxTypes'

import R from 'ramda'

import {
    LOAD_ARTICLES_START,
    LOAD_ARTICLES_FINISHED,
    LOAD_ARTICLES_FAILED,

    LOAD_AUTHORS_FOR_ARTICLE_FINISHED,

    LOAD_ARTICLE_DETAILS_FINISHED,
    SELECT_ARTICLE,
} from './actionTypes'


const initialState = {
    byId: {},
    loading: false,
    selectedArticlePermalink: undefined,
}



const reducer = (state:any = initialState, action:Action) => {

    switch (action.type) {

        case SELECT_ARTICLE: {
            return {
                ...state,
                selectedArticlePermalink: action.payload.permalink
            }
        }


        case LOAD_ARTICLE_DETAILS_FINISHED: {
            const loadedArticleDetails = action.payload.article
            const existingArticle = state.byId[loadedArticleDetails.id]

            const newArticle = {
                ...existingArticle,
                text: loadedArticleDetails.text
            }

            return {
                ...state,
                byId: {
                    ...state.byId,
                    [newArticle.id]: newArticle
                }
            }

        }

        case LOAD_ARTICLES_START: {
            const newState = {...state}

            newState.loading = true

            return newState
        }

        case LOAD_ARTICLES_FINISHED: {
            const newState = {...state}

            const { articles } = action.payload

            let byId = {...state.byId}

            articles.forEach(article => {
                let oldArticle = byId[article.id];

                if(oldArticle) {
                    // articles from this Rest response will contain an empty
                    // authors array. If we already have authors loaded for
                    // this article we shouldn't overwrite it with this
                    // empty authors array
                    let {authors, ...clone} = article;

                    let newArticle = {...oldArticle, ...clone}

                    byId[article.id] = newArticle
                } else {
                    byId[article.id] = {...article}
                }
            })

            newState.byId = byId
            newState.loading = false

            return newState
        }

        case LOAD_ARTICLES_FAILED: {
            const newState = {...state}

            newState.loading = false

            return newState
        }

        case LOAD_AUTHORS_FOR_ARTICLE_FINISHED: {
            const articleId = action.payload.articleId
            const articleAuthors = action.payload.authors

            let article = state.byId[articleId]

            if(article) {
                let newArticleAuthors = [...article.authors]

                articleAuthors.map(author => author.id)
                    .forEach(authorId => {
                        if(!newArticleAuthors.includes(authorId)) {
                            newArticleAuthors.push(authorId)
                        }
                    })

                // define a simply sort function
                const sort = R.sort((a,b) => a - b)

                // only if the new authors array is different to the existing one
                // we need to return a new state.
                // we compare sorted arrays because the order of authors isn't relevant.
                if(! R.equals(sort(article.authors) , sort(newArticleAuthors))) {
                    return {
                        ...state,
                        byId: {
                            ...state.byId,
                            [article.id]: {
                                ...article,
                                authors: newArticleAuthors
                            }
                        }
                    }
                }
            }

            return state
        }

        default: return state
    }
}

export default reducer