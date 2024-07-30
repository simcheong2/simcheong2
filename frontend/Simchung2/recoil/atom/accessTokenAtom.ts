import { atom } from 'recoil';

const accessTokenAtom = atom<string>({
    key: 'accessToken',
    default: '',
})

export default accessTokenAtom