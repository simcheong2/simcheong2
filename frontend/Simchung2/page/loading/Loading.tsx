import React, { useEffect, useRef } from 'react';
import { Dimensions, Animated, StyleSheet, Text, View, Easing } from 'react-native';

const Loading = () => {
    const { width, height } = Dimensions.get('window');
    const fadeAnim = useRef(new Animated.Value(0)).current;
    const progressAnims = [
        useRef(new Animated.Value(0)).current,
        useRef(new Animated.Value(0)).current,
        useRef(new Animated.Value(0)).current,
    ];

    useEffect(() => {
        // Image animation
        Animated.loop(
            Animated.sequence([
                Animated.timing(fadeAnim, {
                    toValue: 1,
                    duration: 500,
                    useNativeDriver: true,
                }),
                Animated.timing(fadeAnim, {
                    toValue: 0,
                    duration: 500,
                    useNativeDriver: true,
                }),
            ]),
        ).start();

        // Progress animations
        progressAnims.forEach((anim, index) => {
            Animated.loop(
                Animated.sequence([
                    Animated.delay(index * 200),
                    Animated.timing(anim, {
                        toValue: 1,
                        duration: 500,
                        easing: Easing.inOut(Easing.ease),
                        useNativeDriver: true,
                    }),
                    Animated.timing(anim, {
                        toValue: 0,
                        duration: 500,
                        easing: Easing.inOut(Easing.ease),
                        useNativeDriver: true,
                    }),
                ]),
            ).start();
        });
    }, [fadeAnim, progressAnims]);

    return (
        <View style={[styles.container, { width: width, height: height + 50 }]}>
            <Animated.Image
                style={[styles.img, { opacity: fadeAnim }]}
                source={require('../../assets/images/loading.png')}
            />
            <View style={styles['progress-container']}>
                {progressAnims.map((anim, index) => (
                    <Animated.View key={index} style={[styles.progress, { opacity: anim }]} />
                ))}
            </View>
            <View style={styles.popup} />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
        position: 'absolute',
        top: 0,
        left: 0,
    },

    popup: {
        flex: 1,
        width: '100%',
        height: '100%',
        backgroundColor: '#000',
        opacity: 0.2,
        position: 'absolute',
        top: 0,
        left: 0,
    },

    img: {
        width: 900 * 0.15,
        height: 970 * 0.15,
        marginBottom: 20,
    },

    "progress-container": {
        flexDirection: 'row',
        justifyContent: 'center',
        alignItems: 'center',
        marginBottom: 150,
        marginRight: 20,
    },

    progress: {
        borderRadius: 10,
        width: 20,
        height: 20,
        backgroundColor: "#000",
        marginHorizontal: 20,
    },
});

export default Loading;