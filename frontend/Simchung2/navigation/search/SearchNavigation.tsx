import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import MyPageScreen from '../../page/myPage/screen/MyPageScreen';
import MyFeedScreen from '../../page/myPage/screen/MyFeedScreen';
import SearchScreen from '../../page/search/SearchScreen';

const Stack = createNativeStackNavigator();
const SearchNavigation = () => {
    return(
        <Stack.Navigator initialRouteName='Other'>
            <Stack.Screen name='Other' component={SearchScreen} options={{ headerShown: false }}/>
            <Stack.Screen name="OtherProfile" component={MyPageScreen} options={{ headerShown: false }}/>
            <Stack.Screen name="OtherFeed" component={MyFeedScreen} options={{ headerShown: false }}/>
        </Stack.Navigator>
    )
}

export default SearchNavigation