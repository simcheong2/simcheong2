import React, { useState } from "react";
import { Image, StyleSheet, Text, TextInput, TouchableOpacity, View } from "react-native";
import Icon from "react-native-vector-icons/MaterialCommunityIcons";

function LoginScreen() {
    const [isChecked, setIsChecked] = useState(false);

    const toggleCheckbox = () => {
        setIsChecked(!isChecked);
    };

    return(
        <View style={styles.container}>
            <Image source={require('../assets/images/logo.png')} style={styles.logo}/>
            <TextInput style={styles.input} placeholder="아이디"/>
            <View style={styles.passwordContainer}>
                <TextInput style={styles.passwordInput} placeholder="비밀번호" secureTextEntry={true}/>
                <Icon name="eye-off-outline" size={24} style={styles.icon}/>
            </View>
            <View style={styles.saveIdContainer}>
                <TouchableOpacity onPress={toggleCheckbox} style={styles.checkbox}>
                    <Icon name={isChecked ? "checkbox-marked" : "checkbox-blank-outline"} size={24} />
                </TouchableOpacity>
                <Text>아이디 저장</Text>
            </View>
            <TouchableOpacity style={styles.button}>
                <Text style={styles.buttonText}>로그인</Text>
            </TouchableOpacity>
            <TouchableOpacity style={styles.buttonSignup}>
                <Text style={styles.buttonTextSignup}>회원가입</Text>
            </TouchableOpacity>
        </View>
    )
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
        justifyContent:"flex-end",
        marginBottom: 16,
        marginLeft:200,
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
        borderColor:"#555555",
        borderWidth:1,
    },
    buttonText: {
        color: "#fff",
        fontSize: 16,
        fontWeight:"bold",
    },
    buttonSignup: {
        backgroundColor: "#FFFFFF",
        padding: 12,
        borderRadius: 8,
        alignItems: "center",
        marginTop: 16,
        width: '100%',
        borderColor:"#555555",
        borderWidth:1,
       
    },
    buttonTextSignup: {
        color: "#555555",
        fontSize: 16,
        fontWeight:"bold",
    },
})

export default LoginScreen;
