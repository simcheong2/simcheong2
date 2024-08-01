import React from 'react';
import { Image, StyleSheet, Text, TouchableOpacity, View, FlatList, Dimensions, ListRenderItem } from 'react-native';
import { MyProfile, Posts } from '../../interface/user/Profile';

interface MyPageProps {
    myProfile: MyProfile;
    filterClick: (item: Posts) => void;
    feedClick: () => void;
    editClick: () => void;
    followClick: (follow: 'follow' | 'follower') => void;
    followingClick: ()=>void;
    isMine: boolean;
}

const Profile = ({ myProfile, filterClick, feedClick, editClick, followClick, followingClick, isMine }: MyPageProps) => {
    const profileUrl = { uri: `${myProfile.profile.profileUrl}` };
    // Calculate the size for grid items
    const { width } = Dimensions.get('window');
    const itemSize = (width) / 2; // Adjusted to maintain margins

    const renderPost: ListRenderItem<Posts> = ({ item }) => (
        <TouchableOpacity accessibilityLabel={item.images[0].imageText}
            style={[styles.postContainer, { width: itemSize, height: itemSize }]}
            onPress={() => filterClick(item)}>
            <Image source={{ uri: item.images[0].imageUrl }} style={styles.postImage} />
        </TouchableOpacity>
    );

    return (
        <View accessible={false} style={styles.container}>
            <View accessibilityLabel={myProfile.profile.nickname} style={styles.header}>
                <Text style={styles['header-title']}>{myProfile.profile.nickname}</Text>
            </View>
            <View accessible={false} style={styles['my-profile']}>
                <Image accessible={false} style={styles['profile-image']} source={profileUrl} resizeMode="cover" />
                <TouchableOpacity accessibilityLabel={`게시물${myProfile.posts.length}개 입니다.`} style={styles.touchOpacity} onPress={feedClick}>
                    <Text style={styles.bold}>{myProfile.posts.length}</Text>
                    <Text style={styles.medium}>게시물</Text>
                </TouchableOpacity>
                <TouchableOpacity accessibilityLabel={`팔로워${myProfile.profile.followerCount}명 입니다.`} style={styles.touchOpacity} onPress={() => followClick('follower')}>
                    <Text style={styles.bold}>{myProfile.profile.followerCount}</Text>
                    <Text style={styles.medium}>팔로워</Text>
                </TouchableOpacity>
                <TouchableOpacity accessibilityLabel={`팔로잉${myProfile.profile.followingCount}명 입니다.`} style={styles.touchOpacity} onPress={() => followClick('follow')}>
                    <Text style={styles.bold}>{myProfile.profile.followingCount}</Text>
                    <Text style={styles.medium}>팔로잉</Text>
                </TouchableOpacity>
            </View>
            <View style={styles['profile-edit']}>
                {isMine ?
                    <TouchableOpacity accessibilityLabel='프로필 편집을 원하시면 두번 탭 하세요.'
                        style={styles['edit-button']}
                        onPress={editClick}
                    >
                        <Text style={{ fontSize: 12, color: '#fff' }}>프로필 편집</Text>
                    </TouchableOpacity>
                    :
                    <TouchableOpacity accessibilityLabel={myProfile.profile.isFollow?'팔로우를 취소하고 싶으시면 두번 탭하세요.' : '팔로우 하고 싶으시면 두번 탭하세요.'}
                        style={styles['edit-button']}
                        onPress={followingClick}
                    >
                        <Text style={{ fontSize: 12, color: '#fff' }}>{myProfile.profile.isFollow?'팔로우 중':'팔로우'}</Text>
                    </TouchableOpacity>
                }
            </View>
            <FlatList
                data={myProfile.posts.flatMap(post => post)}
                renderItem={renderPost}
                keyExtractor={(item, index) => index.toString()}
                numColumns={2}
                contentContainerStyle={styles.grid}
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        width: '100%',
        height: '100%',
        backgroundColor: '#fff',
    },

    header: {
        width: '100%',
        height: 46,
        marginTop: 44,
        paddingLeft: 12,
        flexDirection: 'row',
        alignItems: 'center',
        borderBottomWidth: 1,
        borderBottomColor: '#EFF3F1',
    },

    'header-title': {
        fontSize: 24,
        color: '#555',
    },

    'my-profile': {
        width: '100%',
        padding: 12,
        marginTop: 12,
        flexDirection: 'row',
        display: 'flex',
        alignItems: 'center',
    },

    'profile-image': {
        width: 84,
        height: 84,
        borderRadius: 42,
        backgroundColor: '#000',
    },

    touchOpacity: {
        marginLeft: 44,
        flexDirection: 'column',
        alignItems: 'center',
    },

    bold: {
        fontSize: 16,
        fontWeight: 'bold',
    },

    medium: {
        fontSize: 12,
        fontWeight: 'medium',
    },

    'profile-edit': {
        height: 66,
        paddingHorizontal: 12,
        width: '100%',
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',
    },

    'edit-button': {
        width: '100%',
        backgroundColor: '#334792',
        borderRadius: 4,
        borderWidth: 1,
        borderColor: '#32458E',
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',
        height: 32,
    },
    grid: {
        marginTop: 10,
    },
    postContainer: {
        marginRight: 2,
        marginVertical: 2,
        backgroundColor: '#f0f0f0',
        borderWidth: 1,
    },
    postImage: {
        width: '100%',
        height: '100%',
    },
});

export default Profile;