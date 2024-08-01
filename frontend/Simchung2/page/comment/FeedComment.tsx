import React, {useEffect, useState} from "react";
import {
  SafeAreaView,
  ScrollView,
  StyleSheet,
  Text,
  TextInput,
  View,
  KeyboardAvoidingView,
  TouchableWithoutFeedback,
  Keyboard,
  TouchableOpacity,
} from "react-native";
import { useRecoilValue } from "recoil";
import Icon from "react-native-vector-icons/MaterialIcons";
import { Audio } from 'expo-av';
import axios from 'axios';
import accessTokenAtom from "../../recoil/atom/accessTokenAtom";
import Comment from './Comment';
import Loading from "../loading/Loading";
import postAtom from "../../recoil/atom/postAtom";

const FeedComment = () => {
  const postId = useRecoilValue(postAtom);
  const [mic, setMic] = useState(false);
  const [recording, setRecording] = useState<Audio.Recording | undefined>(undefined);
  const [text, setText] = useState<string>('');
  const [permissionResponse, requestPermission] = Audio.usePermissions();
  const accessToken = useRecoilValue(accessTokenAtom);
  const [loading, setLoading] = useState<boolean>(false);
  const [comments, setComments] = useState<any[]>([]); // State to store comments

  const formCommentData={
    postId: postId,
    content: '',
  };

  const baseURL = 'http://www.my-first-develop-library.shop:8080';

  useEffect(() => {
    handleGetPostData();
  }, []);

  const handleGetPostData = () => {
    axios
        .get(`${baseURL}/posts/main`,{
          headers: {
            Authorization: `Bearer ${accessToken}`,
          }
        })
        .then((response) => {
          if (response.status == 200) {
              console.log("성공!");
              console.log(postId);

              const postData = response.data;

              const post = postData.find((post:any)=>post.posts.postId===postId)
              if (post) {
                setComments(post.comments);
              }
          }
        })
        .catch((error) => {
          console.error('Error fetching post ID', error);
        });
  };
  const handleAddComment = () =>{
      axios
          .post(`${baseURL}/comments`, formCommentData,{headers: {
              Authorization: `Bearer ${accessToken}`,
              "Content-Type": 'application/json',
            }})
          .then((response)=>{
              if(response.status===200){
                  console.log("댓글달기 성공!");
                  handleGetPostData();
              }
          })
          .catch((error)=>{
            console.error(error);
          })
          .finally(()=>{
            console.log(formCommentData);
          })
  }
  const onTextInputFocus = () => {
    setMic(true);
  }

  const onTextChange = (e: string) => {
    setText(e);
  }

  async function startRecording() {
    try {
      if (permissionResponse?.status !== 'granted') {
        console.log('Requesting permission..');
        await requestPermission();
      }
      await Audio.setAudioModeAsync({
        allowsRecordingIOS: true,
        playsInSilentModeIOS: true,
      });

      console.log('Starting recording..');
      const { recording } = await Audio.Recording.createAsync(Audio.RecordingOptionsPresets.HIGH_QUALITY);
      setRecording(recording);
      console.log('Recording started');
    } catch (err) {
      console.error('Failed to start recording', err);
    }
  }

  async function stopRecording() {
    console.log('Stopping recording..');
    setRecording(undefined);
    await recording?.stopAndUnloadAsync();
    await Audio.setAudioModeAsync({
      allowsRecordingIOS: false,
    });
    const uri = recording?.getURI();
    if (uri) {
      upload(uri);
    }
    console.log('Recording stopped and stored at', uri);
  }

  const upload = (uri: string) => {
    const formData = new FormData();
    const fileName = uri.split('/').pop();

    formData.append('audio', {
      uri: uri,
      name: fileName,
      type: 'audio/mp3',
    });

    setLoading(true);
    axios.post(`${baseURL}/users/audio`, formData, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        'Content-Type': 'multipart/form-data',
      },
    }).then((response) => {
      console.log('Success', response.data);
      formCommentData.content = response.data
      // setFormCommentData((prevData) => ({
      //   ...prevData,
      //   content: response.data.toString(),
      // }));
      setText(response.data);
      handleAddComment();
      setText("");
    }).catch((error) => {
      console.log('Fail', error);
    }).finally(() => {
      setLoading(false);
    });
  };

  return (
      <KeyboardAvoidingView
          style={styles.container}
          behavior="padding"
          keyboardVerticalOffset={80}
      >
        <TouchableWithoutFeedback onPress={Keyboard.dismiss}>
          <View style={styles.inner}>
            <View style={styles["title-container"]}>
              <Text>댓글</Text>
            </View>
            <SafeAreaView style={styles["comments-container"]}>
              <ScrollView style={styles["comments-scroll"]}>
                {comments.length > 0 ? (
                    comments.map((comment, index) => (
                        <Comment key={index} comment={comment} />
                    ))
                ) : (
                    <Text style={styles.noCommentsText}>아직 댓글이 없습니다.</Text>
                )}
              </ScrollView>
            </SafeAreaView>
            <View style={styles["comment-register"]}>
              <TextInput
                  style={styles.comment}
                  placeholder="댓글"
                  onFocus={onTextInputFocus}
                  onChangeText={onTextChange}
                  value={text}
              />
            </View>
            {mic && (
                <View style={styles["mic-wrapper"]}>
                  <TouchableOpacity
                      style={styles["mic-container"]}
                      onPress={recording ? stopRecording : startRecording}
                  >
                    <Icon name={"mic"} size={48} color={recording ? "#ff6347" : "#ffffff"} />
                  </TouchableOpacity>
                  {recording && <Text style={styles.recordingText}>Recording...</Text>}
                </View>
            )}
            {loading && <Loading />}
          </View>
        </TouchableWithoutFeedback>
      </KeyboardAvoidingView>
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
    backgroundColor: "#FFFFFF"
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
    padding: 6,
    borderRadius: 8,
  },
  "mic-wrapper": {
    alignItems: 'center',
    marginTop: 20,
  },
  "mic-container": {
    width: 80,
    height: 80,
    borderRadius: 40,
    backgroundColor: "#ff6347",
    justifyContent: "center",
    alignItems: "center",
    elevation: 2,
  },
  recordingText: {
    marginTop: 10,
    color: "#ff6347",
    fontWeight: "bold",
  },
  noCommentsText: {
    textAlign: "center",
    color: "#fff",
    marginTop: 20,
  },
});

export default FeedComment;
