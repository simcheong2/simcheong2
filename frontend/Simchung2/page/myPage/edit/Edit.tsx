import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { MyProfile } from '../../../interface/user/Profile';

interface EditProps{
    profile: MyProfile
}

const Edit = ({profile}:EditProps) => {
    return(
        <View style={styles.container}>
            <Text>텍스트</Text>
        </View>
    )
}

const styles = StyleSheet.create({
    container:{
        flex: 1,
        width: "100%",
        height: "100%",
        backgroundColor: "#fff",
    }
})

export default Edit