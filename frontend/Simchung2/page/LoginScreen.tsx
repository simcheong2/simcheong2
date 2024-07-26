import React, { useState } from "react";
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

    const [formData, setFormData] = useState({ id: '', pwd: '' });

    const handleLogin = async () => {
        try {
            const response = await axios.post(`${BaseUrl}/auth/login`, formData);
            if (response.status === 200) {
                const { accessToken, refreshToken } = response.data;
                await AsyncStorage.setItem('accessToken', accessToken);
                await AsyncStorage.setItem('refreshToken', refreshToken);
                Alert.alert("로그인 성공", "로그인에 성공하였습니다.");
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
                    value={formData.pwd}
                    onChangeText={(text) => setFormData({ ...formData, pwd: text })}
                />
                <TouchableOpacity onPress={toggleSecureTextEntry}>
                    <Icon name={secureTextEntry ? "eye-off-outline" : "eye-outline"} size={24} style={styles.icon} />
                </TouchableOpacity>
            </View>
            <View style={styles.saveIdContainer}>
                <TouchableOpacity onPress={toggleCheckbox} style={styles.checkbox}>
                    <Icon name={isChecked ? "checkbox-marked" : "checkbox-blank-outline"} size={24} />
                </TouchableOpacity>
                <Text>아이디 저장</Text>
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
        padding: 10,
        borderColor: '#ccc',
        borderWidth: 1,
        borderRadius: 5,
        marginBottom: 16,
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
        marginTop: 16,
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
        marginTop: 16,
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
