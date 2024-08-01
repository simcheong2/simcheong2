import React from 'react';
import { StyleSheet, View } from 'react-native';

interface FollowPage{
    select: 'my'|'other';
}

const FollowPage = ({select}:FollowPage) => {
    return(
        <View style={styles.container}>
        </View>
    )
}

const styles = StyleSheet.create({
    container:{
        flex:1,
        width: "100%",
        height: "100%",
        backgroundColor: "#fff",
    },

})

export default FollowPage