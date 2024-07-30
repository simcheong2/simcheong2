import { formatComma } from '../../common/Common';
import { Comments, FeedItemResponse } from '../../../interface/feed/Feed';

export const testUserFeeds = [
        {
            userName: "Lionell_Messi",
            feedContent: "이 이미지에는 축구 유니폼을 입은 사람이 트로피를 들고 환호하는 장면이 담겨 있습니다.",
            userImgUrl: "https://reactjs.org/logo-og.png",
            imgUrl: "https://i.namu.wiki/i/FylQ2OG1ppJWqGXu1AalEXK1HEDwql9pDvlK5EB4BZh-B2qJNpRfpCYtb_r6MgFtiXKPx5l5fawyO17tt4UMDta7k9zuy_GasunqDG58HglNcHlmIaEWjV_rf6tQvf3RWd0xGtsQ6htHaL5KR7NfPA.webp",
            favoriteCnt: formatComma(27000),
            commentCnt: formatComma(27000),
        },
        {
            userName: "Cristiano_Ronaldo",
            feedContent: "SIUUUUUUUUUUUUUUUUUUUUUUUU",
            userImgUrl: "https://reactjs.org/logo-og.png",
            imgUrl: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR4qqA-r4IaPjnDGL1Of1_mVHE6_ft-OXVebQ&s",
            favoriteCnt: formatComma(22000),
            commentCnt: formatComma(20000),
        },
        {
            userName: "katarinabluu",
            feedContent: "카리나 vs 이재용",
            userImgUrl: "https://reactjs.org/logo-og.png",
            imgUrl: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTtRCGjNQ-eOBVSrPManpSmj8FJIv3f_pNq6w&s",
            favoriteCnt: formatComma(222200000),
            commentCnt: formatComma(3333333),
        }
    ]

export const userFeeds = [
    {
        otherUserInfoResponse: {
            profileUrl: "https://reactjs.org/logo-og.png",
            nickname : "Lionell_Messi",
            isReport: false,
            isFollow: true
        },
        posts: {
            images:[
                {
                    imageUrl: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTtRCGjNQ-eOBVSrPManpSmj8FJIv3f_pNq6w&s",
                    imageText: "이 이미지에는 축구 유니폼을 입은 사람이 트로피를 들고 환호하는 장면이 담겨 있습니다."
                },
                {
                    imageUrl: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTtRCGjNQ-eOBVSrPManpSmj8FJIv3f_pNq6w&s",
                    imageText: "이 이미지에는 축구 유니폼을 입은 사람이 트로피를 들고 환호하는 장면이 담겨 있습니다."
                },
                {
                    imageUrl: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTtRCGjNQ-eOBVSrPManpSmj8FJIv3f_pNq6w&s",
                    imageText: "이 이미지에는 축구 유니폼을 입은 사람이 트로피를 들고 환호하는 장면이 담겨 있습니다."
                }
            ],
            content: "이 이미지에는 축구 유니폼을 입은 사람이 트로피를 들고 환호하는 장면이 담겨 있습니다.",
            likeCount: 27000,
            commentCount: 27000,
            isLiked: false,
            isReported: false,
            createdAt: "2024-07-30T10:06:07.856717"
        },
        comments:[
            {
                nickname: "Cristiano_Ronaldo",
                comment: "와 역시 메시 부럽다."
            },
            {
                nickname: "_Ronaldo_Cristiano",
                comment: "너 축구 개못하잖아"
            }
        ]
    }
]

export const EmptyComments: Comments[] = [
    {
        nickname: "",
        comment: '',
    }
]

export const EmptyFeeds: FeedItemResponse[] = [
    {
        otherUserInfoResponse: {
            profileUrl:'https://via.placeholder.com/150',
            nickname:'',
            isReport:false,
            isFollow:false
        },
        posts:{
            images:[
                {
                    imageUrl: 'https://via.placeholder.com/150',
                    imageText: '',
                }
            ],
            content: '',
            likeCount: 0,
            commentCount: 0,
            isLiked: false,
            isReported: false,
            createdAt: '',
        },
        comments:[
            {
                nickname: '',
                comment: '',
            }
        ]
    }
]