import React, { useRef, useState } from 'react';
import {
    Alert,
    Image,
    Keyboard,
    StyleSheet,
    Text,
    TouchableOpacity,
    TouchableWithoutFeedback,
    View,
} from 'react-native';
import { MyProfile } from '../../../interface/user/Profile';
import * as ImagePicker from 'expo-image-picker';
import { TextInput } from 'react-native-paper';
import Icon from 'react-native-vector-icons/MaterialIcons';

interface ProfileEditProps {
    profile: MyProfile;
    onPress: (uri:string)=>void
}

const ProfileEdit = ({ profile, onPress }: ProfileEditProps) => {
    const [selectImage, setSelectImage] = useState<string>(profile.profile.profileUrl);
    const textInputRef = useRef(null);  // TextInput 참조를 위한 ref 생성

    const dismissKeyboard = () => {
        Keyboard.dismiss();  // 키보드 닫기
        if (textInputRef.current) {
            // @ts-ignore
            textInputRef.current.blur();
        }
    };

    const pickImageAsync = async () => {
        let result = await ImagePicker.launchImageLibraryAsync({
            allowsEditing: true,
            quality: 1,
        });

        if (!result.canceled) {
            setSelectImage(result.assets[0].uri);
            onPress(result.assets[0].uri);
        } else {
            Alert.alert('You did not select any image.');
        }
    };

    return (
        <TouchableWithoutFeedback onPress={dismissKeyboard}>
            <View style={styles.container}>
                {selectImage ? <Image source={{ uri: selectImage }} style={styles.image} /> :
                    <Image style={styles.image} />}
                <TouchableOpacity style={styles['profile-btn']} onPress={pickImageAsync}>
                    <Text style={styles['profile-text']}>프로필 사진 수정</Text>
                </TouchableOpacity>
                <TextInput
                    style={styles.input}
                    mode="outlined"
                    label="사용자 이름"
                    value={profile.profile.nickname}
                    textColor="#555555"
                    ref={textInputRef} disabled />
                <TextInput
                    style={styles.input}
                    mode="outlined"
                    label="이메일"
                    value={profile.profile.email}
                    textColor="#555555"
                    ref={textInputRef} disabled />
                <View style={styles.choiceTabContainer}>
                    <Icon style={styles.tabIcon} name="wc" size={24} />
                    <TouchableOpacity disabled
                                      style={[
                                          styles.choiceTab,
                                          profile.profile.sex && styles.choicedTab,
                                      ]}
                    >
                        <Text
                            style={[
                                styles.choiceTabText,
                                profile.profile.sex && styles.choicedTabText,
                            ]}
                        >
                            남성
                        </Text>
                    </TouchableOpacity>
                    <TouchableOpacity disabled
                                      style={[
                                          styles.choiceTab,
                                          !profile.profile.sex && styles.choicedTab,
                                      ]}
                    >
                        <Text
                            style={[
                                styles.choiceTabText,
                                !profile.profile.sex && styles.choicedTabText,
                            ]}
                        >
                            여성
                        </Text>
                    </TouchableOpacity>
                </View>
                <View style={styles.choiceTabContainer}>
                    <Icon style={styles.tabIcon} name="accessible" size={24} />
                    <TouchableOpacity disabled
                        style={[
                            styles.choiceTab,
                            profile.profile.isDisabled && styles.choicedTab,
                        ]}
                    >
                        <Text
                            style={[
                                styles.choiceTabText,
                                profile.profile.isDisabled && styles.choicedTabText,
                            ]}
                        >
                            장애인
                        </Text>
                    </TouchableOpacity>
                    <TouchableOpacity disabled
                        style={[
                            styles.choiceTab,
                            !profile.profile.isDisabled && styles.choicedTab,
                        ]}
                    >
                        <Text
                            style={[
                                styles.choiceTabText,
                                !profile.profile.isDisabled && styles.choicedTabText,
                            ]}
                        >
                            비장애인
                        </Text>
                    </TouchableOpacity>
                </View>
            </View>
        </TouchableWithoutFeedback>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        alignItems: 'center',
        padding: 20,
    },

    'profile-btn': {
        marginTop: 20,
        width: 254,
        height: 48,
        justifyContent: 'center',
        alignItems: 'center',
    },

    'profile-text': {
        fontSize: 16,
    },

    image: {
        width: 120,
        height: 120,
        resizeMode: 'cover',
        borderRadius: 60,
        borderWidth: 1,
        borderColor: '#555',
    },

    input: {
        width: '100%',
        fontSize: 20,
        lineHeight: 45,
        marginTop: 16,
        backgroundColor: "#F6F6F6",
    },
    choiceTabContainer: {
        marginTop: 16,
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',
        backgroundColor: '#F3F4F6',
        borderWidth: 1,
        borderColor: '#555555',
        shadowColor: "#000", // 그림자 색상
        shadowOffset: { width: 0, height: 2 }, // 그림자 오프셋
        shadowOpacity: 0.25, // 그림자 불투명도
        shadowRadius: 3.84, // 그림자 반경
        elevation: 5, // Android 그림자 효과
    },
    choiceTab: {
        flex: 1,
        borderLeftWidth: 1,
        paddingVertical: 10,
        backgroundColor: '#F6F6F6',
        alignItems: 'center',
        justifyContent: 'center',
    },
    tabIcon: {
        marginHorizontal: 8,
    },
    choiceTabText: {
        color: '#555555',
    },
    choicedTab: {
        backgroundColor: '#334792',
        borderColor: '#334792',
    },
    choicedTabText: {
        color: '#fff',
        fontSize: 16,
        fontWeight: 'bold'
    },
});

export default ProfileEdit;