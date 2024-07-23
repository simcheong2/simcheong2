import React from "react";
import Recommend from "../../page/home/Recommend";
import Follow from "../../page/home/Follow";
import { createMaterialTopTabNavigator } from "@react-navigation/material-top-tabs";

const Tab = createMaterialTopTabNavigator();

const Tabs = () => {
    return(
        <Tab.Navigator 
            initialRouteName="Follow"
            screenOptions={{
                tabBarIndicatorStyle: {
                    backgroundColor: '#555555', // 하단 경계선의 색상을 검은색으로 변경
                    width: '10%', // 경계선의 너비를 10%로 설정
                    left: '20%', 
                },
                tabBarActiveTintColor: '#555555', // 활성화된 탭의 텍스트 색상을 검은색으로 변경
                tabBarInactiveTintColor: 'gray', // 비활성화된 탭의 텍스트 색상을 회색으로 설정
                tabBarLabelStyle: {
                    fontSize: 16, // 탭의 텍스트 크기를 16으로 설정
                },
            }}>
            <Tab.Screen 
                name="Recommend" 
                component={Recommend} 
                options={{
                    title: "추천"
                }}/>
            <Tab.Screen 
                name="Follow"
                component={Follow} 
                options={{
                    title: "팔로우"
                }}/>
        </Tab.Navigator>
    );
}

export default Tabs