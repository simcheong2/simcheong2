import React from "react";
import { View,Button } from "react-native";

function MyPageScreen({navigation}) {
    return(
        <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
    
        <Button
          onPress={() => navigation.navigate('Signup')}
          title="회원가입"
        />
      </View>
    )
}

export default MyPageScreen