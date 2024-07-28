import React from 'react';
import { View } from 'react-native';
import { SearchResponse } from '../../interface/user/Search';
import User from './User';

interface SearchListProps{
    searchList: SearchResponse[];
}

const SearchList = ({searchList}:SearchListProps) =>{
    return(
        <View>
            {searchList.map((search, index)=>(
                <User key={index} user={search}/>
            ))}
        </View>
    )
}

export default SearchList