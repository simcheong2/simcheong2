import React, { useEffect, useState } from 'react';
import { Text, View, StyleSheet, SafeAreaView, ScrollView } from 'react-native';
import { useRecoilState } from 'recoil';
import modalAtom from '../../recoil/modalAtom';
import { Comments, FeedItemResponse } from '../../interface/feed/Feed';
import { EmptyFeeds, userFeeds } from '../../util/test/userFeed/UserFeed';
import commentsAtom from '../../recoil/atom/commentsAtom';
import Feed from './Feed';


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
    const [modal, setModal] = useRecoilState<number>(modalAtom)

    const [feeds, setFeeds] = useState<FeedItemResponse[]>(userFeeds);

    const [comments, setComments] = useRecoilState<Comments[]>(commentsAtom);

    const handleComment = (data: Comments[]) => {
        setModal(3);
        setComments(data);
    }

    return(
        (<SafeAreaView style={styles.container}>
            <ScrollView style={styles.scrollView}>
                {feeds.map((feed, index)=>(
                    <Feed key={index} feed={feed} onPress={handleComment}/>
                ))}
            </ScrollView>
        </SafeAreaView>)
    )
}

export default Recommend