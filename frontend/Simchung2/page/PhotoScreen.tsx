import React from "react";
import { StyleSheet, Text, View } from 'react-native';
import CameraTest from '../components/image/CameraTest';
import VoiceTest from '../components/voice/VoiceTest';
import { Camera } from 'expo-camera/legacy';
import UploadNavigation from '../navigation/photo/UploadNavigation';

const PhotoScreen = () => {
    return(
        <View style={styles.container}>
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