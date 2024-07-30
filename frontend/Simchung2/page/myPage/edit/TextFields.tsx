import React, { useState } from 'react';
import { Alert, StyleSheet, Text, View, Pressable } from 'react-native';
import * as ImagePicker from 'expo-image-picker';

const TextFields = () => {
    const [name, setName] = useState<string>('');
    const [selectImage, setSelectImage] = useState<string>('')

    const setNameHandler = (e: string) => {
        setName((name) => (name=e));
    }

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
            <View style={styles.imageContainer}>
            </View>
            <View style={styles.footerContainer}>
                <View>
                    <Pressable onPress={pickImageAsync}>
                        <Text >Choose a photo</Text>
                    </Pressable>
                </View>
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#25292e',
        alignItems: 'center',
    },
    imageContainer: {
        flex:1,
        paddingTop: 58
    },
    footerContainer: {
        flex: 1 / 3,
        alignItems: 'center',
    },
});

export default TextFields