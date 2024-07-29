import React, { useEffect, useState } from 'react';
import Profile from '../Profile';
import { EmptyProfile, myProfile } from '../../../util/test/user/MyPageProfile';
import { MyProfile, Posts } from '../../../interface/user/Profile';
import { useNavigation } from '@react-navigation/native';
import { FeedNavigationProp } from '../../../types/navigationTypes';

const MyPageScreen = () => {
    const navigation = useNavigation<FeedNavigationProp>();
    const [filterProfile, setFilterProfile] = useState<MyProfile>(EmptyProfile);
    const [profile, setProfile] = useState<MyProfile>(EmptyProfile);

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
        return () => {
            setProfile(EmptyProfile);
            console.log("Empty")
        };
    });

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