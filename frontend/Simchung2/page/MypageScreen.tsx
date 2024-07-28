import React from 'react';
import { useNavigation } from '@react-navigation/native';
import { ScreenNavigationProp } from '../types/navigationTypes';
import MyPage from './myPage/MyPage';
import { myProfile } from '../util/test/user/MyPageProfile';

function MyPageScreen() {
  const navigation = useNavigation< ScreenNavigationProp>();

  return(
      <MyPage myProfile={myProfile}/>
  )
}

export default MyPageScreen;
