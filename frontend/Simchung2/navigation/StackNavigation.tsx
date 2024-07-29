import { createNativeStackNavigator } from "@react-navigation/native-stack";
import React from "react";
import BottomNavigation from "./BottomNavigation";
import SignupScreen from "../page/SignupScreen";
import LoginScreen from "../page/LoginScreen";
import HomeScreen from "../page/home/HomeScreen";
import FollowScreen from "../page/FollowScreen";
import MangerScreen from "../page/ManagerScreen";

const Stack = createNativeStackNavigator();

const StackNavigation = () =>{
    return(
        <Stack.Navigator initialRouteName="Login">
            <Stack.Screen name="BottomNavigation" component={BottomNavigation} options={{ headerShown: false }}/>
            <Stack.Screen name="Signup" component={SignupScreen}   />
            <Stack.Screen name="Login" component={LoginScreen}   options={{ headerShown: false }} />
            <Stack.Screen name="Home" component={HomeScreen}    options={{ headerShown: false }}/>
            <Stack.Screen name="Follow" component={FollowScreen}   />
            <Stack.Screen name="Manager" component={MangerScreen}   />
        </Stack.Navigator>
    )
}

export default StackNavigation