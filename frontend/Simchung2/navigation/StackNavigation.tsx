import { createNativeStackNavigator } from "@react-navigation/native-stack";
import React from "react";
import BottomNavigation from "./BottomNavigation";
import SignupScreen from "../page/SignupScreen";

const Stack = createNativeStackNavigator();

const StackNavigation = () =>{
    return(
        <Stack.Navigator>
            <Stack.Screen name="BottomNavigation" component={BottomNavigation} options={{ headerShown: false }}/>
            <Stack.Screen name="Signup" component={SignupScreen}  options={{ headerShown: false }} />
        </Stack.Navigator>
    )
}

export default StackNavigation