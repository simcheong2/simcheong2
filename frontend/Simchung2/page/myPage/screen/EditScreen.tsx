import React from 'react';
import Edit from '../edit/Edit';

// @ts-ignore
const EditScreen = ({route}) => {
    const {profile} = route.params;

    return(
        <Edit profile={profile}/>
    )
}

export default EditScreen