
export interface User {
    username: string;
    password: string; //todo
    fName: string;
    lName: string;
    role: string[]; //associerad senare
    yoe: number[]; // role.yoe
    moe: number[]; // role.moe
    available: false;
    uid: number,
}