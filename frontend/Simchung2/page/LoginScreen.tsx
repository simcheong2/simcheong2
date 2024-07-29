import React, { useState,useEffect } from "react";
import { Image, StyleSheet, Text, TextInput, TouchableOpacity, View, Alert } from "react-native";
import Icon from "react-native-vector-icons/MaterialCommunityIcons";
import axios from 'axios';
import AsyncStorage from "@react-native-async-storage/async-storage";

function LoginScreen() {
    const [isChecked, setIsChecked] = useState(false);
    const [secureTextEntry, setSecureTextEntry] = useState(true);
    const BaseUrl = 'http://www.my-first-develop-library.shop:8080';

    const toggleCheckbox = () => {
        setIsChecked(!isChecked);
    };

    const toggleSecureTextEntry = () => {
        setSecureTextEntry(!secureTextEntry);
    };

    const [formData, setFormData] = useState({ id: '', password: '' });
    useEffect(() => {
        const autoLogin = async () => {
            const storedIsChecked = await AsyncStorage.getItem('isChecked');
            if (storedIsChecked === 'true') {
                const refreshToken = await AsyncStorage.getItem('refreshToken');
                if (refreshToken) {
                    try {
                        const response = await axios.post(`${BaseUrl}/auth/reissue`, { refreshToken });
                        if (response.status === 200) {
                            const { accessToken } = response.data;
                            await AsyncStorage.setItem('accessToken', accessToken);
                            Alert.alert("자동 로그인 성공", "자동 로그인에 성공하였습니다.");
                            // 여기 아래에, 아마 조합된 url이 추가 되지 않을까 싶네요.
                        } else {
                            Alert.alert("토큰 재발급 실패", "다시 로그인해 주세요.");
                        }
                    } catch (error) {
                        Alert.alert("토큰 재발급 에러", "다시 로그인해 주세요.");
                    }
                } else {
                    Alert.alert("리프레시 토큰 없음", "다시 로그인해 주세요.");
                }
            }
        };

        autoLogin();
    }, []);

    const handleLogin = async () => {
        try {
            const response = await axios.post(`${BaseUrl}/auth/login`, formData);
            
            if (response.status === 200) {
                const { accessToken, refreshToken } = response.data;
                await AsyncStorage.setItem('accessToken', accessToken);
                await AsyncStorage.setItem('refreshToken', refreshToken);
                if (isChecked) {
                    await AsyncStorage.setItem('isChecked', 'true');
                } else {
                    await AsyncStorage.setItem('isChecked', 'false');
                }
                Alert.alert("로그인 성공", "로그인에 성공하였습니다.");
                // navigate to the main screen or perform further actions
            } else {
                Alert.alert("로그인 실패", "아이디 또는 비밀번호를 확인해주세요.");
            }
        } catch (error) {
            Alert.alert("로그인 실패", "아이디 또는 비밀번호를 확인해주세요.");
        }
    };

    return (
        <View style={styles.container}>
            <Image source={require('../assets/images/logo.png')} style={styles.logo} />
            <TextInput
                style={styles.input}
                placeholder="아이디"
                value={formData.id}
                onChangeText={(text) => setFormData({ ...formData, id: text })}
            />
            <View style={styles.passwordContainer}>
                <TextInput
                    style={styles.passwordInput}
                    placeholder="비밀번호"
                    secureTextEntry={secureTextEntry}
                    value={formData.password}
                    onChangeText={(text) => setFormData({ ...formData, password: text })}
                />
                <TouchableOpacity onPress={toggleSecureTextEntry}>
                    <Icon name={secureTextEntry ? "eye-off-outline" : "eye-outline"} size={24} style={styles.icon} />
                </TouchableOpacity>
            </View>
            <View style={styles.saveIdContainer}>
                <TouchableOpacity onPress={toggleCheckbox} style={styles.checkbox}>
                    <Icon name={isChecked ? "checkbox-marked" : "checkbox-blank-outline"} size={24} />
                </TouchableOpacity>
                <Text>자동 로그인</Text>
            </View>
            <TouchableOpacity style={styles.button} onPress={handleLogin}>
                <Text style={styles.buttonText}>로그인</Text>
            </TouchableOpacity>
            <TouchableOpacity style={styles.buttonSignup}>
                <Text style={styles.buttonTextSignup}>회원가입</Text>
            </TouchableOpacity>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flexGrow: 1,
        justifyContent: 'center',
        alignItems: 'center',
        padding: 16,
        backgroundColor: "#ffffff",
    },
    logo: {
        marginBottom: 32,
    },
    input: {
        width: '100%',
        borderColor: '#ccc',
        borderWidth: 1,
        borderRadius: 5,
        marginBottom: 16,
        paddingLeft:22,
        paddingTop:10,
        paddingBottom:10,
    },
    passwordContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        width: '100%',
        borderColor: '#ccc',
        borderWidth: 1,
        borderRadius: 5,
        marginBottom: 16,
        paddingHorizontal: 10,
    },
    passwordInput: {
        flex: 1,
        padding: 10,
    },
    icon: {
        marginLeft: 10,
    },
    saveIdContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: "flex-end",
        marginBottom: 16,
        marginLeft: 200,
    },
    checkbox: {
        marginRight: 10,
    },
    button: {
        backgroundColor: "#334792",
        padding: 12,
        borderRadius: 8,
        alignItems: "center",
        width: '100%',
        borderColor: "#555555",
        borderWidth: 1,
    },
    buttonText: {
        color: "#fff",
        fontSize: 16,
        fontWeight: "bold",
    },
    buttonSignup: {
        backgroundColor: "#FFFFFF",
        padding: 12,
        borderRadius: 8,
        alignItems: "center",
        marginTop: 8,
        width: '100%',
        borderColor: "#555555",
        borderWidth: 1,
    },
    buttonTextSignup: {
        color: "#555555",
        fontSize: 16,
        fontWeight: "bold",
    },
})

export default LoginScreen;
