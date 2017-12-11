// @flow

import R from 'ramda'

import {
    createSelector
} from 'reselect'

export const selectedArticle = createSelector(
    state => state.articles.byId,
    state => state.articles.selectedArticlePermalink,
    state => state.authors.byId,
    (articles, selectedArticlePermalink, authors) => {
        const matchingArticles = R.values(articles).filter(article => article.permalink === selectedArticlePermalink)

        const article = (matchingArticles && matchingArticles.length > 0) ? matchingArticles[0] : undefined;

        if(article) {
            return fillArticleWithAuthors(article, authors);
        }

        return undefined
    }
)


const fillArticleWithAuthors = (article, authors) => {
    let newArticle = {...article}
    newArticle.authors = article.authors.map(authorId => authors[authorId])
    return newArticle
}

export const fullArticles = createSelector(
    state => state.articles.byId,
    state => state.authors.byId,
    (articles, authors) => {
        const articlesArray = R.values(articles)

        const sortedArray = articlesArray.sort((a,b) => a.releaseDate - b.releaseDate)

        const articlesWithCompleteAuthors = sortedArray.map(article => {
            return fillArticleWithAuthors(article, authors)
        })

        return articlesWithCompleteAuthors;
    }
)