export interface FeedItemResponse{
    otherUserInfoResponse: OtherUserInfoResponse;
    post: Post;
    comments: Comments[];
}

interface OtherUserInfoResponse{
    profileUrl: string,
    nickname: string,
    isReport: boolean,
    isFollow: boolean,
}

interface Post{
    images: Images[],
    content: string,
    likeCount: number,
    commentCount: number,
    isLike: boolean,
    isReport: boolean,
}

interface Images{
    imageUrl: string,
    imageText: string,
}

export interface Comments{
    nickname: string,
    comment: string,
}