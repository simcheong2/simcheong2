import AsyncStorage from '@react-native-async-storage/async-storage';

// get
export const getStorage = async (key: string) => {
    const result = await AsyncStorage.getItem(key);
    return result && JSON.parse(result);
};

// set
export const setStorage = async (key: string, value:string) => {
    return await AsyncStorage.setItem(key, JSON.stringify(value));
};

// remove
export const removeStorage = async (key:string) => {
    return await AsyncStorage.removeItem(key);
};