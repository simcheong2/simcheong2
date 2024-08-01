import { Images } from '../feed/Feed';

export interface MyProfile{
    profile: Profile,
    posts: Posts[]
}

export interface Profile{
    followingCount: number,
    followerCount: number,
    email: string,
    profileUrl: string,
    nickname: string,
    sex: "MALE" | "FEMALE",
    isDisabled: boolean,
    isFollow: boolean,
}

export interface Posts{
    postId: number,
    images: Images[],
    content: string,
    likeCount: number,
    commentCount: number,
    isLiked: boolean,
    isReported: boolean,
    createdDate: string
}