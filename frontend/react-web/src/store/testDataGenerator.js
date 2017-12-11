// @flow

import Faker from 'faker';

export const createAuthor = (id:string = Faker.random.uuid()) => ({
    id: id,
    name: Faker.name.firstName()
})

export const createArticle = (authors:[{id:string}], id:string = Faker.random.uuid()) => ({
    id: id,
    title: Faker.lorem.sentence(),
    teaser: Faker.lorem.paragraph(),
    text: Faker.lorem.paragraphs(3),
    releaseDate: Faker.date.recent(),
    authors: [...authors.map(a => a.id)]
})


