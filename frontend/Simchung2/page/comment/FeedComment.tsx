import React from "react";
import {
  StyleSheet,
  Text,
  View,
} from "react-native";
import { useRecoilValue } from "recoil";
import commentsAtom from "../../recoil/atom/commentsAtom";
import { Comments } from "../../interface/feed/Feed";

const FeedComment = () => {
  const comments = useRecoilValue<Comments[]>(commentsAtom);

  const handleVoice = () => {
    
  }

  return (
      <View>
        <Text>댓글</Text>
      </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  inner: {
    flex: 1,
    justifyContent: "flex-end",
  },
  "title-container": {
    width: "100%",
    paddingTop: 4,
    paddingBottom: 16,
    alignItems: "center",
    borderBottomColor: "#EFF3F1",
    borderBottomWidth: 1,
  },
  "comments-container": {
    width: "100%",
    height: "84.5%",
    backgroundColor: "#515151",
  },
  "comments-scroll": {
    width: "100%",
  },
  "comment-register": {
    width: "100%",
    height: 65,
    paddingHorizontal: 24,
    flexDirection: "row",
    alignItems: "center",
  },
  comment: {
    width: "100%",
    borderColor: "#555555",
    borderWidth: 1,
    paddingHorizontal: 12,
  },
});

export default FeedComment;