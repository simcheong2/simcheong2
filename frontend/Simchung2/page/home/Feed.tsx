import React from "react";
import { Button, Image, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import Icon from "react-native-vector-icons/MaterialIcons";
import LonIcons from "react-native-vector-icons/Ionicons";

export interface FeedItem{
    userName: string,
    feedContent: string,
    userImgUrl: string,
    imgUrl: string,
    favoriteCnt: string,
    commentCnt: string, 
}

interface FeedProps{
    feed: FeedItem;
}

const Feed = ({feed}: FeedProps) => {
    const userImgSrc = {uri: `${feed.userImgUrl}`}
    const imgSrc = {uri: `${feed.imgUrl}`}


    return(
        <View style={[styles.container, {marginTop: 12, marginBottom: 12}]}>
            <View style={styles.header}>
                <Image 
                    style={styles["profile-image"]}
                    source={userImgSrc}
                    resizeMode="cover"/>
                <View style={styles["user-container"]}>
                    <Text style={styles.userName}>{feed.userName}</Text>
                    <Text style={styles.comment} numberOfLines={1} ellipsizeMode="tail">{feed.feedContent}</Text>
                </View>
                <TouchableOpacity style={styles.iconButton}>
                    <Icon name="more-horiz" size={24} style={styles.icon} /> 
                </TouchableOpacity>
            </View>
            <View style={styles["image-wrapper"]} accessibilityLabel="김동연은 신이고 개새끼다">
                <Image style={styles.image} source={imgSrc} resizeMode="cover"/>
            </View>
            <View style={styles.favorite}>
                <TouchableOpacity style={styles["comment-container"]}>
                    <LonIcons name="chatbubble-ellipses" size={24} style={styles.icon}/>
                    <Text style={styles["comment-count"]}>{feed.commentCnt}</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles["favorite-container"]}>
                    <Icon name="favorite-outline" size={24} style={styles.icon}/>
                    <Text style={styles["comment-count"]}>{feed.favoriteCnt}</Text>
                </TouchableOpacity>
            </View>
        </View>
    )
}

const styles = StyleSheet.create({
    container:{
        width:"100%",
    },

    header:{
        paddingHorizontal: 12,
        flexDirection: "row",
        alignItems: "center",
    },

    "profile-image":{
        width: 36,
        height: 36,
        borderRadius: 18,
        backgroundColor: "#fff",
    },

    "image-wrapper":{
        width: "100%", // 이미지의 너비를 100%로 설정
        aspectRatio: 1, // 이미지의 비율을 1:1로 설정
        marginTop: 8,
    },
    
    image:{
        width: "100%", // 너비를 100%로 설정
        height: "100%", // 높이를 부모의 100%로 설정하여 비율 유지
        borderRadius: 0,
    },

    favorite:{
        flexDirection: "row",
        alignItems: "flex-start",
        marginTop: 8,
        paddingHorizontal: 12
    },

    "favorite-container":{
        flexDirection: "row",
        alignItems: "flex-start",
        marginLeft: 24
    },

    "comment-container":{
        flexDirection: "row",
        alignItems: "flex-start",
    },

    "comment-count":{
        marginLeft: 8
    },

    "user-container":{
        width: "70%",
        flexDirection: "column",
        alignItems: "flex-start",
        justifyContent: "flex-start"
    },

    userName:{
        color: "#555555",
        fontSize: 12,
        fontWeight: "bold",
        marginLeft: 12,
    },

    comment:{
        color: "#000",
        fontSize: 12,
        fontWeight: "medium",
        marginLeft: 12,
    },
    iconButton: {
        marginLeft: 'auto', // 버튼을 오른쪽 끝으로 이동
    },
    icon: {
        color: "#000", // 아이콘 색상
    },
})

export default Feed