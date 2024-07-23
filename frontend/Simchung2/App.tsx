import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import BottomNavigation from './navigation/BottomNavigation';
import StackNavigation from './navigation/StackNavigation';

const App = () => {
  return (    
    <NavigationContainer>
      <StackNavigation/>
    </NavigationContainer>
  );
}

export default App