import React from 'react';
import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';
import { MyProfile } from '../../interface/user/Profile';
import Follower from '../../page/follow/Follower';
import Following from '../../page/follow/Following';
import { Follows } from '../../page/FollowScreen';
import { FollowProvider } from '../../components/context/FollowContext';

const Tab = createMaterialTopTabNavigator();

interface FollowTabProps {
    profile: MyProfile;
    follow: 'Following' | 'Follower';
    follows: Follows;
}

const FollowTab = ({ profile, follow, follows }: FollowTabProps) => {
    return (
        <FollowProvider value={follows}>
            <Tab.Navigator
                initialRouteName={`${follow}`}
                screenOptions={{
                    swipeEnabled: false,
                    tabBarIndicatorStyle: {
                        backgroundColor: '#555555', // 하단 경계선의 색상을 검은색으로 변경
                        width: '10%', // 경계선의 너비를 10%로 설정
                        left: '20%',
                    },
                    tabBarActiveTintColor: '#555555', // 활성화된 탭의 텍스트 색상을 검은색으로 변경
                    tabBarInactiveTintColor: 'gray', // 비활성화된 탭의 텍스트 색상을 회색으로 설정
                    tabBarLabelStyle: {
                        fontSize: 16, // 탭의 텍스트 크기를 16으로 설정
                    },
                }}>
                <Tab.Screen
                    name="Follower"
                    component={Follower}
                    options={{
                        title: `${profile.profile.followerCount}팔로워`,
                    }} />
                <Tab.Screen
                    name="Following"
                    component={Following}
                    options={{
                        title: `${profile.profile.followingCount}팔로우`,
                    }} />
            </Tab.Navigator>
        </FollowProvider>
    );
};

export default FollowTab;