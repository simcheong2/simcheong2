import React, { useState } from "react";
import { SafeAreaView, ScrollView, StyleSheet, Text, View, ViewBase } from "react-native";
import Feed from "./Feed";
import { useRecoilState } from "recoil";
import modalAtom from '../../recoil/modalAtom';
import { Comments, FeedItemResponse } from '../../interface/feed/Feed';
import { userFeeds } from '../../util/test/userFeed/UserFeed';
import commentsAtom from '../../recoil/atom/commentsAtom';

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
const Follow = () => {
    const [modal, setModal] = useRecoilState<number>(modalAtom)

    const [feeds, setFeeds] = useState<FeedItemResponse[]>(userFeeds);

    const [comments, setComments] = useRecoilState<Comments[]>(commentsAtom);

    const handleComment = (data: Comments[]) => {
        setModal(3);
        console.log(modal);
        setComments(data);
    }

    return(
        <>
        {feeds ? 
            (<SafeAreaView style={styles.container}>
                <ScrollView style={styles.scrollView}>
                    {feeds.map((feed, index)=>(
                        <Feed key={index} feed={feed} onPress={handleComment}/>
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
    )
}

export default Follow