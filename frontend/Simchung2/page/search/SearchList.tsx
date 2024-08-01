import React from 'react';
import { View } from 'react-native';
import { SearchResponse } from '../../interface/user/Search';
import User from './User';

interface SearchListProps{
    searchList: SearchResponse[];
    onPress: (nickname: string)=>void
}

const SearchList = ({searchList, onPress}:SearchListProps) =>{
    return(
        <View>
            {searchList.map((search, index)=>(
                <User key={index} user={search} onPress={onPress}/>
            ))}
        </View>
    )
}

export default SearchList