import React, { useState } from 'react';
import { Alert, Dimensions, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { MyProfile } from '../../../interface/user/Profile';
import Icon from 'react-native-vector-icons/MaterialIcons';
import { CommonActions, useNavigation } from '@react-navigation/native';
import { FeedNavigationProp } from '../../../types/navigationTypes';
import ProfileEdit from './ProfileEdit';
import FeatherIcon from 'react-native-vector-icons/Feather';
import * as ImageManipulator from 'expo-image-manipulator';
import { ImageResize } from '../../../util/common/Common';
import axios from 'axios';
import { useRecoilValue } from 'recoil';
import accessTokenAtom from '../../../recoil/atom/accessTokenAtom';

interface EditProps {
    profile: MyProfile;
}

const Edit = ({ profile }: EditProps) => {
    const { width } = Dimensions.get('window');
    const [selectUri, setSelectUri] = useState<string>(profile.profile.profileUrl);
    const navigation = useNavigation<FeedNavigationProp>();
    const accessToken = useRecoilValue(accessTokenAtom);

    const editHandler = async () => {
        if (selectUri === profile.profile.profileUrl) {
            Alert.alert('똑같은 사진으로는 수정 불가능 합니다.');
            navigation.goBack();
            return;
        }

        const { manipulatedImage, fileName, mimeType } = await ImageResize(selectUri);
        upload(manipulatedImage.uri, fileName, mimeType);
        navigation.navigate('Profile',{refresh: true});
    };

    const selectImage = (uri: string) => {
        setSelectUri(uri);
    };

    const upload = async (uri: string, fileName: string | undefined, mimeType: string) => {
        const formData = new FormData();
        formData.append('image', {
            uri,
            name: fileName,
            type: mimeType,
        });

        console.log(accessToken)

        try {
            await axios.post('http://www.my-first-develop-library.shop:8080/users/profile-url',
                formData, {
                    headers: {
                        Authorization: `Bearer ${accessToken}`,
                        'Content-Type': 'multipart/form-data',
                    },
                },
            ).then((response) => {
                console.log('Success Image', response);
            }).catch((error) => {
                console.log('Failed Image', error);
            });
        } catch (error) {
            console.log('Error', error);
        }
    };

    return (
        <View style={styles.container}>
            <View style={styles.header}>
                <TouchableOpacity onPress={() => navigation.goBack()}>
                    <Icon name="chevron-left" size={36} />
                </TouchableOpacity>
                <View style={[styles['header-container'], { width: width - 84 }]}>
                    <Text style={styles['header-title']}>프로필 편집</Text>
                </View>
                <TouchableOpacity onPress={editHandler}>
                    <FeatherIcon name="edit" size={24} />
                </TouchableOpacity>
            </View>
            <ProfileEdit profile={profile} onPress={selectImage} />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        width: '100%',
        height: '100%',
        backgroundColor: '#fff',
    },

    header: {
        width: '100%',
        padding: 12,
        marginTop: 18,
        flexDirection: 'row',
        alignItems: 'center',
    },

    'header-container': {
        alignItems: 'center',
        justifyContent: 'center',
    },

    'header-title': {
        fontSize: 24,
        fontWeight: 'bold',
        color: '#555',
    },
});

export default Edit;