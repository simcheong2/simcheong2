import { atom } from 'recoil';

const loadingAtom = atom<number>({
    key: 'loading',
    default: 0,
})

export default loadingAtom