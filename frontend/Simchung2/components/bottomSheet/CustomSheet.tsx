import React, { useCallback, useEffect, useMemo, useRef } from 'react';
import { View, Text, StyleSheet, Button } from 'react-native';
import {
  BottomSheetModal,
  BottomSheetModalProvider,
} from '@gorhom/bottom-sheet';
import { useRecoilState, useRecoilValue } from 'recoil';
import modalAtom from '../../recoil/modalAtom';

interface CustomSheetProps{
    children?: React.ReactNode;
    snapPoint: string[]
}

const CustomSheet = ({children,snapPoint}: CustomSheetProps) => {
  // ref
  const bottomSheetModalRef = useRef<BottomSheetModal>(null);

  const [modalIndex, setModalIndex] = useRecoilState<number>(modalAtom)

  // variables
  const snapPoints = useMemo(() => snapPoint, []);

  useEffect(()=>{
    if(modalIndex>0){
        bottomSheetModalRef.current?.present();
    }
  },[modalIndex])

  const handleSheetChanges = useCallback((index: number) => {
    if(index<0){
        setModalIndex(index)
    }
  }, []);

  // renders
  return (
    <BottomSheetModalProvider>
        <BottomSheetModal
          ref={bottomSheetModalRef}
          index={2}
          snapPoints={snapPoints}
          onChange={handleSheetChanges}
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

export default CustomSheet