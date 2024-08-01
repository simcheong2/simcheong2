import type { CompositeNavigationProp } from '@react-navigation/native';
import type { NativeStackNavigationProp } from '@react-navigation/native-stack';
import type { BottomTabNavigationProp } from '@react-navigation/bottom-tabs';
import { MyProfile } from '../interface/user/Profile';
import { SelectType } from './SelectType';

type RootStackParamList = {
  Signup: undefined;
  Login : undefined;
  BottomNavigation : undefined;
  Follow : {isMine: SelectType};
};

type FeedStackParamList = {
  Profile: undefined;
  Feed: { profile: MyProfile };
  Edit: { profile: MyProfile };
}

type OtherFeedStackParamList = {
  Other: undefined;
  OtherProfile: { select: 'my' | 'other', nickname: string };
  OtherFeed: { profile: MyProfile };
}

type UploadStackParamList = {
  Picture: undefined;
  Upload: { photo: string[] };
}

type BottomTabParamList = {
  Home: {upload: boolean};
  Search: undefined;
  Photo: undefined;
  MyPage: undefined;
};

export type ScreenNavigationProp = CompositeNavigationProp<
  BottomTabNavigationProp<BottomTabParamList, 'Home'>,
  NativeStackNavigationProp<RootStackParamList>
>;

export type FeedNavigationProp = NativeStackNavigationProp<FeedStackParamList, 'Feed' | 'Edit' | 'Profile'>

export type OtherNavigationProp = NativeStackNavigationProp<OtherFeedStackParamList, 'OtherProfile' | 'OtherFeed'>

export type UploadNavigationProp = NativeStackNavigationProp<UploadStackParamList, 'Upload'>