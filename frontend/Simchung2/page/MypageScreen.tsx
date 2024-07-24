import React from 'react';
import { useNavigation } from '@react-navigation/native';
import { ScreenNavigationProp } from '../types/navigationTypes';
import Profile from './myPage/Profile';
import { myProfile } from '../util/test/user/MyPageProfile';
import MyPageNavigation from '../navigation/home/MyPageNavigation';

function MyPageScreen() {

  return(
      <MyPageNavigation/>
  )
}

export default MyPageScreen;
