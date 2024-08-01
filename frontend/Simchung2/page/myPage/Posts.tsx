import React, { useState } from 'react';
import { StyleSheet, Text, View, TouchableOpacity, SafeAreaView, ScrollView } from 'react-native';
import { MyProfile } from '../../interface/user/Profile';
import Icon from 'react-native-vector-icons/MaterialIcons';
import { useNavigation } from '@react-navigation/native';
import { FeedNavigationProp } from '../../types/navigationTypes';
import { useRecoilState, useRecoilValue } from 'recoil';
import modalAtom from '../../recoil/modalAtom';
import { Comments } from '../../interface/feed/Feed';
import commentsAtom from '../../recoil/atom/commentsAtom';
import Post from './Post';
import axios from 'axios';
import accessTokenAtom from '../../recoil/atom/accessTokenAtom';
import postAtom from '../../recoil/atom/postAtom';

interface FeedProps {
    profile: MyProfile;
}

const Posts = ({ profile }: FeedProps) => {
    const [modal, setModal] = useRecoilState<number>(modalAtom);

    const [postID, setPostID] = useRecoilState<number>(postAtom);
    const [profileData, setProfileData] = useState<MyProfile>(profile)

    const handleComment = (postID: number) => {
        setModal(3);
        setPostID(postID)
    };
    const accessToken = useRecoilValue(accessTokenAtom)

    const onLikeHandler = (postId: number) => {
        const PostID = {id: postId}
        axios.post(`http://www.my-first-develop-library.shop:8080/posts/like`, PostID,{
            headers: {
                Authorization: `Bearer ${accessToken}`,
            },
        }).then((response) => {
            setProfileData((prevProfileData) => {
                // 좋아요가 업데이트된 상태를 새로 생성
                const updatedPosts = prevProfileData.posts.map((post) => {
                    if (post.postId === postId) {
                        // 좋아요 수와 좋아요 상태 업데이트
                        return {
                            ...post,
                            likeCount: post.isLiked ? post.likeCount - 1 : post.likeCount + 1,
                            isLiked: !post.isLiked,
                        };
                    }
                    return post;
                });

                // 새 상태 반환
                return {
                    ...prevProfileData,
                    posts: updatedPosts,
                };
            });
            console.log(response.data);
        }).catch((error)=>{
            console.log(error.data);
        })
    }

    const navigation = useNavigation<FeedNavigationProp>();
    return (
        <View accessible={false} style={styles.container}>
            <View accessible={false} style={styles.header}>
                <TouchableOpacity accessible importantForAccessibility='yes' accessibilityLabel='뒤로 가고 싶으시면 두번탭하세요.' onPress={() => navigation.goBack()}>
                    <Icon name="chevron-left" size={48} />
                </TouchableOpacity>
                <View accessible importantForAccessibility='yes' accessibilityLabel='게시글' style={styles['header-container']}>
                    <Text style={styles['header-title']}>게시글</Text>
                </View>
            </View>
            {profileData.posts ?
                (<SafeAreaView style={styles.container}>
                    <ScrollView style={styles.scrollView}>
                        {profileData.posts.map((post, index) => (
                            <Post key={index} post={post} onPress={handleComment} profile={profileData.profile} onLike={onLikeHandler}/>
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