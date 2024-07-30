import React, { useRef, useState } from 'react';
import { useCameraPermissions } from 'expo-camera';
import { Button, Image, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { Camera, CameraType } from 'expo-camera/legacy';
import axios from 'axios';
import * as ImageManipulator from 'expo-image-manipulator';

const CameraTest = () => {
    const accessToken = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0OWlkIiwiZXhwIjoxNzI0OTEwNDU2LCJraW5kIjoiYWNjZXNzVG9rZW4ifQ.DvKqqgTuvudlzJVQJYz8zhURG3amw8yVcb-MGzdfMD95__GMZbw4frBgWbRfOnzFfQECf4aNPmKHX3n8GTLS4g';
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
        // cameraRef가 없으면 해당 함수가 실행되지 않게 가드
        if (!cameraRef.current) return;

        const photos = await cameraRef.current?.takePictureAsync();

        const imageInfo = await ImageManipulator.manipulateAsync(photos.uri);
        const { width, height } = imageInfo;

        // Calculate new dimensions
        const newWidth = width * 0.2;
        const newHeight = height * 0.2;

        // Manipulate the image (resize it)
        const manipulatedImage = await ImageManipulator.manipulateAsync(
            photos.uri,
            [{ resize: { width: newWidth, height: newHeight } }],
            { compress: 1, format: ImageManipulator.SaveFormat.JPEG, base64: true },
        );

        setPhoto(manipulatedImage.uri);

        console.log(manipulatedImage.uri);

        const fileName = manipulatedImage.uri.split('/').pop();
        const fileType = fileName?.split('.').pop();
        const mimeType = `image/${fileType}`;
        await uploadPhoto(manipulatedImage.uri, manipulatedImage.base64!!, mimeType, fileName!!);
    };

    const uploadPhoto = async (uri: string, base64Data: string, mimeType: string, fileName: string) => {
        const formData = new FormData();

        // Append image file
        formData.append('images', {
            uri,
            name: fileName,
            type: 'image/jpeg',
        });

        // Append request JSON object as a Blob
        const content = { 'string': JSON.stringify({ content: 'Sample' }), type: 'application/json' };
        formData.append('request', content);

        try {
            const response = await axios.post(
                'http://www.my-first-develop-library.shop:8080/posts',
                formData,
                {
                    headers: {
                        Authorization: `Bearer ${accessToken}`,
                        'Content-Type': 'multipart/form-data',
                    },
                },
            );
            console.log('Success', response.data);
        } catch (error) {
            console.error('Error', error);
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