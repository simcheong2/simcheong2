import React, { useContext } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { useFollowContext } from '../../components/context/FollowContext';

const Follower = () =>{
    const follower = useFollowContext();
    return(
        <View style={styles.container}>
            <Text>{follower?.followers?.nickname}</Text>
        </View>
    )
}

const styles = StyleSheet.create({
    container:{
        flex: 1,
        width: "100%",
        height: "100%",
        alignItems: 'center',
        justifyContent: 'center',
        backgroundColor: '#fff',
    }
})

export default Follower