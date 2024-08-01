import React, { useCallback, useEffect, useMemo, useRef } from 'react';
import { View, StyleSheet } from 'react-native';
import {
    BottomSheetModal,
    BottomSheetModalProvider,
    BottomSheetBackdrop,
} from '@gorhom/bottom-sheet';
import { useRecoilState } from 'recoil';
import modalAtom from '../../recoil/modalAtom';

interface CustomSheetProps {
    children?: React.ReactNode;
    snapPoint: string[];
}

const CustomSheet = ({ children, snapPoint }: CustomSheetProps) => {
    // ref
    const bottomSheetModalRef = useRef<BottomSheetModal>(null);

    const [modalIndex, setModalIndex] = useRecoilState<number>(modalAtom);

    // variables
    const snapPoints = useMemo(() => snapPoint, [snapPoint]);

    useEffect(() => {
        if (modalIndex > 0) {
            bottomSheetModalRef.current?.present();
        }
    }, [modalIndex]);

    const handleSheetChanges = useCallback((index: number) => {
        if (index < 0) {
            setModalIndex(index);
        }
    }, [setModalIndex]);

    // renders
    return (
        <BottomSheetModalProvider>
            <BottomSheetModal
                ref={bottomSheetModalRef}
                index={2}
                snapPoints={snapPoints}
                onChange={handleSheetChanges}
                backdropComponent={(props) => (
                    <BottomSheetBackdrop
                        {...props}
                        opacity={0.5}
                        appearsOnIndex={0}
                        disappearsOnIndex={-1}
                        pressBehavior="close"
                    />
                )}
            >
                <View style={styles.contentContainer}>
                    {children}
                </View>
            </BottomSheetModal>
        </BottomSheetModalProvider>
    );
};

const styles = StyleSheet.create({
    contentContainer: {
        flex: 1,
        alignItems: 'center',
    },
});

export default CustomSheet;
