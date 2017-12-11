// @flow

import {
    createAuthor,
    createArticle
} from './testDataGenerator'


const author1 = createAuthor();
const author2 = createAuthor();
const author3 = createAuthor();

const article1 = createArticle([author1]);
const article2 = createArticle([author2, author3]);
const article3 = createArticle([author2]);
const article4 = createArticle([author1, author3]);

const initialState = {
    authors: {
        byId: {
            [author1.id]: author1,
            [author2.id]: author2,
            [author3.id]: author3,
        },
        loading: false,
    },
    articles: {
        byId: {
            [article1.id]: article1,
            [article2.id]: article2,
            [article3.id]: article3,
            [article4.id]: article4,
        },
        loading: false,
    }
}

export default initialState;