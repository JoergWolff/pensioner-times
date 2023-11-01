import {HobbyModel} from "../hobby/HobbyModel.tsx";

export type UserModel = {
    id?:string,
    firstName: string,
    lastName: string,
    email: string,
    birthDay: string,
    hobbies?: HobbyModel[],
    isActive?: boolean
}