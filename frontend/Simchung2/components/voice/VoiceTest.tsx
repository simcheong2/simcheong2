import React, { useEffect, useRef, useState } from 'react';
import {
    View,
    StyleSheet,
    Button,
    Text,
    Alert,
    TouchableOpacity,
    Dimensions,
    ListRenderItem,
    Image, FlatList, Animated,
} from 'react-native';
import { Audio } from 'expo-av';
import { useRecoilValue } from 'recoil';
import accessTokenAtom from '../../recoil/atom/accessTokenAtom';
import axios from 'axios';
import { useNavigation } from '@react-navigation/native';
import { ScreenNavigationProp, UploadNavigationProp } from '../../types/navigationTypes';
import Loading from '../../page/loading/Loading';
import Icon from 'react-native-vector-icons/MaterialIcons';
import Mic from 'react-native-vector-icons/Feather';
import { Images } from '../../interface/feed/Feed';

// @ts-ignore
const VoiceTest = ({ route }) => {
    const { photo } = route.params;

    const [recording, setRecording] = useState<Audio.Recording | undefined>(undefined);
    const [text, setText] = useState<string>('');
    const [permissionResponse, requestPermission] = Audio.usePermissions();
    const accessToken = useRecoilValue(accessTokenAtom);
    const baseURL = 'http://www.my-first-develop-library.shop:8080';
    const navigation = useNavigation<ScreenNavigationProp>();
    const backNavigation = useNavigation<UploadNavigationProp>();
    const [loading, setLoading] = useState<boolean>(false);

    // Animation state
    const waveAnim1 = useRef(new Animated.Value(0)).current;
    const waveAnim2 = useRef(new Animated.Value(0)).current;
    const waveAnim3 = useRef(new Animated.Value(0)).current;

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
            const { recording } = await Audio.Recording.createAsync(Audio.RecordingOptionsPresets.HIGH_QUALITY,
            );
            setRecording(recording);
            console.log('Recording started');
            startWaveAnimation();
        } catch (err) {
            console.error('Failed to start recording', err);
        }
    }

    async function stopRecording() {
        console.log('Stopping recording..');
        setRecording(undefined);
        await recording?.stopAndUnloadAsync();
        await Audio.setAudioModeAsync(
            {
                allowsRecordingIOS: false,
            },
        );
        const uri = recording?.getURI();
        upload(uri!);
        console.log('Recording stopped and stored at', uri);

        stopWaveAnimation();
    }

    const startWaveAnimation = () => {
        // @ts-ignore
        const waveAnimation = (waveAnim) => {
            waveAnim.setValue(0);
            Animated.timing(waveAnim, {
                toValue: 1,
                duration: 1000,
                useNativeDriver: true,
            }).start(() => waveAnimation(waveAnim));
        };

        waveAnimation(waveAnim1);
        setTimeout(() => waveAnimation(waveAnim2), 300);
        setTimeout(() => waveAnimation(waveAnim3), 600);
    };

    const stopWaveAnimation = () => {
        waveAnim1.stopAnimation();
        waveAnim2.stopAnimation();
        waveAnim3.stopAnimation();
    };

    const upload = (uri: string) => {
        const formData = new FormData();

        const fileName = uri.split('/').pop();

        formData.append('audio', {
            uri: uri,
            name: fileName,
            type: 'audio/mp3',
        });

        axios.post(`${baseURL}/users/audio`, formData, {
            headers: {
                Authorization: `Bearer ${accessToken}`,
                'Content-Type': 'multipart/form-data',
            },
        }).then((response) => {
            console.log('Success', response.data);
            setText(response.data);
        }).catch((error) => {
            console.log('Fail', error.data);
        });
    };

    const uploadPhoto = async () => {
        setLoading(true);
        const formData = new FormData();

        if (photo != null) {
            photo.forEach((picture: string) => {
                const fileName = picture.split('/').pop();
                formData.append('images', {
                    uri: picture,
                    name: fileName,
                    type: 'image/jpeg',
                });
            });
        } else {
            Alert.alert('업로드 할 이미지가 없습니다.');
            setLoading(false);
            return;
        }

        // Append request JSON object as a Blob
        const content = { 'string': JSON.stringify({ content: text }), type: 'application/json' };
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
            navigation.navigate('Home', { upload: true });
        } catch (error) {
            console.error('Error', error);
            setLoading(false);
        }
        setLoading(false);
    };

    const { width, height } = Dimensions.get('window');

    const renderItems: ListRenderItem<string> = ({ item }) => (
        <View style={[styles['image-wrapper'], { width: width, height: height / 2.5 }]}>
            <Image style={styles.image} source={{ uri: item }} resizeMode="cover" />
        </View>
    );

    return (
        <View style={styles.container}>
            <View style={styles.header}>
                <TouchableOpacity onPress={() => backNavigation.goBack()}>
                    <Icon name="chevron-left" size={36} />
                </TouchableOpacity>
                <View style={styles['text-container']}>
                    <Text style={styles.text}>사진 업로드</Text>
                </View>
            </View>
            <FlatList
                data={photo.flatMap((picture: string) => picture)}
                renderItem={renderItems}
                keyExtractor={(item, index) => index.toString()}
                horizontal={true}
                snapToAlignment={'start'}
                snapToInterval={width}
                decelerationRate={'fast'}
                style={styles.flatList}
                pagingEnabled
            />
            <View style={styles['recoding-container']}>
                <Text>{text}</Text>
            </View>
            <View>
                <TouchableOpacity style={styles.button} onPress={recording ? stopRecording : startRecording}>
                    <Icon name="mic" size={48} />
                </TouchableOpacity>
                {recording && (
                    <>
                        <Animated.View
                            style={[styles.wave, { opacity: waveAnim1, transform: [{ scale: waveAnim1 }] }]} />
                        <Animated.View
                            style={[styles.wave, { opacity: waveAnim2, transform: [{ scale: waveAnim2 }] }]} />
                        <Animated.View
                            style={[styles.wave, { opacity: waveAnim3, transform: [{ scale: waveAnim3 }] }]} />
                    </>
                )}
            </View>
            <Button title="업로드" onPress={uploadPhoto} />
            {loading && <Loading />}
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        backgroundColor: '#fff',
        width: '100%',
        height: '100%',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'flex-start',
    },
    header: {
        width: '100%',
        height: 69,
        marginTop: 44,
        padding: 12,
        flexDirection: 'row',
        alignItems: 'center',
        borderBottomWidth: 1,
        borderBottomColor: '#EFF3F1',
    },
    'text-container': {
        width: '100%',
        alignItems: 'center',
        justifyContent: 'center',
    },
    text: {
        fontSize: 16,
        marginRight: 68,
    },

    'recoding-container': {},

    'photo-container': {},

    button: {
        width: 96,
        height: 96,
        borderRadius: 48,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#ff6347',
    },


    flatList: {
        flexGrow: 0, // Prevent FlatList from growing indefinitely
        marginVertical: 24, // Added margin to separate from bottom
    },

    'image-wrapper': {
        width: '100%',
        alignItems: 'center',
    },

    image: {
        width: '50%',
        height: '100%',
    },
    wave: {
        position: 'absolute',
        width: 96,
        height: 96,
        borderRadius: 48,
        backgroundColor: "#ff6347",
    },
});

export default VoiceTest;