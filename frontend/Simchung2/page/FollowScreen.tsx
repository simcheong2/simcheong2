import React, { useEffect, useState } from 'react';
import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import Icon from 'react-native-vector-icons/MaterialIcons';
import { useNavigation } from '@react-navigation/native';
import { ScreenNavigationProp } from '../types/navigationTypes';
import FollowTab from '../navigation/home/FollowTab';
import axios from 'axios';
import { useRecoilValue } from 'recoil';
import accessTokenAtom from '../recoil/atom/accessTokenAtom';
import Loading from './loading/Loading';
import { Follow, Follower, OtherFollow, OtherFollower } from '../interface/follow/follow';
import { FollowContext, FollowProvider } from '../components/context/FollowContext';

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        width: '100%',
        height: '100%',
        flexDirection: 'column',
    },
    header: {
        width: '100%',
        height: 69,
        marginTop: 44,
        padding: 12,
        flexDirection: 'row',
        alignItems: 'center',
        borderBottomWidth: 1,
        borderBottomColor: '#EFF3F1',
    },
    tab: {
        width: '100%',
        height: '95%',
    },
    'nickname-container': {
        width: '100%',
        alignItems: 'center',
        justifyContent: 'center',
    },
    nickname: {
        fontSize: 16,
        marginRight: 68,
    },
});

export interface Follows {
    follows: Follow | OtherFollow,
    followers: Follower | OtherFollower
    select: 'my' | 'other'
}

// @ts-ignore
const FollowScreen = ({ route }) => {
    const [loading, setLoading] = useState<boolean>(false);
    const baseURL = 'http://www.my-first-develop-library.shop:8080/';
    const { isMine } = route.params;
    const navigation = useNavigation<ScreenNavigationProp>();
    const accessToken = useRecoilValue(accessTokenAtom);
    const [following, setFollowing] = useState<Follow | OtherFollow>();
    const [follower, setFollower] = useState<Follower | OtherFollower>();

    useEffect(() => {
        setLoading(true);
        const follow = axios.get(`${baseURL}follow/${isMine.select}-followers`, {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            },
            params: isMine.select !== 'my' ? { nickname: isMine.profile.profile.nickname } : {},
        }).then((response) => {
            setFollower(response.data);
            console.log(response.data);
            setLoading(false);
        }).catch((error) => {
            console.log(error.data);
            setLoading(false);
        });
        return () => {
            setLoading(false);
        };
    }, [accessToken]);

    useEffect(() => {
        setLoading(true);
        const follow = axios.get(`${baseURL}follow/${isMine.select}-follows`, {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            },
            params: isMine.select !== 'my' ? { nickname: isMine.profile.profile.nickname } : {},
        }).then((response) => {
            setFollowing(response.data);
            console.log(response.data);
            setLoading(false);
        }).catch((error) => {
            console.log(error.data);
            setLoading(false);
        });
        return () => {
            setLoading(false);
        };
    }, [accessToken]);

    const userFollows: Follows = {
        follows: following!,
        followers: follower!,
        select: isMine.select,
    };

    return (
        loading ? <Loading /> :
            <View style={styles.container}>
                <View style={styles.header}>
                    <TouchableOpacity onPress={() => navigation.goBack()}>
                        <Icon name="chevron-left" size={36} />
                    </TouchableOpacity>
                    <View style={styles['nickname-container']}>
                        <Text style={styles.nickname}>{isMine.profile.profile.nickname}</Text>
                    </View>
                </View>
                <View style={styles.tab}>
                    <FollowTab profile={isMine.profile} follow={isMine.follow} follows={userFollows}/>
                </View>
            </View>
    );
};

export default FollowScreen;