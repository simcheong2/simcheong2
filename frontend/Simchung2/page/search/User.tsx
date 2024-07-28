import React from 'react';
import { Alert, Image, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { SearchResponse } from '../../interface/user/Search';

interface UserProps{
    user: SearchResponse
}

const User = ({user}: UserProps) => {
    const handlePress = () => {
        Alert.alert(`User ${user.nickname} pressed!`);
        // 여기에 추가적인 작업을 수행할 수 있다.
    };

    return(
        <TouchableOpacity style={styles.userContainer} onPress={handlePress}>
            <Image
                source={{ uri: user.profileUrl || 'https://reactjs.org/logo-og.png' }} // 기본 프로필 이미지 URL
                style={styles.profileImage}
            />
            <Text style={styles.nickname} numberOfLines={1}>
                {user.nickname}
            </Text>
        </TouchableOpacity>
    )
}

const styles = StyleSheet.create({
    userContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        paddingVertical: 10,
    },
    profileImage: {
        width: 64,
        height: 64,
        borderRadius: 32,
        marginRight: 12,
    },
    nickname: {
        fontSize: 20,
        color: '#555',
        maxWidth: 200, // 닉네임 텍스트의 최대 너비를 설정합니다
    },
});

export default User