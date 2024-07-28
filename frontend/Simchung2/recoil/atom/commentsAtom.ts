import { atom } from "recoil";
import { Comments } from "../../../../../front/aaa/interface/feed/Feed";
import { EmptyComments } from "../../util/test/userFeed/UserFeed";

const commentsAtom = atom<Comments[]>({
    key: 'comments',
    default: EmptyComments,
})

export default commentsAtom