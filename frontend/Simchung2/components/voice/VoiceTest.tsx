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
import IconCommunity from 'react-native-vector-icons/MaterialCommunityIcons';

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
    const pulseAnim = useRef(new Animated.Value(1)).current;

    useEffect(() => {
        if (recording) {
            startPulsing();
        } else {
            stopPulsing();
        }
    }, [recording]);

    const startPulsing = () => {
        Animated.loop(
            Animated.sequence([
                Animated.timing(pulseAnim, {
                    toValue: 1.5,
                    duration: 500,
                    useNativeDriver: true,
                }),
                Animated.timing(pulseAnim, {
                    toValue: 1,
                    duration: 500,
                    useNativeDriver: true,
                }),
            ]),
        ).start();
    };

    const stopPulsing = () => {
        pulseAnim.setValue(1);
        // @ts-ignore
        Animated.timing(pulseAnim).stop();
    };

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
    }

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
        <View accessible importantForAccessibility='yes' accessibilityLabel='첫번째 사진입니다. 원하시는 사진이 맞으신가요?' style={[styles['image-wrapper'], { width: width, height: height / 2.5 }]}>
            <Image style={styles.image} source={{ uri: item }} resizeMode="cover" />
        </View>
    );

    return (
        <View style={styles.container} accessible={false}>
            <View style={styles.header} accessible={false}>
                <TouchableOpacity onPress={() => backNavigation.goBack()} accessible accessibilityLabel='뒤로가기 버튼 입니다. 뒤로가기를 원하면 두번 탭해주세요.' importantForAccessibility='yes'>
                    <Icon name="chevron-left" size={36} />
                </TouchableOpacity>
                <View style={styles['text-container']} accessible importantForAccessibility='yes' accessibilityLabel='사진 업로드'>
                    <Text style={styles.text}>사진 업로드</Text>
                </View>
                <TouchableOpacity onPress={uploadPhoto} accessible importantForAccessibility='yes' accessibilityLabel='업로드 버튼입니다. 게시글 업로드를 원하시면 두번 탭하세요.'>
                    <IconCommunity name="file-upload-outline" size={36} />
                </TouchableOpacity>
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
                <Text numberOfLines={3} ellipsizeMode="tail" style={styles['recoding-text']}>{text}</Text>
            </View>
            <View>
                <TouchableOpacity  accessible importantForAccessibility='yes' accessibilityLabel='음성을 통해 현재 느낌을 표현 해주세요. 두번 탭하세요.' style={styles.button} onPress={recording ? stopRecording : startRecording}>
                    <Animated.View style={{ transform: [{ scale: pulseAnim }] }}>
                        <Icon name="mic" size={48} style={{ color: '#fff' }} />
                    </Animated.View>
                </TouchableOpacity>
            </View>
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
        display: 'flex',
        width: '100%',
        height: 69,
        marginTop: 44,
        padding: 12,
        flexDirection: 'row',
        justifyContent: 'space-between',
        borderBottomWidth: 1,
        borderBottomColor: '#EFF3F1',
    },
    'text-container': {
        alignItems: 'center',
        justifyContent: 'center',
    },
    text: {
        fontSize: 16,
    },

    'recoding-container': {
        width: '80%',
        height: 64,
        flexDirection: 'row',
        alignItems: 'flex-start',
        justifyContent: 'center',
        marginBottom: 20,
    },

    'recoding-text': {
        fontSize: 16,
    },

    button: {
        width: 96,
        height: 96,
        borderRadius: 48,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#ff6347',
    },

    flatList: {
        flexGrow: 0,
        marginTop: 24,
        marginBottom: 16,
    },

    'image-wrapper': {
        width: '100%',
        alignItems: 'center',
    },

    image: {
        width: '50%',
        height: '100%',
    },
});

export default VoiceTest;