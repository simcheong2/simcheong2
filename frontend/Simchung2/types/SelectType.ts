import { MyProfile } from '../interface/user/Profile';

export type SelectType = {
    select : 'other' | 'my'
    follow: 'Following' | 'Follower',
    profile: MyProfile
}