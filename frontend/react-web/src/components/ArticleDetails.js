// @flow

import React from 'react';

import Authors from './Authors'

type Props = {
    title: string,
    releaseDate: Date,
    teaser: string,
    text: string,
    authors: [{
        name: string
    }]
}


const styles = {
    teaser: {
        fontWeight: 'bold'
    },
    content: {
    }
}

const ArticleDetails = (props: Props) => (
    <article>
        <header>
            <h1>{props.title}</h1>
            <Authors authors={props.authors}/>
        </header>
        <section style={styles.teaser}>
            {props.teaser}
        </section>
        <section style={styles.content}>
            {props.text}
        </section>
    </article>
)

export default ArticleDetails

