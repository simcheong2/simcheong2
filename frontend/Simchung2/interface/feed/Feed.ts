export interface FeedItemResponse{
    otherUserInfoResponse: OtherUserInfoResponse;
    posts: Post;
    comments: Comments[];
}

export interface OtherUserInfoResponse{
    profileUrl: string,
    nickname: string,
    isReport: boolean,
    isFollow: boolean,
}

export interface Post{
    postId: number,
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