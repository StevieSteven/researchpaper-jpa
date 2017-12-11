// @flow

import React from 'react';
import {
    connect
} from 'react-redux'
import {
    selectors,
    actionCreators
} from '../store/articles'

import ArticleDetails from '../components/ArticleDetails'

import type {
    Article
} from '../types'



const ArticleLoading = () => (
    <div>
        <p>Article is loading</p>
    </div>
)

const mapStateToProps = (state) => {
    return {
        loading: state.articles.loading,
        article: selectors.selectedArticle(state)
    }
}

type Route = {
    isExact: boolean,
    location: {
        hash: string,
        key: any,
        pathname: string,
        query: any,
        search: string,
        state: any
    },
    params: any,
    pathname: string,
    patter: string
}

type Props = {
    loading: Boolean,
    dispatch: (action:any) => void,
    article: Article
} & Route

class ArticleDetailsPage extends React.Component {

    componentDidMount() {
        this.props.dispatch(actionCreators.loadArticleDetails(this.props.params.permalink))
    }

    render() {
        const article = this.props.article

        if(article) {
            return <ArticleDetails
                title={article.title}
                releaseDate={article.releaseDate}
                teaser={article.teaser}
                text={article.text}
                authors={article.authors}
            />
        } else {
            return <ArticleLoading/>
        }
    }
}

export default connect(mapStateToProps)(ArticleDetailsPage)