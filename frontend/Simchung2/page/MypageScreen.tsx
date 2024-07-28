import React from 'react';
import { View, Button } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { ScreenNavigationProp } from '../types/navigationTypes';

function MyPageScreen() {
  const navigation = useNavigation< ScreenNavigationProp>();

  return (
    <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
      <Button
        onPress={() => navigation.navigate('Signup')}
        title="회원가입"
      />
      <Button
        onPress={() => navigation.navigate('Login')}
        title="로그인"
      />
    </View>
  );
}

export default MyPageScreen;
