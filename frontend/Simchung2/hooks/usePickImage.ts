import React, { useState } from "react";
import * as ImagePicker from "expo-image-picker";
import { Alert } from 'react-native';

const usePickImage = (initialState: string ) => {
    const [pickedImg, setPickedImg] = useState<string>(initialState);
    // 권한부여
    const [status, requestPermission] = ImagePicker.useMediaLibraryPermissions();

    const pickImage = async (): Promise<void> => {
        if (!status?.granted) {
            const permissions = await requestPermission();
            if (!permissions.granted) {
                return;
            }
        }
        const result = await ImagePicker.launchImageLibraryAsync({
            mediaTypes: ImagePicker.MediaTypeOptions.Images,
            allowsEditing: false,
            aspect: [4, 3],
            quality: 1,
        });
        if (!result.canceled && result.assets.length > 0) {
            const uri = result.assets[0].uri;
            setPickedImg(uri);
            console.log("Selected image URI: ", uri);
        } else {
            Alert.alert("You did not select any image.");
        }
    };

    return [pickedImg, setPickedImg, pickImage] as const;
};

export default usePickImage;