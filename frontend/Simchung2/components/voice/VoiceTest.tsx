import React, { useEffect, useState } from 'react';
import { View, StyleSheet, Button, Text, Alert } from 'react-native';
import { Audio } from 'expo-av';
import { useRecoilValue } from 'recoil';
import accessTokenAtom from '../../recoil/atom/accessTokenAtom';
import axios from 'axios';
import { useNavigation } from '@react-navigation/native';
import { ScreenNavigationProp } from '../../types/navigationTypes';
import Loading from '../../page/loading/Loading';

// @ts-ignore
const VoiceTest = ({ route }) => {
    const {photo} = route.params;

    const [recording, setRecording] = useState<Audio.Recording | undefined>(undefined);
    const [text, setText] = useState<string>('');
    const [permissionResponse, requestPermission] = Audio.usePermissions();
    const accessToken = useRecoilValue(accessTokenAtom);
    const baseURL = 'http://www.my-first-develop-library.shop:8080';
    const navigation = useNavigation<ScreenNavigationProp>();
    const [loading, setLoading] = useState<boolean>(false);

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

    return (loading ? <Loading /> :
            <View style={styles.container}>
                <Button
                    title={recording ? 'Stop Recording' : 'Start Recording'}
                    onPress={recording ? stopRecording : startRecording}
                />
                <Button title="업로드" onPress={uploadPhoto} />
            </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },

    button:{
        margin: 16,
    }
})

export default VoiceTest;