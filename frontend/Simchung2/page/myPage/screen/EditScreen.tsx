import React from 'react';
import { Text, View } from 'react-native';

// @ts-ignore
const EditScreen = ({route}) => {
    const {profile} = route.params;

    return(
        <View>
            <Text>텍스트</Text>
        </View>
    )
}

export default EditScreen