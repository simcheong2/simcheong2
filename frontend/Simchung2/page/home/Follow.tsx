import React, { useState } from "react";
import { SafeAreaView, ScrollView, StyleSheet, Text, View, ViewBase } from "react-native";
import Feed, { FeedItem } from "./Feed";

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
    const formatComma = (num: number) => {
        if (num >= 100000000) {
            return (num / 100000000).toFixed(1) + '억';
        } else if (num >= 10000) {
            return (num / 10000).toFixed(1) + '만';
        } else {
            return num.toLocaleString('ko-KR');
        }
    }

    const [feeds, setFeeds] = useState<FeedItem[]>([
        {
            userName: "Lionell_Messi",
            feedContent: "이 이미지에는 축구 유니폼을 입은 사람이 트로피를 들고 환호하는 장면이 담겨 있습니다.",
            userImgUrl: "https://reactjs.org/logo-og.png",
            imgUrl: "https://i.namu.wiki/i/FylQ2OG1ppJWqGXu1AalEXK1HEDwql9pDvlK5EB4BZh-B2qJNpRfpCYtb_r6MgFtiXKPx5l5fawyO17tt4UMDta7k9zuy_GasunqDG58HglNcHlmIaEWjV_rf6tQvf3RWd0xGtsQ6htHaL5KR7NfPA.webp",
            favoriteCnt: formatComma(27000),
            commentCnt: formatComma(27000),
        },
        {
            userName: "Cristiano_Ronaldo",
            feedContent: "SIUUUUUUUUUUUUUUUUUUUUUUUU",
            userImgUrl: "https://reactjs.org/logo-og.png",
            imgUrl: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR4qqA-r4IaPjnDGL1Of1_mVHE6_ft-OXVebQ&s",
            favoriteCnt: formatComma(22000),
            commentCnt: formatComma(20000),
        },
        {
            userName: "katarinabluu",
            feedContent: "카리나 vs 이재용",
            userImgUrl: "https://reactjs.org/logo-og.png",
            imgUrl: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTtRCGjNQ-eOBVSrPManpSmj8FJIv3f_pNq6w&s",
            favoriteCnt: formatComma(222200000),
            commentCnt: formatComma(3333333),
        }
    ]);

    return(
        <>
        {feeds ? 
            (<SafeAreaView style={styles.container}>
                <ScrollView style={styles.scrollView}>
                    {feeds.map((feed, index)=>(
                        <Feed key={index} feed={feed}/>
                    ))}
                </ScrollView>
            </SafeAreaView>) :
            (
                <View style={styles.noFeed}>
                    <Text>친구 없으면 꺼져라</Text>
                </View>
            )
        }
        </>
    )
}

export default Follow