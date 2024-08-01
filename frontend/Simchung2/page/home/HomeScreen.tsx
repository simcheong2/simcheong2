import React, { useEffect, useState } from 'react';
import { Image, StyleSheet, Text, View } from "react-native";
import Tabs from '../../navigation/home/Tabs';
import Snack from '../../components/snack/Snack';

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: "#fff",
        width: "100%",
        height: "100%",
        flexDirection: "column"
    },
    header: {
        width: "100%",
        height: 69,
        marginTop: 44,
        padding: 12,
        flexDirection: "row",
        alignItems: "center"
    },
    tab: {
        width: "100%",
        height: "95%",
    },
    "logo-image": {
        width: 145,
        height: 45,
    }
})

const HomeScreen = ({ route = {} }) => {
    // @ts-ignore
    const { params } = route || {};
    const { upload } = params || {};

    const [visible, setVisible] = useState<boolean>(false);

    useEffect(() => {
        if(upload!=undefined){
            setVisible(upload);
        }
    }, []);

    const onDismissSnackBar = () => {
        setVisible(false)
    }

    const onPress = () => {

    }

    return(
        <View accessibilityLabel="심청2 홈 화면 입니다. 당신의 친구들과의 추억을 즐겨보세요." style={styles.container}>
            <View style={styles.header}>
                <Image 
                    style={styles["logo-image"]} 
                    source={require('../../assets/images/logo.png')} 
                    resizeMode="contain" />
            </View>
            <View style={styles.tab}>
                <Tabs/>
            </View>
            <Snack visible={visible} onDismissSnackBar={onDismissSnackBar} onPress={onPress} content='업로드 완료했습니다.'/>
        </View>
    )
}

export default HomeScreen