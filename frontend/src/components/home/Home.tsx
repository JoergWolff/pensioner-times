import Navigation from "../navigation/Navigation.tsx";
import Header from "../header/Header.tsx";
import {ApiPaths} from "../../helpers/ApiPaths.tsx";
import {useEffect, useState} from "react";
import axios from "axios";
import HobbyCollector from "../hobby/HobbyCollector.tsx";
import {HobbyCollectorModel} from "../../models/hobby/HobbyCollectorModel.tsx";

export default function Home(){
    const uri: string = ApiPaths.HOBBY_COLLECTOR_API

    const [hobbyCollectors, setHobbyCollectors] = useState<HobbyCollectorModel[]>()

    useEffect(() => {
        getAllHobbyCollectors()
    }, []);

    function getAllHobbyCollectors() {
        axios.get(uri)
            .then(response => {
                setHobbyCollectors(response.data)
            })
            .catch()
    }
    return(
        <>
            <Header headAddOn={"Home"}/>
            <Navigation site={"Home"}/>
            <main>
                <h2>Top Favorite Hobbies</h2>
                {hobbyCollectors?.sort((hobbyA,hobbyB)=>{return hobbyB.counter - hobbyA.counter})
                    .map((hobby) => (
                        <HobbyCollector hobbyCollector={hobby} key={hobby.id}/>
                    ))}
            </main>
        </>

    )
}