import { NavigationContainer } from "@react-navigation/native";
import { createStackNavigator } from "@react-navigation/stack";
import React from "react";
import Signupscreen from "../page/SignupScreen";
import MyPageScreen from "../page/MypageScreen";
const Stack = createStackNavigator();

const StackNavigation = () =>{
    return(
      
            <Stack.Navigator initialRouteName="MyPageScreen">
                 <Stack.Screen name="MyPageScreen" component={MyPageScreen} />
                <Stack.Screen name="signup" component={Signupscreen}/>
            </Stack.Navigator>
       
    )
}

export default StackNavigation