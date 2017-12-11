import React from 'react';
import ReactDOM from 'react-dom';

import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-theme.css';


import App from './components/App';

import { Provider } from 'react-redux'


import configureStore from './configureStore'

import initialState from './store/initialState'
// import initialState from './store/initialStateTestData'

const store = configureStore(initialState);

ReactDOM.render(
    <Provider store={store}>
        <App />
    </Provider>,
  document.getElementById('root')
);
