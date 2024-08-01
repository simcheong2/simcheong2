import React from "react";
import { StyleSheet, Text, View } from 'react-native';
import CameraTest from '../components/image/CameraTest';
import VoiceTest from '../components/voice/VoiceTest';
import { Camera } from 'expo-camera/legacy';
import UploadNavigation from '../navigation/photo/UploadNavigation';

const PhotoScreen = () => {
    return(
        <View style={styles.container} accessibilityLabel='게시글 작성 화면 입니다. 최대 사진 3개까지 촬영이 가능합니다. 좋은 추억을 남겨 공유 해봅시다.'>
            <UploadNavigation/>
        </View>
    )
}

const styles = StyleSheet.create({
    container:{
        flex:1,
        width:"100%",
        height:"100%",
        backgroundColor:"#fff"
    }
})

export default PhotoScreen