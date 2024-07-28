import { atom } from "recoil";

const modalAtom = atom<number>({
    key: 'modal',
    default: -1,
})

export default modalAtom