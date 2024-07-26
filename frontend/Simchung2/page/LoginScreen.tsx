import React from "react";
import { Image, StyleSheet, Text, TextInput, TouchableOpacity, View } from "react-native";
import Icon from "react-native-vector-icons/MaterialCommunityIcons";
function LoginScreen() {
    return(
       <View style={styles. container}>
        <Image source={require('../assets/images/logo.png')}/>
        <TextInput placeholder="아이디"/>
        <View>
            <TextInput placeholder="비밀번호"/>
            <Icon name="eye-off-outline"/>
        </View>
        <View>

            <Text>아이디 저장</Text>
        </View>
        <TouchableOpacity>
            <Text>로그인</Text>
        </TouchableOpacity>
        <TouchableOpacity>
            <Text>회원가입</Text>
        </TouchableOpacity>
       </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flexGrow: 1,
        padding: 16,
        backgroundColor: "#ffffff",
    },
})

export default LoginScreen