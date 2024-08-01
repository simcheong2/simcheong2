import React, { useEffect, useState } from 'react';
import { Text, View, StyleSheet, SafeAreaView, ScrollView } from 'react-native';
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
    const [comments, setComments] = useRecoilState<Comments[]>(commentsAtom);
    const [accessToken, setAccessToken] = useRecoilState(accessTokenAtom);

    useEffect(() => {
        getStorage('accessToken').then((v) => {
            setAccessToken(v);
        });
    }, []);

    const handleComment = (data: Comments[]) => {
        setModal(3);
        setComments(data);
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