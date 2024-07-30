import React, { useEffect, useState } from 'react';
import Profile from '../Profile';
import { EmptyProfile, myProfile } from '../../../util/test/user/MyPageProfile';
import { MyProfile, Posts } from '../../../interface/user/Profile';
import { useNavigation } from '@react-navigation/native';
import { FeedNavigationProp } from '../../../types/navigationTypes';
import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { getStorage } from '../../../util/common/Storage.ts';
import { useRecoilValue } from 'recoil';
import accessTokenAtom from '../../../recoil/atom/accessTokenAtom';

const MyPageScreen = () => {
    const navigation = useNavigation<FeedNavigationProp>();
    const [filterProfile, setFilterProfile] = useState<MyProfile>(EmptyProfile);
    const [profile, setProfile] = useState<MyProfile>(EmptyProfile);
    const accessToken = useRecoilValue(accessTokenAtom);

    const onFilterHandler = (item: Posts) => {
        const updatedPosts = profile.posts.filter(post => post !== item);
        updatedPosts.unshift(item);
        const updatedProfile = { ...profile, posts: updatedPosts };
        setFilterProfile(updatedProfile);
        navigation.navigate('Feed', { profile: updatedProfile });
    };

    const onFeedHandler = () => {
        navigation.navigate('Feed', { profile: profile });
    };

    const onEditHandler = () => {
        navigation.navigate('Edit', { profile: profile });
    };

    useEffect(() => {
        setProfile(myProfile);
        axios.get('http://www.my-first-develop-library.shop:8080/users/my-page',{
            headers:{
                Authorization: `Bearer ${accessToken}`
            }
        }).then((response)=>{
            if(response.status===200){
                console.log(response.data);
                setProfile(response.data);
            }
            else{
                console.log(response.data);
            }
        }).catch((error)=>{
            console.log(error);
        })
        return () => {
            setProfile(EmptyProfile);
            console.log("Empty")
        };
    },[accessToken]);

    return (
        <Profile
            myProfile={profile}
            filterClick={onFilterHandler}
            feedClick={onFeedHandler}
            editClick={onEditHandler}
        />
    );
};

export default MyPageScreen;