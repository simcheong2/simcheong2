import React from "react";
import Icon from "react-native-vector-icons/MaterialIcons";
import { TouchableOpacity, StyleSheet, Text, TextInput, View, ScrollView, KeyboardAvoidingView, Platform } from "react-native";

function Signupscreen() {
    return(
        <KeyboardAvoidingView
            style={{ flex: 1 }}
            behavior={Platform.OS === "ios" ? "padding" : "height"}
        >
            <ScrollView contentContainerStyle={styles.container}>
                <View style={styles.header}>
                    <Icon name="arrow-left" size={48} />
                    <Text style={styles.headerText}>회원가입</Text>
                </View>
                <View style={styles.inputSection}>
                    <View style={[styles.inputWrapper,styles.topInputWrapper]}>
                        <Icon style={styles.icon} name="person" size={24} />
                        <TextInput style={styles.textInput} placeholder="아이디" />
                    </View>
                    <View style={styles.inputWrapper}>
                        <Icon style={styles.icon} name="lock" size={24} />
                        <TextInput style={styles.textInput} placeholder="비밀번호" secureTextEntry={true} />
                    </View>
                    <View style={[styles.inputWrapper,styles.bottomInputWrapper]}>
                        <Icon style={styles.icon} name="email" size={24} />
                        <TextInput style={styles.textInput} placeholder="이메일" />
                    </View>
                </View>
                <View style={styles.inputSection}>
                    <View style={[styles.inputWrapper,styles.topInputWrapper]}>
                        <Icon style={styles.icon} name="person" size={24} />
                        <TextInput style={styles.textInput} placeholder="이름" />
                    </View>
                    <View style={styles.inputWrapper}>
                        <Icon style={styles.icon} name="cake" size={24} />
                        <TextInput style={styles.textInput} placeholder="생년월일 8자리" />
                    </View>
                    <View style={styles.inputWrapper}>
                        <Icon style={styles.icon} name="wc" size={24} />
                        <TextInput style={styles.textInput} placeholder="성별" />
                    </View>
                    <View style={styles.inputWrapper}>
                        <Icon style={styles.icon} name="accessible" size={24} />
                        <TextInput style={styles.textInput} placeholder="장애인" />
                    </View>
                    <View style={styles.inputWrapper}>
                        <Icon style={styles.icon} name="phone" size={24} />
                        <TextInput style={styles.textInput} placeholder="통신사 선택" />
                    </View>
                    <View style={[styles.inputWrapper,styles.bottomInputWrapper]}>
                        <Icon style={styles.icon} name="smartphone" size={24} />
                        <TextInput style={styles.textInput} placeholder="휴대전화번호" />
                    </View>
                </View>
                <TouchableOpacity style={styles.button}>
                    <Text style={styles.buttonText}>인증요청</Text>
                </TouchableOpacity>
            </ScrollView>
        </KeyboardAvoidingView>
    );
}

const styles = StyleSheet.create({
    container: {
        flexGrow: 1,
        padding: 16,
        backgroundColor: "#ffffff",
    },
    header: {
        flexDirection: "row",
        alignItems: "center",
    },
    headerText: {
        fontSize: 24,
        fontWeight: "bold",
        color: "#555555",
        marginLeft: 80,
    },
    inputSection: {
        marginVertical: 16,
    },
    inputWrapper: {
        flexDirection: "row",
        alignItems: "center",
        backgroundColor: "#F3F4F6",
      
        padding: 8,
        borderColor: "#555555",
        borderWidth: 1,
     
    },
    topInputWrapper : {
        borderTopLeftRadius: 8,
        borderTopRightRadius: 8,
    },
    bottomInputWrapper : {
        borderBottomLeftRadius: 8,
        borderBottomRightRadius: 8,
    },
    icon: {
        marginRight: 8,
    },
    textInput: {
        flex: 1,
    },
    button: {
        backgroundColor: "#334792",
        padding: 12,
        borderRadius: 8,
        alignItems: "center",
        marginTop: 16,
    },
    buttonText: {
        color: "#fff",
        fontSize: 16,
    },
});

export default Signupscreen;
