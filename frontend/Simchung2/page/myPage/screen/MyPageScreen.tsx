import React, { useEffect, useState } from 'react';
import Profile from '../Profile';
import { EmptyProfile, myProfile } from '../../../util/test/user/MyPageProfile';
import { MyProfile, Posts } from '../../../interface/user/Profile';
import { useIsFocused, useNavigation } from '@react-navigation/native';
import { FeedNavigationProp, ScreenNavigationProp } from '../../../types/navigationTypes';
import axios from 'axios';
import { useRecoilValue } from 'recoil';
import accessTokenAtom from '../../../recoil/atom/accessTokenAtom';
import { SelectType } from '../../../types/SelectType';

const MyPageScreen = () => {
    const navigation = useNavigation<FeedNavigationProp>();
    const followNavigation = useNavigation<ScreenNavigationProp>()
    const [filterProfile, setFilterProfile] = useState<MyProfile>(EmptyProfile);
    const [profile, setProfile] = useState<MyProfile>(EmptyProfile);
    const accessToken = useRecoilValue(accessTokenAtom);
    const isFocused = useIsFocused();
    const [isMine, setIsMine] = useState<SelectType>({select: 'other'})


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
    },[isFocused]);

    const onFollowHandler = (follow:'follow'|'follower') => {
        switch (follow){
            case 'follow': {
                followNavigation.navigate('Follow', {isMine: isMine})
                break;
            }
            case 'follower':{
                followNavigation.navigate('Follow', {isMine: isMine})
                break;
            }
        }
    }

    return (
        <Profile
            myProfile={profile}
            filterClick={onFilterHandler}
            feedClick={onFeedHandler}
            editClick={onEditHandler}
            followClick={onFollowHandler}
        />
    );
};

export default MyPageScreen;