import {Hobby} from "../hobby/Hobby.tsx";

export type User = {
    id?:string,
    firstName: string,
    lastName: string,
    email: string,
    birthDay: string,
    hobbies?: Hobby[],
    isActive?: boolean
}