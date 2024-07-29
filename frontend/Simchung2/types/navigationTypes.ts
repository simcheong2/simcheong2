import type { CompositeNavigationProp } from '@react-navigation/native';
import type { NativeStackNavigationProp } from '@react-navigation/native-stack';
import type { BottomTabNavigationProp } from '@react-navigation/bottom-tabs';
import { MyProfile } from '../interface/user/Profile';

type RootStackParamList = {
  Signup: undefined;
  Login : undefined;
  Home : undefined;
};

type FeedStackParamList = {
  Profile: undefined;
  Feed: { profile: MyProfile };
  Edit: { profile: MyProfile };
}

type BottomTabParamList = {
  Home: undefined;
  Search: undefined;
  Photo: undefined;
  MyPage: undefined;
};

export type ScreenNavigationProp = CompositeNavigationProp<
  BottomTabNavigationProp<BottomTabParamList>,
  NativeStackNavigationProp<RootStackParamList>
>;

export type FeedNavigationProp = NativeStackNavigationProp<FeedStackParamList, 'Feed' | 'Edit'>
