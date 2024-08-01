import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import CameraTest from '../../components/image/CameraTest';
import VoiceTest from '../../components/voice/VoiceTest';

const Stack = createNativeStackNavigator()

const UploadNavigation = ()=>{
    return(
        <Stack.Navigator initialRouteName='Picture'>
            <Stack.Screen name='Picture' component={CameraTest} options={{ headerShown: false }} />
            <Stack.Screen name='Upload' component={VoiceTest} options={{headerShown: false}}/>
        </Stack.Navigator>
    )
}

export default UploadNavigation