import React from "react";
import { StyleSheet, Text, View } from 'react-native';
import CameraTest from '../components/image/CameraTest';

const PhotoScreen = () => {
    return(
        <View style={styles.container}>
            <CameraTest/>
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