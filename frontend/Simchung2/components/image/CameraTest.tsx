import React, { useRef, useState } from 'react';
import { useCameraPermissions } from 'expo-camera';
import { Alert, Button, Image, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { Camera, CameraType } from 'expo-camera/legacy';
import axios from 'axios';
import * as ImageManipulator from 'expo-image-manipulator';
import { useRecoilValue } from 'recoil';
import accessTokenAtom from '../../recoil/atom/accessTokenAtom';
import Icon from 'react-native-vector-icons/MaterialIcons';
import IconCommunity from 'react-native-vector-icons/MaterialCommunityIcons';
import { ImageResize } from '../../util/common/Common';
import Loading from '../../page/loading/Loading';
import { useNavigation } from '@react-navigation/native';
import { ScreenNavigationProp } from '../../types/navigationTypes';

const CameraTest = () => {
    const [facing, setFacing] = useState<CameraType>(CameraType.back);
    const [permission, requestPermission] = useCameraPermissions();
    const [photo, setPhoto] = useState<string[] | null>(null);
    const cameraRef = useRef<Camera | null>(null);
    const accessToken = useRecoilValue(accessTokenAtom);
    const [loading, setLoading] = useState<boolean>(false);
    const navigation = useNavigation<ScreenNavigationProp>()

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
        const newWidth = width * 0.3;
        const newHeight = height * 0.3;

        // Manipulate the image (resize it)
        const manipulatedImage = await ImageManipulator.manipulateAsync(
            photos.uri,
            [{ resize: { width: newWidth, height: newHeight } }],
            { compress: 1, format: ImageManipulator.SaveFormat.JPEG, base64: true },
        );

        if (photo == null) {
            setPhoto((prev) => {
                if (prev === null) {
                    return [manipulatedImage.uri];
                }
                return [...prev, manipulatedImage.uri];
            });
        } else if (photo.length < 3) {
            setPhoto((prev) => {
                if (prev === null) {
                    return [manipulatedImage.uri];
                }
                return [...prev, manipulatedImage.uri];
            });
        }

        console.log(manipulatedImage.uri);

        const fileName = manipulatedImage.uri.split('/').pop();
        const fileType = fileName?.split('.').pop();
        const mimeType = `image/${fileType}`;
        // await uploadPhoto(manipulatedImage.uri, manipulatedImage.base64!!, mimeType, fileName!!);
    };

    const uploadPhoto = async () => {
        setLoading(true);
        const formData = new FormData();

        if (photo != null) {
            photo.forEach((picture) => {
                const fileName = picture.split('/').pop();
                formData.append('images', {
                    uri: picture,
                    name: fileName,
                    type: 'image/jpeg',
                });
            });
        }
        else{
            Alert.alert('업로드 할 이미지가 없습니다.');
            setLoading(false);
            return;
        }

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
            setLoading(false);
            navigation.navigate('Home');
        } catch (error) {
            console.error('Error', error);
            setLoading(false);
        }
        setLoading(false);
    };

    return (loading ? <Loading /> :
            <View style={styles.container}>
                <Camera style={styles.camera} type={facing} ref={cameraRef}>
                    <View style={styles.buttonContainer}>
                        <TouchableOpacity style={[styles.button]} onPress={toggleCameraFacing}>
                            <Icon style={styles.text} name="screen-rotation" size={48} />
                        </TouchableOpacity>
                        <TouchableOpacity style={[styles.button]} onPress={takePicture}>
                            <Icon style={styles.text} name="camera-alt" size={48} />
                        </TouchableOpacity>
                        <TouchableOpacity style={[styles.button]} onPress={uploadPhoto}>
                            <IconCommunity style={styles.text} name="file-upload-outline" size={48} />
                        </TouchableOpacity>
                    </View>
                </Camera>
                {photo && (
                    <View style={styles.previewContainer}>
                        <Text style={styles.previewText}>사진 미리보기</Text>
                        {photo.map((picture, index) => (
                            <View style={styles.previewImageContainer} key={index}>
                                <Image style={styles.previewImage} source={{ uri: picture }} />
                            </View>
                        ))}
                    </View>)
                }
            </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
    },
    message: {
        textAlign: 'center',
        paddingBottom: 10,
    },
    camera: {
        flex: 1,
        position: 'absolute',
        top: 0,
        left: 0,
        width: '100%',
        height: '100%',
    },
    buttonContainer: {
        flex: 1,
        flexDirection: 'row',
        backgroundColor: 'transparent',
        marginBottom: 64,
    },
    button: {
        flex: 1,
        justifyContent: 'flex-end',
        alignItems: 'center',
    },
    text: {
        fontWeight: 'bold',
        color: 'white',
    },
    previewContainer: {
        flex: 1,
        flexDirection: 'column',
        alignItems: 'flex-end',
        marginRight: 12,
        marginTop: 120,
    },
    previewText: {
        fontSize: 18,
        marginBottom: 10,
    },
    previewImageContainer: {
        alignItems: 'center',
        marginBottom: 20,
    },
    previewImage: {
        width: 100,
        height: 100,
    },
});

export default CameraTest;