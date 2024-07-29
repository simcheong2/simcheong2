import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import MyPageScreen from '../../page/myPage/screen/MyPageScreen';
import MyFeedScreen from '../../page/myPage/screen/MyFeedScreen';

const Stack = createNativeStackNavigator();

const MyPageNavigation = () => {
    return(
        <Stack.Navigator initialRouteName="Profile">
            <Stack.Screen name="Profile" component={MyPageScreen} options={{ headerShown: false }}/>
            <Stack.Screen name="Feed" component={MyFeedScreen} options={{ headerShown: false }}/>
        </Stack.Navigator>
    )
}

export default MyPageNavigation