import React, { useState } from "react";
import Icon from "react-native-vector-icons/MaterialIcons";
import { TouchableOpacity, StyleSheet, Text, TextInput, View, ScrollView, KeyboardAvoidingView, Platform, Alert } from "react-native";
import { useNavigation } from '@react-navigation/native';
import { ScreenNavigationProp } from "../types/navigationTypes";
import axios from 'axios';

function SignupScreen() {
    const [formData, setFormData] = useState({
        id: '',
        password: '',
        email: '',
        name: '',
        nickname: '',
        openingDate: '',
        phone: '',
        verificationCode: '',
    });
    
    const [selectedGender, setSelectedGender] = useState("");
    const [selectedDisability, setSelectedDisability] = useState("");
    const [isVerificationSent, setIsVerificationSent] = useState(false);
    const [isVerified, setIsVerified] = useState(false);
    const navigation = useNavigation<ScreenNavigationProp>();

    const handleChange = (name: string, value: string) => {
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const handleVerificationRequest = () => {
        const smsData = {
            name: formData.name,
            isForeign: false,
            sex: selectedGender === 'male' ? 'MALE' : 'FEMALE',
            openingDate: formData.openingDate,
            phone: formData.phone,
        };

        axios
            .post('http://www.my-first-develop-library.shop:8080/auth/validations/sms', smsData)
            .then((response) => {
                console.log('Response', response);
                if (response.status === 200 && response.data.isSuccess) {
                    Alert.alert("Success", "Verification code sent!");
                    setIsVerificationSent(true);
                } else {
                    Alert.alert("Error", "Verification request failed.");
                }
            })
            .catch((error) => {
                Alert.alert("Error", "Verification request failed. Please try again.");
                console.error(error);
            });
    };

    const handleVerification = () => {
        axios
            .get(`http://www.my-first-develop-library.shop:8080/auth/validations/sms?code=${formData.verificationCode}`)
            .then((response) => {
                if (response.status === 200 && response.data.isSuccess) {
                    Alert.alert("Success", "Phone number verified!");
                    setIsVerified(true);
                } else {
                    Alert.alert("Error", "Verification failed. Incorrect code.");
                }
            })
            .catch((error) => {
                Alert.alert("Error", "Verification failed. Please try again.");
                console.error(error);
            });
    };

    const handleSignup = () => {
        let formattedDate;
        try {
            const [year, month, day] = formData.openingDate.split('-').map(Number);
            if (!year || !month || !day) throw new Error("Invalid date format");
            const date = new Date(year, month - 1, day);
            formattedDate = date.toISOString();
        } catch (error) {
            Alert.alert("Error", "생년월일을 올바른 형식으로 입력해주세요. (예: YYYY-MM-DD)");
            return;
        }

        const data = {
            id: formData.id,
            password: formData.password,
            email: formData.email,
            name: formData.name,
            isForeign: false, 
            sex: selectedGender === 'male' ? 'MALE' : 'FEMALE',
            openingDate: formattedDate,
            phone: formData.phone,
            isDisabled: selectedDisability === 'disabled',
            nickname: formData.nickname,
        };

        axios
            .post('http://www.my-first-develop-library.shop:8080/auth/signup', data)
            .then((response) => {
                console.log('Response', response);
                if (response.status === 200) {
                    Alert.alert("Success", "Signup successful!");
                    navigation.goBack();
                } else {
                    Alert.alert("Error", "Signup failed. not status 200!!");
                }
            })
            .catch((error) => {
                Alert.alert("Error", "Signup failed. Please try again.");
                console.error(error);
            });
    };

    return (
        <KeyboardAvoidingView
            style={{ flex: 1 }}
            behavior={Platform.OS === "ios" ? "padding" : "height"}
            keyboardVerticalOffset={80}
        >
            <ScrollView contentContainerStyle={styles.scrollContainer}>
                <View style={styles.container}>
                    <View style={styles.header}>
                        <TouchableOpacity onPress={() => navigation.goBack()}>
                            <Icon name="chevron-left" size={48} />
                        </TouchableOpacity>
                        <Text style={styles.headerText}>회원가입</Text>
                    </View>
                    <View style={styles.inputSection}>
                        <View style={[styles.inputWrapper, styles.topInputWrapper]}>
                            <Icon style={styles.icon} name="person" size={24} />
                            <TextInput
                                style={styles.textInput}
                                placeholder="아이디"
                                value={formData.id}
                                onChangeText={(text) => handleChange('id', text)}
                            />
                        </View>
                        <View style={styles.inputWrapper}>
                            <Icon style={styles.icon} name="lock" size={24} />
                            <TextInput
                                style={styles.textInput}
                                placeholder="비밀번호"
                                secureTextEntry={true}
                                value={formData.password}
                                onChangeText={(text) => handleChange('password', text)}
                            />
                        </View>
                        <View style={[styles.inputWrapper, styles.bottomInputWrapper]}>
                            <Icon style={styles.icon} name="email" size={24} />
                            <TextInput
                                style={styles.textInput}
                                placeholder="이메일"
                                value={formData.email}
                                onChangeText={(text) => handleChange('email', text)}
                            />
                        </View>
                    </View>
                    <View style={styles.inputSection}>
                        <View style={[styles.inputWrapper, styles.topInputWrapper]}>
                            <Icon style={styles.icon} name="person" size={24} />
                            <TextInput
                                style={styles.textInput}
                                placeholder="이름"
                                value={formData.name}
                                onChangeText={(text) => handleChange('name', text)}
                            />
                        </View>
                        <View style={styles.inputWrapper}>
                            <Icon style={styles.icon} name="person" size={24} />
                            <TextInput
                                style={styles.textInput}
                                placeholder="닉네임"
                                value={formData.nickname}
                                onChangeText={(text) => handleChange('nickname', text)}
                            />
                        </View>
                        <View style={styles.inputWrapper}>
                            <Icon style={styles.icon} name="cake" size={24} />
                            <TextInput
                                style={styles.textInput}
                                placeholder="생년월일 (YYYY-MM-DD)"
                                value={formData.openingDate}
                                onChangeText={(text) => handleChange('openingDate', text)}
                            />
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
                            <Icon style={styles.tabIcon} name="accessible" size={32} />
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
                            <TextInput
                                style={styles.textInput}
                                placeholder="휴대전화번호"
                                value={formData.phone}
                                onChangeText={(text) => handleChange('phone', text)}
                            />
                        </View>
                    </View>
                    {isVerificationSent && !isVerified && (
                        <View style={styles.inputSection}>
                            <View style={[styles.inputWrapper, styles.topInputWrapper]}>
                                <Icon style={styles.icon} name="lock" size={24} />
                                <TextInput
                                    style={styles.textInput}
                                    placeholder="Verification Code"
                                    value={formData.verificationCode}
                                    onChangeText={(text) => handleChange('verificationCode', text)}
                                />
                            </View>
                            <TouchableOpacity style={styles.button} onPress={handleVerification}>
                                <Text style={styles.buttonText}>Verify Code</Text>
                            </TouchableOpacity>
                        </View>
                    )}
                    {!isVerificationSent && (
                        <TouchableOpacity style={styles.button} onPress={handleVerificationRequest}>
                            <Text style={styles.buttonText}>인증요청</Text>
                        </TouchableOpacity>
                    )}
                    {isVerified && (
                        <TouchableOpacity style={styles.button} onPress={handleSignup}>
                            <Text style={styles.buttonText}>회원가입</Text>
                        </TouchableOpacity>
                    )}
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
        marginTop: 24,
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
        borderRadius: 8,
    },
    choicedTabText: {
        color: "#fff",
    },
});

export default SignupScreen;