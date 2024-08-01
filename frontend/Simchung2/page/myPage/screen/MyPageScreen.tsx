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
import Loading from '../../loading/Loading';

const MyPageScreen = ({ route = {} }) => {
    // @ts-ignore
    const { params } = route || {};
    const { select, nickname } = params || {};
    const [loading, setLoading] = useState<boolean>(false);
    const navigation = useNavigation<FeedNavigationProp>();
    const followNavigation = useNavigation<ScreenNavigationProp>();
    const [filterProfile, setFilterProfile] = useState<MyProfile>(EmptyProfile);
    const [profile, setProfile] = useState<MyProfile>(EmptyProfile);
    const accessToken = useRecoilValue(accessTokenAtom);
    const isFocused = useIsFocused();
    const [isMine, setIsMine] = useState<SelectType>({ select: 'my', follow: 'Follower', profile: EmptyProfile });
    const [followAction, setFollowAction] = useState<'follow' | 'follower' | null>(null);

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
        setLoading(true);

        const requestData = select === undefined ? {} : JSON.stringify({ nickname: nickname });

        axios.get(`http://www.my-first-develop-library.shop:8080/users/${select==undefined ? 'my' : 'other'}-page`, {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            },
            data: requestData
        }).then((response) => {
            if (response.status === 200) {
                setProfile(response.data);
            } else {
                console.log(response.data);
            }
            setLoading(false);
        }).catch((error) => {
            console.log(error);
            setLoading(false);
        });
        return () => {
            setProfile(EmptyProfile);
            setLoading(false);
        };
    }, [isFocused]);

    useEffect(() => {
        setIsMine(prevIsMine => ({
            ...prevIsMine,
            profile: profile
        }));
    }, [profile]);

    useEffect(() => {
        if (followAction) {
            followNavigation.navigate('Follow', { isMine: isMine });
            setFollowAction(null);
        }
    }, [isMine, followAction]);

    const onFollowHandler = (following: 'follow' | 'follower') => {
        switch (following) {
            case 'follow': {
                setIsMine(prevIsMine => ({
                    ...prevIsMine,
                    follow: 'Following'
                }));
                setFollowAction('follow');
                break;
            }
            case 'follower': {
                setIsMine(prevIsMine => ({
                    ...prevIsMine,
                    follow: 'Follower'
                }));
                setFollowAction('follower');
                break;
            }
        }
    };

    const FollowingHandle = () => {

    }

    return (
        loading ? <Loading /> :
            <Profile
                myProfile={profile}
                filterClick={onFilterHandler}
                feedClick={onFeedHandler}
                editClick={onEditHandler}
                followClick={onFollowHandler}
                followingClick={FollowingHandle}
                isMine={select === undefined ? true : false}
            />
    );
};

export default MyPageScreen;