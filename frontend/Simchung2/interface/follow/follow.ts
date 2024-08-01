export interface Follow{
    nickname: string,
    profile: string,
    isDisabled: boolean,
}

export interface Follower{
    nickname:string,
    profileUrl: string,
    isDisabled: boolean,
    isFollow: boolean,
}

export interface OtherFollow{
    nickname: string,
    profile: string,
    isDisabled: boolean,
    isMine: boolean,
}

export interface OtherFollower{
    nickname:string,
    profileUrl: string,
    isDisabled: boolean,
    isFollow: boolean,
    isMine: boolean,

}