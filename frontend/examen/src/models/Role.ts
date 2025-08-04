
export interface Role {
    username: string;
    password: string; //todo
    fName: string;
    lName: string;
    role: string[]; //associerad senare
    yoe: number[];
    moe: number[];
    available: false;
}