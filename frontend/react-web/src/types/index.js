// @flow


export type Article = {
    id: string,
    permalink: string,
    link: string,
    title: string,
    teaser: string,
    text: string,
    releaseDate: Date,
    authors: Array<Author>,
    detailsLoaded: boolean
}

export type Author = {
    id: string,
    name: string,
    link: string,
}