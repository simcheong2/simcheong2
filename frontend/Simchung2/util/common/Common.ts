export const formatComma = (num: number) => {
        if (num >= 100000000) {
            return (num / 100000000).toFixed(1) + '억';
        } else if (num >= 10000) {
            return (num / 10000).toFixed(1) + '만';
        } else {
            return num.toLocaleString('ko-KR');
        }
    }