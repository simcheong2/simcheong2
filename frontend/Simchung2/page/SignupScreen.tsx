import React, { useState } from "react";
import Icon from "react-native-vector-icons/MaterialIcons";
import { TouchableOpacity, StyleSheet, Text, TextInput, View, ScrollView, KeyboardAvoidingView, Platform } from "react-native";

function Signupscreen() {
    const [selectedGender, setSelectedGender] = useState("");
    const [selectedDisability, setSelectedDisability] = useState("");

    return (
        <KeyboardAvoidingView
            style={{ flex: 1 }}
            behavior={Platform.OS === "ios" ? "padding" : "height"}
            keyboardVerticalOffset={80} // iOS의 경우 필요한 오프셋 설정
        >
            <ScrollView contentContainerStyle={styles.scrollContainer}>
                <View style={styles.container}>
                    <View style={styles.header}>
                        <Icon name="chevron-left" size={48} />
                        <Text style={styles.headerText}>회원가입</Text>
                    </View>
                    <View style={styles.inputSection}>
                        <View style={[styles.inputWrapper, styles.topInputWrapper]}>
                            <Icon style={styles.icon} name="person" size={24} />
                            <TextInput style={styles.textInput} placeholder="아이디" />
                        </View>
                        <View style={styles.inputWrapper}>
                            <Icon style={styles.icon} name="lock" size={24} />
                            <TextInput style={styles.textInput} placeholder="비밀번호" secureTextEntry={true} />
                        </View>
                        <View style={[styles.inputWrapper, styles.bottomInputWrapper]}>
                            <Icon style={styles.icon} name="email" size={24} />
                            <TextInput style={styles.textInput} placeholder="이메일" />
                        </View>
                    </View>
                    <View style={styles.inputSection}>
                        <View style={[styles.inputWrapper, styles.topInputWrapper]}>
                            <Icon style={styles.icon} name="person" size={24} />
                            <TextInput style={styles.textInput} placeholder="이름" />
                        </View>
                        <View style={styles.inputWrapper}>
                            <Icon style={styles.icon} name="person" size={24} />
                            <TextInput style={styles.textInput} placeholder="닉네임" />
                        </View>
                        <View style={styles.inputWrapper}>
                            <Icon style={styles.icon} name="cake" size={24} />
                            <TextInput style={styles.textInput} placeholder="생년월일 8자리" />
                        </View>
                        <View style={styles.choiceTabContainer}>
                            <Icon style={[styles.icon, styles.tabIcon]} name="wc" size={24} />
                            <TouchableOpacity
                                style={[
                                    styles.choiceTab,
                                    selectedGender === 'male' && styles.choicedTab,
                                ]}
                                onPress={() => setSelectedGender('male')}
                            >
                                <Text
                                    style={[
                                        styles.choiceTabText,
                                        selectedGender === 'male' && styles.choicedTabText,
                                    ]}
                                >
                                    남
                                </Text>
                            </TouchableOpacity>
                            <TouchableOpacity
                                style={[
                                    styles.choiceTab,
                                    selectedGender === 'female' && styles.choicedTab,
                                ]}
                                onPress={() => setSelectedGender('female')}
                            >
                                <Text
                                    style={[
                                        styles.choiceTabText,
                                        selectedGender === 'female' && styles.choicedTabText,
                                    ]}
                                >
                                    여
                                </Text>
                            </TouchableOpacity>
                        </View>
                        <View style={styles.choiceTabContainer}>
                            <Icon style={styles.tabIcon} name="accessible" size={24} />
                            <TouchableOpacity
                                style={[
                                    styles.choiceTab,
                                    selectedDisability === 'disabled' && styles.choicedTab,
                                ]}
                                onPress={() => setSelectedDisability('disabled')}
                            >
                                <Text
                                    style={[
                                        styles.choiceTabText,
                                        selectedDisability === 'disabled' && styles.choicedTabText,
                                    ]}
                                >
                                    장애인
                                </Text>
                            </TouchableOpacity>
                            <TouchableOpacity
                                style={[
                                    styles.choiceTab,
                                    selectedDisability === 'nondisabled' && styles.choicedTab,
                                ]}
                                onPress={() => setSelectedDisability('nondisabled')}
                            >
                                <Text
                                    style={[
                                        styles.choiceTabText,
                                        selectedDisability === 'nondisabled' && styles.choicedTabText,
                                    ]}
                                >
                                    비장애인
                                </Text>
                            </TouchableOpacity>
                        </View>
                        <View style={[styles.inputWrapper, styles.bottomInputWrapper]}>
                            <Icon style={styles.icon} name="smartphone" size={24} />
                            <TextInput style={styles.textInput} placeholder="휴대전화번호" />
                        </View>
                    </View>
                    <TouchableOpacity style={styles.button}>
                        <Text style={styles.buttonText}>인증요청</Text>
                    </TouchableOpacity>
                </View>
            </ScrollView>
        </KeyboardAvoidingView>
    );
}

const styles = StyleSheet.create({
    scrollContainer: {
        flexGrow: 1,
    },
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
    topInputWrapper: {
        borderTopLeftRadius: 8,
        borderTopRightRadius: 8,
    },
    bottomInputWrapper: {
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
    choiceTabContainer: {
        flexDirection: 'row',
        flex: 1,
        alignItems: "center",
        justifyContent: "center",
        backgroundColor: "#F3F4F6",
        borderWidth: 1,
        borderColor: "#555555",
    },
    choiceTab: {
        flex: 1,
        padding: 10,
        backgroundColor: "#F3F4F6",
        alignItems: "center",
        justifyContent: "center",
    },
    tabIcon: {
        marginLeft: 8,
    },
    choiceTabText: {
        color: "#555555",
    },
    choicedTab: {
        backgroundColor: "#334792",
        borderColor: "#334792",
    },
    choicedTabText: {
        color: "#fff",
    },
});

export default Signupscreen;
