import axios from 'axios';
import type { User } from '../models/User';


export const Authenticate = async (username: string, password: string): Promise<User | null> => {
    const response = await axios.get<User[]>('/mockDB.json');
    return response.data.find(
        (u) => u.username === username && u.password === password
    ) || null;
}


export const getUserByParam = async (param: ): Promise<User | null> => {
    const response = await axios.get<User[]>('/mockDB.json');
    return response.data.find(
        (u) => u.username === username && u.password === password
    ) || null;
}

export const createUser = async (user: User): Promise<boolean> => {
    return true
}

