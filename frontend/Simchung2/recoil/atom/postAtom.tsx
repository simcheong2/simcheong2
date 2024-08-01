import { atom } from 'recoil';

const postAtom = atom<number>({
    key: 'postId',
    default: 0,
})

export default postAtom