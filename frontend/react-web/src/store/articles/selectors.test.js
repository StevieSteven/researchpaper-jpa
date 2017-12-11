import {
    createAuthor,
    createArticle
} from '../testDataGenerator'

import deepFreeze from 'deep-freeze'

import {
    fullArticles
} from './selectors'


const createTestData = () => {
    const author1 = createAuthor("author1");
    const author2 = createAuthor("author2");
    const author3 = createAuthor("author3");

    const article1 = createArticle([author1], "article1");
    const article2 = createArticle([author2], "article2");
    const article3 = createArticle([author1, author2], "article3");
    const article4 = createArticle([author3], "article4");

    return {
        authors: {
            byId: {
                "author1": author1,
                "author2": author2,
                "author3": author3,
            }
        },
        articles: {
            byId:{
                "article1": article1,
                "article2": article2,
                "article3": article3,
                "article4": article4,
            }
        }
    }
}


describe('fullArticles selector', () => {
    it('should sort articles', () => {

        const state = createTestData();

        state.articles.byId.article1.releaseDate = new Date(2016, 11, 15, 3, 15, 0)
        state.articles.byId.article2.releaseDate = new Date(2016, 11, 17, 4, 23, 0)
        state.articles.byId.article3.releaseDate = new Date(2016, 11, 17, 3, 15, 0)
        state.articles.byId.article4.releaseDate = new Date(2016, 11, 13, 1, 15, 0)


        deepFreeze(state)

        const articles = fullArticles(state);


        expect(articles).toHaveLength(4)
        expect(articles.map(article => article.id)).toEqual(["article4", "article1", "article3", "article2"])
    })

    it('should return articles with complete authors', () => {
        const state = createTestData()
        state.articles.byId.article1.releaseDate = new Date(2016, 11, 1, 3, 15, 0)
        state.articles.byId.article2.releaseDate = new Date(2016, 11, 2, 4, 23, 0)
        state.articles.byId.article3.releaseDate = new Date(2016, 11, 3, 3, 15, 0)
        state.articles.byId.article4.releaseDate = new Date(2016, 11, 4, 1, 15, 0)

        deepFreeze(state)

        const articles = fullArticles(state)

        expect(articles).toHaveLength(4)

        const article1 = articles[0];
        const article2 = articles[1];
        const article3 = articles[2];
        const article4 = articles[3];

        expect(article1.authors).toHaveLength(1)
        expect(article1.authors[0]).toEqual(state.authors.byId.author1)
        expect(article1.authors).toContainEqual(state.authors.byId.author1)

        expect(article2.authors).toHaveLength(1)
        expect(article2.authors).toContainEqual(state.authors.byId.author2)

        expect(article3.authors).toHaveLength(2)
        expect(article3.authors).toContainEqual(state.authors.byId.author1, state.authors.byId.author2)
    })
})
