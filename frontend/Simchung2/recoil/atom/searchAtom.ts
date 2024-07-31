import { atom } from 'recoil';

const searchAtom = atom<string>({
    key: 'search',
    default: '',
})

export default searchAtom