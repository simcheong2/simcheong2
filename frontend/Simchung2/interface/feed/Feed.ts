export interface FeedItemResponse{
    otherUserInfoResponse: OtherUserInfoResponse;
    posts: Post;
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
    createdAt: string,
    commentCount: number,
    isLiked: boolean,
    isReported: boolean,
}

export interface Images{
    imageUrl: string,
    imageText: string,
}

export interface Comments{
    nickname: string,
    comment: string,
}