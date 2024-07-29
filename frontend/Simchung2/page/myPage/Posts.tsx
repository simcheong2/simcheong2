import React, { useState } from 'react';
import { StyleSheet, Text, View, TouchableOpacity, SafeAreaView, ScrollView } from 'react-native';
import { MyProfile } from '../../interface/user/Profile';
import Icon from 'react-native-vector-icons/MaterialIcons';
import { useNavigation } from '@react-navigation/native';
import { FeedNavigationProp } from '../../types/navigationTypes';
import { useRecoilState } from 'recoil';
import modalAtom from '../../recoil/modalAtom';
import { Comments } from '../../interface/feed/Feed';
import commentsAtom from '../../recoil/atom/commentsAtom';
import Post from './Post';

interface FeedProps {
    profile: MyProfile;
}

const Posts = ({ profile }: FeedProps) => {
    const [modal, setModal] = useRecoilState<number>(modalAtom);

    const [comments, setComments] = useRecoilState<Comments[]>(commentsAtom);

    const handleComment = () => {
        setModal(3);
    };

    const navigation = useNavigation<FeedNavigationProp>();
    return (
        <View style={styles.container}>
            <View style={styles.header}>
                <TouchableOpacity onPress={() => navigation.goBack()}>
                    <Icon name="chevron-left" size={48} />
                </TouchableOpacity>
                <View style={styles['header-container']}>
                    <Text style={styles['header-title']}>게시글</Text>
                </View>
            </View>
            {profile.posts ?
                (<SafeAreaView style={styles.container}>
                    <ScrollView style={styles.scrollView}>
                        {profile.posts.map((post, index) => (
                            <Post key={index} post={post} onPress={handleComment} profile={profile.profile} />
                        ))}
                    </ScrollView>
                </SafeAreaView>) :
                (
                    <View style={styles.noFeed}>
                        <Text>친구 없으면</Text>
                    </View>
                )
            }
        </View>
    );
};

const styles= StyleSheet.create({
    container: {
        flex: 1,
        width: '100%',
        height: '100%',
        backgroundColor: '#fff',
    },

    header: {
        width: '100%',
        padding: 12,
        marginTop: 18,
        flexDirection: 'row',
        alignItems: 'center',
    },

    'header-container': {
        width:"72%",
        alignItems: 'center',
    },

    'header-title': {
        fontSize: 24,
        fontWeight: 'bold',
        color: '#555',
    },
    scrollView: {
        flexGrow: 1,
    },
    noFeed: {
        width: '100%',
        height: '100%',
        backgroundColor: '#fff',
    },
});

export default Posts;