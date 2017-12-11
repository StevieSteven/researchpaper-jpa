// @flow

import React from 'react';

import R from 'ramda';

type Props = {
    authors: [{
        name: string
    }]
}

const Authors = ({authors}: Props) => {
    if(authors) {
        return <p>{R.join(', ', authors.map(a => a.name))}</p>;
    } else {
        return <p/>;
    }
}

export default Authors

