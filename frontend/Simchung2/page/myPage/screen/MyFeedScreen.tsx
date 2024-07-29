import React from 'react';
import Posts from '../Posts';

// @ts-ignore
const MyFeedScreen = ({route}) => {
    const {profile} = route.params;
    return(
        <Posts profile={profile}/>
    )
}

export default MyFeedScreen