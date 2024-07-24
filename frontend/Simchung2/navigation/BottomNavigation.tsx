import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import HomeScreen from "../page/HomeScreen";
import SearchScreen from "../page/SearchScreen";
import PhotoScreen from "../page/PhotoScreen";
import MyPageScreen from "../page/MypageScreen";
import Icon from "react-native-vector-icons/MaterialIcons";
import Signupscreen from "../page/SignupScreen";

const Tab = createBottomTabNavigator();

const BottomNavigation = () => {
    return(
        <Tab.Navigator
            initialRouteName="Home">
            <Tab.Screen 
                name="Home" 
                component={HomeScreen} 
                options={{
                    title: '홈',
                    tabBarIcon: ({color, size})=>(
                        <Icon name="home" color={color} size={size} />
                    )
                }}/>
            <Tab.Screen 
                name="Search" 
                component={SearchScreen} 
                options={{
                    title: '검색',
                    tabBarIcon: ({color, size})=>(
                        <Icon name="search" color={color} size={size} />
                    )
                }}/>
            <Tab.Screen 
                name="Photo" 
                component={PhotoScreen} 
                options={{
                    title: '사진등록',
                    tabBarIcon: ({color, size})=>(
                        <Icon name="add-photo-alternate" color={color} size={size} />
                    )
                }}/>
            <Tab.Screen 
                name="MyPage" 
                component={MyPageScreen} 
                options={{
                    title: '마이페이지',
                    tabBarIcon: ({color, size})=>(
                        <Icon name="people-alt" color={color} size={size} />
                    )
                }}/>
                 <Tab.Screen 
                name="SignUp" 
                component={Signupscreen} 
                options={{
                    title: '회원가입',
                    tabBarIcon: ({color, size})=>(
                        <Icon name="people-alt" color={color} size={size} />
                    )
                }}/>
        </Tab.Navigator>
    )
}

export default BottomNavigation