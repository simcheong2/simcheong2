import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import BottomNavigation from './navigation/BottomNavigation';
import StackNavigation from './navigation/StackNavigation';
import { RecoilRoot } from 'recoil';
import { StatusBar } from 'expo-status-bar';

const App = () => {
    return (
        <NavigationContainer>
            <RecoilRoot>
                <StatusBar/>
                <StackNavigation />
            </RecoilRoot>
        </NavigationContainer>
    );
};

export default App;