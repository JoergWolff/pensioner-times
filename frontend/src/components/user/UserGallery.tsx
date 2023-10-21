import Navigation from "../navigation/Navigation.tsx";
import {useEffect, useState} from "react";
import axios from "axios";
import UserCard from "./UserCard.tsx";
import {User} from "../../model/User.tsx";
import Header from "../header/Header.tsx";

export default function UserGallery() {
    const uri: string = "/api/users"
    const [users, setUsers] = useState<User[]>();
    useEffect(() => {
        getAllUsers()
    }, []);
    function getAllUsers(){
        axios.get(uri)
            .then(response => setUsers(response.data))
            .catch(()=>{
                alert('Problem')
            })
    }

    return (
        <>
            <Header children={"User Gallery"}/>
            <Navigation/>
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