import React from "react";
import { Text, View } from "react-native";
import { Comments } from '../../interface/feed/Feed';

interface CommentProps{
    comment: Comments;
}

const Comment = ({comment}: CommentProps) => {
    return(
        <View>
            <Text>{comment.comment}</Text>
        </View>
    )
}

export default Comment