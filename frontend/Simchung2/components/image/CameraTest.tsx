import React, { useRef, useState } from 'react';
import { useCameraPermissions } from 'expo-camera';
import { Button, Image, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { Camera, CameraType } from 'expo-camera/legacy';
import axios from 'axios';
import { Blob } from 'buffer'

const CameraTest = () => {
    const accessToken = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0OWlkIiwiZXhwIjoxNzI0OTAyNjE0LCJraW5kIjoiYWNjZXNzVG9rZW4ifQ.Zv7FqfwkQy4_FJ_OmQXKiy-ZPKVF7PBSSfeLafcd1KHvjOd-Vg-9gYBns7jicyzBopkyixy83NzfIBTG0Kp8Mw';
    const [facing, setFacing] = useState<CameraType>(CameraType.back);
    const [permission, requestPermission] = useCameraPermissions();
    const [photo, setPhoto] = useState<string | null>(null);
    const cameraRef = useRef<Camera | null>(null);

    if (!permission) {
        // Camera permissions are still loading.
        return <View />;
    }

    if (!permission.granted) {
        // Camera permissions are not granted yet.
        return (
            <View style={styles.container}>
                <Text style={styles.message}>We need your permission to show the camera</Text>
                <Button onPress={requestPermission} title="grant permission" />
            </View>
        );
    }

    const toggleCameraFacing = () => {
        setFacing(current => (current === CameraType.back ? CameraType.front : CameraType.back));
    };

    const takePicture = async () => {
        if (cameraRef.current) {
            const photo = await cameraRef.current.takePictureAsync();
            setPhoto(photo.uri);
            const fileName = photo.uri.split('/').pop();
            const fileType = fileName?.split('.').pop();
            const mimeType = `image/${fileType}`;
            uploadPhoto(photo.uri, mimeType, fileName!!);
        }
    };

    const uploadPhoto = async (uri: string, type: string, name: string) => {
        const formData = new FormData()
        formData.append('images',{
            uri,
            name,
            type
        });

        // JSON 요청 본문을 문자열로 변환합니다.
        const requestPayload = JSON.stringify({
            content: "여기 놀러왔어요~"
        });
        const blob = new Blob([requestPayload], { type: "application/json" });

        // JSON 요청 본문을 FormData에 추가합니다.
        formData.append('request', blob);

        try {
            await axios.post('http://www.my-first-develop-library.shop:8080//posts', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                    "Authorization": `Bearer ${accessToken}`,
                }
            }).then((response)=>{
                console.log(response.data);
            }).catch((error)=>{
                console.error(error);
            });
        } catch (error) {
            console.error(error);
        }
    };

    return (
        <View style={styles.container}>
            <Camera style={styles.camera} type={facing} ref={cameraRef}>
                <View style={styles.buttonContainer}>
                    <TouchableOpacity style={styles.button} onPress={toggleCameraFacing}>
                        <Text style={styles.text}>Flip Camera</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.button} onPress={takePicture}>
                        <Text style={styles.text}>Take Picture</Text>
                    </TouchableOpacity>
                </View>
            </Camera>
            {photo && (
                <View style={styles.previewContainer}>
                    <Text style={styles.previewText}>Photo Preview:</Text>
                    <Image source={{ uri: photo }} style={styles.previewImage} />
                </View>
            )}
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
    },
    message: {
        textAlign: 'center',
        paddingBottom: 10,
    },
    camera: {
        flex: 1,
    },
    buttonContainer: {
        flex: 1,
        flexDirection: 'row',
        backgroundColor: 'transparent',
        margin: 64,
    },
    button: {
        flex: 1,
        alignSelf: 'flex-end',
        alignItems: 'center',
    },
    text: {
        fontSize: 24,
        fontWeight: 'bold',
        color: 'white',
    },
    previewContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    previewText: {
        fontSize: 18,
        marginBottom: 10,
    },
    previewImage: {
        width: 300,
        height: 400,
    },
});

export default CameraTest;