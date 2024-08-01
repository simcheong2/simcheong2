import React from 'react';
import { Snackbar } from 'react-native-paper';

interface SnackProps{
    visible: boolean;
    onDismissSnackBar: ()=>void;
    onPress: ()=>void;
    content: string;
    comment?: string;
}

const Snack = ({visible, onDismissSnackBar, onPress, content, comment}: SnackProps)=> {
    return(
        <Snackbar
            accessibilityLabel={comment}
            accessible
            importantForAccessibility='yes'
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