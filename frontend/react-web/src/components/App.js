// @flow

import React from 'react';

import {
    BrowserRouter,
    Match,
    Miss,
    Redirect
} from 'react-router'

import Navigation from './Navigation'

import ArticleOverviewPage from '../container/ArticleOverviewPage'
import ArticleDetailsPage from '../container/ArticleDetailsPage'


const NoMatch = () => <p>404</p>

const Authors = () => <p>Authors</p>

const App = () => (
    <BrowserRouter>
        <div>
            <Navigation/>
            <div className="container">
                <Match exactly pattern="/" render={() => (
                    <Redirect to="/articles" />
                )} />
                <Match pattern="/articles/:permalink" component={ArticleDetailsPage} />
                <Match exactly pattern="/articles" component={ArticleOverviewPage} />
                <Match exactly pattern="/articles/" component={ArticleOverviewPage} />
                <Match pattern="/authors" component={Authors} />

                <Miss component={NoMatch}/>
            </div>
        </div>
    </BrowserRouter>
)

export default App