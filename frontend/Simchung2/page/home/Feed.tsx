import React, { useState } from 'react';
import {
    Button,
    Dimensions,
    FlatList,
    Image,
    ListRenderItem,
    StyleSheet,
    Text,
    TouchableOpacity,
    View,
} from 'react-native';
import Icon from 'react-native-vector-icons/MaterialIcons';
import LonIcons from 'react-native-vector-icons/Ionicons';
import { Comments, FeedItemResponse, Images } from '../../interface/feed/Feed';
import { formatComma } from '../../util/common/Common';
import profile from '../myPage/Profile';

export interface FeedItem {
    userName: string,
    feedContent: string,
    userImgUrl: string,
    imgUrl: string,
    favoriteCnt: string,
    commentCnt: string,
}

interface FeedProps {
    feed: FeedItemResponse;
    onPress: (comments: Comments[]) => void;
    onLike: (id: number) => void;
}

const Feed = ({ feed, onPress, onLike }: FeedProps) => {
    const userImgSrc = { uri: `${feed.otherUserInfoResponse.profileUrl}` };
    const { width } = Dimensions.get('window');

    const renderItems: ListRenderItem<Images> = ({ item }) => (
        <View style={[styles['image-wrapper'], {width: width}]} accessibilityLabel={`${item.imageText}`}>
            <Image style={styles.image} source={{ uri: item.imageUrl }} resizeMode="cover" />
        </View>
    );

    const formatFavorite = ():string => {
        const icon = feed.posts.isLiked ? 'favorite' : 'favorite-outline'
        return icon
    }

    return (
        <View style={[styles.container, { marginTop: 12, marginBottom: 12 }]} accessible={false}>
            <View style={styles.header} accessible={false}>
                <Image
                    style={styles['profile-image']}
                    source={userImgSrc}
                    resizeMode="cover" accessible={false}/>
                <View style={styles['user-container']} accessible={false}>
                    <Text style={styles.userName} accessibilityLabel={`${feed.otherUserInfoResponse.nickname}님 입니다.`}>{feed.otherUserInfoResponse.nickname}</Text>
                    <Text style={styles.comment} numberOfLines={1} ellipsizeMode="tail" accessibilityLabel={`${feed.posts.content}`}>{feed.posts.content}</Text>
                </View>
                <TouchableOpacity style={styles.iconButton} >
                    <Icon name="more-horiz" size={24} style={styles.icon} />
                </TouchableOpacity>
            </View>
            <FlatList
                data={feed.posts.images.flatMap((images) => images)}
                renderItem={renderItems}
                keyExtractor={(item, index) => index.toString()}
                horizontal={true}
                snapToAlignment={"start"}
                snapToInterval={width}
                decelerationRate={"fast"}
                pagingEnabled
            />
            <View accessible={false} style={styles.favorite}>
                <TouchableOpacity accessibilityLabel='댓글 창 입니다 확인 하시려면 두번 탭 해주세요.' style={styles['comment-container']} onPress={() => onPress && onPress(feed.comments)}>
                    <LonIcons name="chatbubble-ellipses" size={24} style={styles.icon} />
                    <Text style={styles['comment-count']}>{formatComma(feed.posts.commentCount)}</Text>
                </TouchableOpacity>
                <TouchableOpacity accessibilityLabel={`좋아요 버튼 입니다. 좋아요를 ${feed.posts.isLiked ? '취소' : ''}하고 싶으면 두번 탭 해주세요.`} style={styles['favorite-container']} onPress={()=>onLike && onLike(feed.posts.postId)}>
                    <Icon name={formatFavorite()} size={24} style={styles.icon} />
                    <Text style={styles['comment-count']}>{formatComma(feed.posts.likeCount)}</Text>
                </TouchableOpacity>
            </View>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        width: '100%',
    },

    header: {
        paddingHorizontal: 12,
        flexDirection: 'row',
        alignItems: 'center',
    },

    'profile-image': {
        width: 36,
        height: 36,
        borderRadius: 18,
        backgroundColor: '#fff',
    },

    'image-wrapper': {
        height: '100%', // 이미지의 너비를 100%로 설정
        aspectRatio: 1, // 이미지의 비율을 1:1로 설정
        marginTop: 8,
    },

    image: {
        width: '100%', // 너비를 100%로 설정
        height: '100%', // 높이를 부모의 100%로 설정하여 비율 유지
        borderRadius: 0,
    },

    favorite: {
        flexDirection: 'row',
        alignItems: 'flex-start',
        marginTop: 8,
        paddingHorizontal: 12,
    },

    'favorite-container': {
        flexDirection: 'row',
        alignItems: 'flex-start',
        marginLeft: 24,
    },

    'comment-container': {
        flexDirection: 'row',
        alignItems: 'flex-start',
    },

    'comment-count': {
        marginLeft: 8,
    },

    'user-container': {
        width: '70%',
        flexDirection: 'column',
        alignItems: 'flex-start',
        justifyContent: 'flex-start',
    },

    userName: {
        color: '#555555',
        fontSize: 12,
        fontWeight: 'bold',
        marginLeft: 12,
    },

    comment: {
        color: '#000',
        fontSize: 12,
        fontWeight: 'medium',
        marginLeft: 12,
    },
    iconButton: {
        marginLeft: 'auto', // 버튼을 오른쪽 끝으로 이동
    },
    icon: {
        color: '#000', // 아이콘 색상
    },
});

export default Feed;