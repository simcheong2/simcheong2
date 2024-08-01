import React, { useEffect, useState } from 'react';
import { Text, View, StyleSheet, SafeAreaView, ScrollView, AccessibilityInfo } from 'react-native';
import { useRecoilState } from 'recoil';
import modalAtom from '../../recoil/modalAtom';
import { Comments, FeedItemResponse } from '../../interface/feed/Feed';
import { EmptyFeeds, userFeeds } from '../../util/test/userFeed/UserFeed';
import commentsAtom from '../../recoil/atom/commentsAtom';
import Feed from './Feed';
import axios from 'axios';
import accessTokenAtom from '../../recoil/atom/accessTokenAtom';
import { getStorage } from '../../util/common/Storage.ts';
import Loading from '../loading/Loading';
import PostAtom from '../../recoil/atom/postAtom';


const styles = StyleSheet.create({
    container:{
        flex: 1,
        width: "100%",
        height: "100%",
        backgroundColor: "#fff",
    },
    scrollView: {
        flexGrow: 1,
        marginBottom: 80,
    },
    noFeed:{
        width: "100%",
        height: "100%",
        backgroundColor: "#fff",
    }
})

const Recommend = () => {
    const [loading, setLoading] = useState<boolean>(false);
    const [modal, setModal] = useRecoilState<number>(modalAtom);
    const [feeds, setFeeds] = useState<FeedItemResponse[]>();
    const [postID, setPostID] = useRecoilState<number>(PostAtom);
    const [accessToken, setAccessToken] = useRecoilState(accessTokenAtom);

    useEffect(() => {
        getStorage('accessToken').then((v) => {
            setAccessToken(v);
        });
    }, []);

    const handleComment = (data: number) => {
        setModal(3);
        setPostID(data);
    };

    useEffect(() => {
        setLoading(true);
        axios.get('http://www.my-first-develop-library.shop:8080/posts/random', {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            },
        }).then((response) => {
            if (response.status === 200) {
                setFeeds(response.data);
            } else {
                console.log(response.data);
                setFeeds(EmptyFeeds);
            }
            setLoading(false);
        }).catch((error) => {
            console.log(error);
            setFeeds(EmptyFeeds);
            setLoading(false);
        });
        return () => {
            setFeeds(EmptyFeeds);
            setLoading(false);
        };
    }, [accessToken]);

    const onLikeHandler = (postId: number) => {
        const PostID = {id: postId}
        axios.post(`http://www.my-first-develop-library.shop:8080/posts/like`, PostID,{
            headers: {
                Authorization: `Bearer ${accessToken}`,
            },
        }).then((response) => {
            setFeeds((prevFeeds) => {
                return prevFeeds?.map(feed => {
                    if (feed.posts.postId === postId) {
                        const commentLike = feed.posts.isLiked ? '좋아요 취소' : '좋아요';
                        AccessibilityInfo.announceForAccessibility(commentLike);
                        const num = feed.posts.isLiked ? -1 : +1;
                        return {
                            ...feed,
                            posts: {
                                ...feed.posts,
                                likeCount: feed.posts.likeCount+num,  // 또는 다른 로직으로 업데이트
                                isLiked: !feed.posts.isLiked,
                            }
                        };
                    }
                    return feed;
                });
            });
            console.log(response.data);
        }).catch((error)=>{
            console.log(error.data);
        })
    }

    return (
        loading ? <Loading /> :
            <>
                {feeds ?
                    (<SafeAreaView style={styles.container}>
                        <ScrollView style={styles.scrollView}>
                            {feeds.map((feed, index) => (
                                <Feed key={index} feed={feed} onPress={handleComment} onLike={onLikeHandler}/>
                            ))}
                        </ScrollView>
                    </SafeAreaView>) :
                    (
                        <View style={styles.noFeed}>
                            <Text>친구 없으면</Text>
                        </View>
                    )
                }
            </>
    );
}

export default Recommend