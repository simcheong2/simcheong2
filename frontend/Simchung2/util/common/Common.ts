import * as ImageManipulator from 'expo-image-manipulator';

export const formatComma = (num: number) => {
    if (num >= 100000000) {
        return (num / 100000000).toFixed(1) + '억';
    } else if (num >= 10000) {
        return (num / 10000).toFixed(1) + '만';
    } else {
        return num.toLocaleString('ko-KR');
    }
};

export const ImageResize = async (uri: string) => {
    const imageInfo = await ImageManipulator.manipulateAsync(uri);
    const { width, height } = imageInfo;

    const newWidth = width * 0.3;
    const newHeight = height * 0.3;

    const manipulatedImage = await ImageManipulator.manipulateAsync(
        uri,
        [{ resize: { width: newWidth, height: newHeight } }],
        { compress: 1, format: ImageManipulator.SaveFormat.JPEG, base64: true },
    );

    const fileName = manipulatedImage.uri.split('/').pop();
    const fileType = fileName?.split('.').pop();
    const mimeType = `image/${fileType}`;

    return { manipulatedImage, fileName, mimeType }
};