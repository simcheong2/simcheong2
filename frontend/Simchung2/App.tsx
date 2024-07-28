import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import BottomNavigation from './navigation/BottomNavigation';
import StackNavigation from './navigation/StackNavigation';
import { RecoilRoot } from 'recoil';

const App = () => {
    return (
        <NavigationContainer>
            <RecoilRoot>
                <StackNavigation />
            </RecoilRoot>
        </NavigationContainer>
    );
};

export default App;