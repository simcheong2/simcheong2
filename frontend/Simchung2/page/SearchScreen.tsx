import React, { useEffect, useState } from 'react';
import {
    Text,
    TextInput,
    View,
    NativeSyntheticEvent,
    StyleSheet,
    TextInputSubmitEditingEventData,
    TouchableOpacity, SafeAreaView, ScrollView,
} from 'react-native';
import Icon from 'react-native-vector-icons/Ionicons'
import { SearchResponse } from '../interface/user/Search';
import { EmptySearchList, SearchList as searchData } from '../util/test/user/Search';
import SearchList from './search/SearchList';

const SearchScreen = () => {
    const [search, setSearch] = useState<string>('');
    const [searchList, setSearchList] = useState<SearchResponse[]>(EmptySearchList);

    const handleSearch = (e: string) => {
        setSearch((prev) => (prev = e));
    };

    const handleSearchSubmit = (e: NativeSyntheticEvent<TextInputSubmitEditingEventData>) => {
        console.log("Search submitted:", search);
        setSearchList(searchData)
    }

    const clearSearch = () => {
        setSearch('');
    }

    useEffect(()=>{
        setSearch('');
        return(
            setSearchList(EmptySearchList)
        )
    },[])

    return (
        <View style={styles.container} accessibilityLabel="원하시는 유저를 검색해보세요.">
            <Text style={styles.title}>아이디 검색</Text>
            <View style={styles.searchContainer}>
                <Icon name="search" style={styles.searchIcon}/>
                <TextInput
                    style={styles.searchInput}
                    onChangeText={handleSearch}
                    value={search}
                    placeholder="검색"
                    returnKeyType='search'
                    onSubmitEditing={handleSearchSubmit}
                />
                {search.length > 0 && (
                    <TouchableOpacity onPress={clearSearch} style={styles.clearIcon}>
                        <Icon name="close-circle" size={20} color="#B0B0B0" />
                    </TouchableOpacity>
                )}
            </View>
            {searchList == EmptySearchList ? (
                <View>
                    <Text>검색해 주세요.</Text>
                </View>
            ): (
                <SafeAreaView style={styles['search-container']}>
                    <ScrollView style={styles['scroll-view']}>
                        <SearchList searchList={searchList}/>
                    </ScrollView>
                </SafeAreaView>
            )}
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        width: '100%',
        height: '100%',
        backgroundColor: '#fff',
        paddingHorizontal: 12,
        paddingTop: 12,
    },

    title: {
        fontSize: 16,
        marginTop: 44,
    },

    searchContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: '#F0F0F0',
        borderRadius: 8,
        paddingHorizontal: 12,
        width: '100%',
        height: 'auto',
        marginTop: 20,
    },

    searchIcon: {
        marginRight: 10,
    },

    searchInput: {
        flex: 1,
        height: 44,
        fontSize: 16,
    },

    clearIcon: {
        marginLeft: 10,
    },

    "search-container":{
        width:"100%",
        minHeight:"80%",
        marginTop: 4,
    },

    "scroll-view":{
        flexGrow: 1,
    }
});

export default SearchScreen;