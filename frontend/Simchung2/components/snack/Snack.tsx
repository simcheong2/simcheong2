import React from 'react';
import { Snackbar } from 'react-native-paper';

interface SnackProps{
    visible: boolean;
    onDismissSnackBar: ()=>void;
    onPress: ()=>void;
    content: string;
}

const Snack = ({visible, onDismissSnackBar, onPress, content}: SnackProps)=> {
    return(
        <Snackbar
            visible={visible}
            onDismiss={onDismissSnackBar}
            action={{
                label: '확인',
                onPress: onPress
            }}>
            {content}
        </Snackbar>
    )
}

export default Snack