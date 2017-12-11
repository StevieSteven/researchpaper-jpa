// @flow

import React from 'react';


import type {
    Article
} from '../types'


import ArticlePreview from './ArticlePreview'

type Props = {
    articles: Array<Article>,
    loading: Boolean
}

const styles = {
    article: {
        marginBottom: "2em",
    }
}

const ArticleList = (props: Props) => (
    <div>
        { props.articles.map(article => (
            <div style={styles.article} key={article.id}>
                <ArticlePreview
                    permalink={article.permalink}
                    title={article.title}
                    releaseDate={article.releaseDate}
                    teaser={article.teaser}
                    authors={article.authors}
                />
            </div>
            )
        )}

        <p>loading: { props.loading ? 'true' : 'false' } </p>
    </div>
)

export default ArticleList