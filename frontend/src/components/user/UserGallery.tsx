import Navigation from "../navigation/Navigation.tsx";
import {useEffect, useState} from "react";
import axios from "axios";
import UserCard from "./UserCard.tsx";
import {UserModel} from "../../models/user/UserModel.tsx";
import Header from "../header/Header.tsx";
import {ApiPaths} from "../../helpers/ApiPaths.tsx";

export default function UserGallery() {
    const uri: string = ApiPaths.USER_API
    const [users, setUsers] = useState<UserModel[]>();
    useEffect(() => {
        getAllUsers()
    }, );
    function getAllUsers(){
        axios.get(uri)
            .then(response => setUsers(response.data))
            .catch(()=>{
                alert('Problem')
            })
    }

    return (
        <>
            <Header headAddOn={"User Gallery"}/>
            <Navigation site={"UserGallery"}/>
            <main>
                {users?.map((user) =>(
                    <div key={user.id}>
                        <UserCard user={user}/>
                    </div>
                ))}
            </main>
        </>
    )
}