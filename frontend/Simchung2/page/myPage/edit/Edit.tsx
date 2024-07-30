import React, { useState } from 'react';
import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { MyProfile } from '../../../interface/user/Profile';
import Icon from 'react-native-vector-icons/MaterialIcons';
import { useNavigation } from '@react-navigation/native';
import { FeedNavigationProp } from '../../../types/navigationTypes';
import ProfileEdit from './ProfileEdit';
import CameraTest from '../../../components/image/CameraTest';

interface EditProps{
    profile: MyProfile
}

const Edit = ({profile}:EditProps) => {
    const navigation = useNavigation<FeedNavigationProp>();
    const [name, setName] = useState<string>('');

    const setNameHandler = (e: string) => {
        setName((name) => (name=e));
    }

    return(
        <View style={styles.container}>
            <View style={styles.header}>
                <TouchableOpacity onPress={() => navigation.goBack()}>
                    <Icon name="chevron-left" size={48} />
                </TouchableOpacity>
                <View style={styles['header-container']}>
                    <Text style={styles['header-title']}>프로필 편집</Text>
                </View>
            </View>
            <ProfileEdit profile={profile}/>
        </View>
    )
}

const styles = StyleSheet.create({
    container:{
        flex: 1,
        width: "100%",
        height: "100%",
        backgroundColor: "#fff",
    },

    header: {
        width: '100%',
        padding: 12,
        marginTop: 18,
        flexDirection: 'row',
        alignItems: 'center',
    },

    'header-container': {
        width:"72%",
        alignItems: 'center',
    },

    'header-title': {
        fontSize: 24,
        fontWeight: 'bold',
        color: '#555',
    },
})

export default Edit