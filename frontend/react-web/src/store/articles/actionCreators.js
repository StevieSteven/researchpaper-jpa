// @flow

import type {
    DispatchFunction
} from '../../types/reduxTypes'

import {
    LOAD_ARTICLES_START,
    LOAD_ARTICLES_FINISHED,
    LOAD_ARTICLES_FAILED,

    LOAD_AUTHORS_FOR_ARTICLE_START,
    LOAD_AUTHORS_FOR_ARTICLE_FINISHED,
    LOAD_AUTHORS_FOR_ARTICLE_FAILED,

    LOAD_ARTICLE_DETAILS_START,
    LOAD_ARTICLE_DETAILS_FINISHED,
    LOAD_ARTICLE_DETAILS_FAILED,

    SELECT_ARTICLE
} from './actionTypes'

/**
 * Converts a JSON representation of an article returned by the REST API to
 * an Article instance used by this client application.
 * This means - amongst others - to remove Hyperlink URLs and create JavaScript Date instances for date strings.
 * @param articleJson
 */
const convertArticleResponse = (articleJson) => {
    let article = {...articleJson}

    article.link = articleJson._links.self.href
    article.releaseDate = new Date(articleJson.releaseDate)
    article.authors = []

    delete article._links

    return article
}

const convertAuthorResponse = (authorJson) => {
    let author = {...authorJson}
    author.link = authorJson._links.self.href

    delete author._links

    return author
}


export const loadArticleDetails = (permalink: string) => {
    return (dispatch: DispatchFunction, getState: () => any) => {
        dispatch({
            type: SELECT_ARTICLE,
            payload: {
                permalink
            }
        })

        dispatch({
            type: LOAD_ARTICLE_DETAILS_START,
            payload: {
                permalink
            }
        })

        fetch(`/api/rest/articles/search/findByPermalink?permalink=${permalink}`)
            .then(response => response.json())
            .then(json => {
                dispatch({
                    type: LOAD_ARTICLE_DETAILS_FINISHED,
                    payload: {
                        article: convertArticleResponse(json)
                    }
                })
            }, error => {
                dispatch({
                    type: LOAD_ARTICLE_DETAILS_FAILED,
                    payload: {
                        error
                    }
                })
            })
    }
}

export const loadArticles = (page?: number = 0) => {
    return (dispatch: DispatchFunction) => {
        dispatch({
            type: LOAD_ARTICLES_START,
        })

        fetch(`/api/rest/articles?page=${page}`)
            .then(response => response.json())
            .then(json => {
                const articlesResponse = json._embedded.articles

                const articles = articlesResponse.map(convertArticleResponse)


                json._embedded.articles
                    .forEach(article => {
                        const authorLink = article._links.authors.href

                        dispatch({
                            type: LOAD_AUTHORS_FOR_ARTICLE_START,
                            payload: {
                                authorLink
                            }
                        })

                        fetch(`/api/rest/articles/${article.id}/authors`)
                        // fetch(authorLink)  // TODO find a way to rewrite proxy referrer so that the link points to the correct server/port
                            .then(response => response.json())
                            .then(json => {
                                const authorsResponse = json._embedded.authors
                                const authors = authorsResponse.map(convertAuthorResponse)

                                dispatch({
                                    type: LOAD_AUTHORS_FOR_ARTICLE_FINISHED,
                                    payload: {
                                        articleId: article.id,
                                        authors
                                    }
                                })
                            }, error => {
                                dispatch({
                                    type: LOAD_AUTHORS_FOR_ARTICLE_FAILED,
                                    payload: {
                                        error
                                    }
                                })
                            })
                    })

                dispatch({
                    type:LOAD_ARTICLES_FINISHED,
                    payload: {
                        articles: articles
                    }
                })
            }, error => {
                dispatch({
                    type: LOAD_ARTICLES_FAILED,
                    payload: {
                        error
                    }
                })
            })
    }
}