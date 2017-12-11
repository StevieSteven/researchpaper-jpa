// @flow

import React from 'react';

import { Link } from 'react-router'

import Authors from './Authors'

type Props = {
    permalink: string,
    title: string,
    releaseDate: Date,
    teaser: string,
    authors: [{
        name: string
    }]
}

const ArticlePreview = (props: Props) => (
    <article>
        <header>
            <Link to={`/articles/${props.permalink}`}>
                <h1>{props.title}</h1>
            </Link>
            <Authors authors={props.authors}/>
            <p>{props.releaseDate.toLocaleDateString()} - {props.releaseDate.toLocaleTimeString()} | {props.authors.length} Kommentare</p>
        </header>

        <section>
            { props.teaser }
        </section>

        <p><Link to={`/articles/${props.permalink}`}>read more...</Link></p>
    </article>
)


export default ArticlePreview