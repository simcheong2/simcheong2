import React, { useState } from 'react';
import { Alert, Image, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { MyProfile } from '../../../interface/user/Profile';
import * as ImagePicker from 'expo-image-picker';

interface ProfileEditProps {
    profile: MyProfile;
}

const ProfileEdit = ({ profile }: ProfileEditProps) => {
    const [selectImage, setSelectImage] = useState<string>(profile.profile.profileUrl)

    const pickImageAsync = async () => {
        let result = await ImagePicker.launchImageLibraryAsync({
            allowsEditing: true,
            quality: 1,
        });

        if (!result.canceled) {
            setSelectImage(result.assets[0].uri);
        } else {
            Alert.alert("You did not select any image.");
        }
    };

    return (
        <View style={styles.container}>
            {selectImage && <Image source={{uri: selectImage}} style={styles.image} />}
            <TouchableOpacity style={styles['profile-btn']} onPress={pickImageAsync}>
                <Text style={styles['profile-text']}>프로필 사진 수정</Text>
            </TouchableOpacity>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        justifyContent: 'center',
        alignItems: 'center',
    },

    "profile-btn":{
        width: 254,
        height: 48,
        justifyContent: 'center',
        alignItems: 'center',
    },

    "profile-text":{
        fontSize: 16,
    },

    image: {
        width: 140,
        height: 140,
        resizeMode: 'cover',
        borderRadius: 70,
    },
});

export default ProfileEdit;