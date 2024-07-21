import React from 'react'; 
import BottomNavigation from './navigation/BottomNavigation';
import { NavigationContainer } from '@react-navigation/native';

const App = () => {
  return (    
    <NavigationContainer>
      <BottomNavigation/>
    </NavigationContainer>
  );
}

export default App