// @flow

import React from 'react'
import {
    connect
} from 'react-redux'

import {
    selectors,
    actionCreators
} from '../store/articles'

import ArticleList from '../components/ArticleList'


const mapStateToProps = (state) => {
    return {
        articles: selectors.fullArticles(state),
        loading: state.articles.loading
    }
}

class ArticleListWithInit extends React.Component {
    componentWillMount() {
        this.props.dispatch(actionCreators.loadArticles())
    }

    render() {
        return <ArticleList {...this.props} />
    }
}

const ArticleOverviewPage = connect(
    mapStateToProps,
)(ArticleListWithInit)

export default ArticleOverviewPage
