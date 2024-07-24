import type { CompositeNavigationProp } from '@react-navigation/native';
import type { NativeStackNavigationProp } from '@react-navigation/native-stack';
import type { BottomTabNavigationProp } from '@react-navigation/bottom-tabs';

type RootStackParamList = {
  Signup: undefined;
};

type BottomTabParamList = {
  Home: undefined;
  Search: undefined;
  Photo: undefined;
  MyPage: undefined;
};

export type ScreenNavigationProp = CompositeNavigationProp<
  BottomTabNavigationProp<BottomTabParamList, 'MyPage','Signup'>,
  NativeStackNavigationProp<RootStackParamList>
>;
