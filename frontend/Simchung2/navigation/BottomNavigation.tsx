import React from 'react';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import Icon from 'react-native-vector-icons/MaterialIcons';
import SearchScreen from '../page/SearchScreen';
import PhotoScreen from '../page/PhotoScreen';
import MyPageScreen from '../page/MypageScreen';
import HomeScreen from '../page/home/HomeScreen';
import { GestureHandlerRootView } from 'react-native-gesture-handler';
import CustomSheet from '../components/bottomSheet/CustomSheet';
import FeedComment from '../page/comment/FeedComment';

const Tab = createBottomTabNavigator();

const BottomNavigation = () => {
    return (
        <GestureHandlerRootView style={{ flex: 1 }}>
            <Tab.Navigator
                initialRouteName="Home"
                screenOptions={{
                    unmountOnBlur: true
                }}>
                <Tab.Screen
                    name="Home"
                    component={HomeScreen}
                    options={{
                        title: '홈',
                        tabBarIcon: ({ color, size }) => (
                            <Icon name="home" color={color} size={size} />
                        ),
                        headerShown: false,
                    }} />
                <Tab.Screen
                    name="Search"
                    component={SearchScreen}
                    options={{
                        title: '검색',
                        tabBarIcon: ({ color, size }) => (
                            <Icon name="search" color={color} size={size} />
                        ),
                        headerShown: false,
                    }}
                />
                <Tab.Screen
                    name="Photo"
                    component={PhotoScreen}
                    options={{
                        title: '사진등록',
                        tabBarIcon: ({ color, size }) => (
                            <Icon name="add-photo-alternate" color={color} size={size} />
                        ),
                        headerShown: false,
                    }} />
                <Tab.Screen
                    name="MyPage"
                    component={MyPageScreen}
                    options={{
                        title: '마이페이지',
                        tabBarIcon: ({ color, size }) => (
                            <Icon name="people-alt" color={color} size={size} />
                        ),
                        headerShown: false,
                    }} />
            </Tab.Navigator>
            <CustomSheet snapPoint={['25%','50%','95%']}>
                <FeedComment/>
            </CustomSheet>
        </GestureHandlerRootView>
    );
};

export default BottomNavigation;