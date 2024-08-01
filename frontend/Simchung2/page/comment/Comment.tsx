import React from "react";
import { Text, View, StyleSheet } from "react-native";
import { Comments } from '../../interface/feed/Feed';

interface CommentProps {
    comment: Comments;
}

const Comment = ({ comment }: CommentProps) => {
    return (
        <View accessible={false} style={styles.commentContainer}>
            <View accessible={false} style={styles.header}>
                <Text accessible accessibilityLabel={`${comment.nickname}님 입니다.`} style={styles.username}>{comment.nickname}</Text>

            </View>
            <Text accessible accessibilityLabel={comment.comment} style={styles.commentText}>{comment.comment}</Text>
        </View>
    )
}

const styles = StyleSheet.create({
    commentContainer: {
        backgroundColor: 'rgba(0, 0, 0, 0.05)', // 배경을 약간 투명하게 설정
        padding: 10,
        marginVertical: 5,
        borderRadius: 8,
        borderColor: '#ddd',
        borderWidth: 1,
        marginLeft:10,
        marginRight:10,
    },
    header: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        marginBottom: 5,
    },
    username: {
        fontWeight: 'bold',
        color: '#333',
    },

    commentText: {
        color: '#333',
    }
})

export default Comment;
