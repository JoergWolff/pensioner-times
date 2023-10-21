export type User = {
    id?:string,
    firstName: string,
    lastName: string,
    email: string,
    birthDay: string,
    hobbies?: string[],
    isActive: boolean
}