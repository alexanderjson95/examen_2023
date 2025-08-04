import axios from 'axios';
import type { User } from '../models/User';
import type { Project } from '../models/Project';
import type { Schedule } from '../models/Schedule';
import type { UserProjects } from '../models/UserProjects';


export const Authenticate = async (username: string, password: string): Promise<User | null> => {
    const response = await axios.get<UserProjects>('/mockDB.json'); // ta bort lista på riktigt scenario, låt backend hantera
    return response.data.users.find(
        (u) => u.username === username && u.password === password
    ) || null;
}


export const getProjectsByUserId = async (userID: number ): Promise<Project[]> => {
    console.log("from back: " + userID)
    const response = await axios.get<UserProjects>('/mockDB.json'); // ta bort lista på riktigt scenario, låt backend hantera
    const projects: Project[] = response.data.projects;
    return projects.filter(p => p.userId.includes(userID))
}


export const getSchedulesByUserId = async (userID: number ): Promise<Schedule | null> => {
    const response = await axios.get<User>('/mockDB.json');
    return response.data
}

export const createUser = async (user: User): Promise<boolean> => {
    return true
}

