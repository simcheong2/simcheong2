import { StyleSheet, Image } from 'react-native';
import React from 'react';

interface ImageViewProps{
    selectedImage: string,
}

export default function ImageViewer({ selectedImage }:ImageViewProps) {
    const imageSource = selectedImage ? { uri: selectedImage } : require('../../assets/images/logo.png');

    return(
        <Image source={imageSource} style={styles.image} />
    )
}

const styles = StyleSheet.create({
    image: {
        width: 320,
        height: 440,
        borderRadius: 18,
    },
});