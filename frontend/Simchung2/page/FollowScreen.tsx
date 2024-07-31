import React from "react";
import { Text } from "react-native";
import { SelectType } from '../types/SelectType';

// @ts-ignore
function FollowScreen({route}) {
    const {isMine} = route.params;

    return(
        <Text>{isMine.select}</Text>
    )
}

export default FollowScreen