import type { Role } from './Role'

export interface Project {
    projectName: string;
    projectDescription: string; //todo
    startDate: string;
    endDate: string;
    created: string[]; //associerad senare
    hiring: string[]; //  null = false role.yoe/moe = krav på erfarenhet, role sen
    visibility: boolean
    createdBy: string
    genre: string, // sedan genre objekt mappat
    salary: boolean,
    runtime: number, // [1.53] = 1h.53 min , [0.35] = 35 min
    id: Number,
    userId: Number[] //todo ta bort sen
}