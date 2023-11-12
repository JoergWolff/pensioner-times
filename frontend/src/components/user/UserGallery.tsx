import Navigation from "../navigation/Navigation.tsx";
import {useEffect, useState} from "react";
import axios from "axios";
import UserCard from "./UserCard.tsx";
import {UserModel} from "../../models/user/UserModel.tsx";
import Header from "../header/Header.tsx";
import {ApiPaths} from "../../helpers/ApiPaths.tsx";

type UserGalleryProps = {
    loginUser: UserModel | undefined
}

export default function UserGallery(props: Readonly<UserGalleryProps>) {
    const uri: string = ApiPaths.USER_API
    const [users, setUsers] = useState<UserModel[]>();

    useEffect(() => {
        getAllUsers()
    }, );

    function getAllUsers(){
        axios.get(uri)
            .then(response => setUsers(response.data))
            .catch(()=>{
                alert('Check your connections...')
            })
    }

    return (
        <>
            <Header headAddOn={"User Gallery"}/>
            <Navigation loginUser={props.loginUser} site={"UserGallery"}/>
            <main>
                {users?.map((user) =>(
                    <div key={user.id}>
                        <UserCard loginUser={props.loginUser} user={user}/>
                    </div>
                ))}
            </main>
        </>
    )
}