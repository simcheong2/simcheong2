import React, { createContext, useContext, ReactNode } from 'react';
import { Follows } from '../../page/FollowScreen';

export const FollowContext = createContext<Follows | undefined>(undefined);

interface FollowProviderProps {
    value: Follows;
    children: ReactNode;
}

export const FollowProvider = ({ value, children }: FollowProviderProps) => {
    return (
        <FollowContext.Provider value={value}>
            {children}
        </FollowContext.Provider>
    );
};

export const useFollowContext = () => useContext(FollowContext);