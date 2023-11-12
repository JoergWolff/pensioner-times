import {ApiPaths} from "../../helpers/ApiPaths.tsx";
import {useEffect, useState} from "react";
import {HobbyCollectorModel} from "../../models/hobby/HobbyCollectorModel.tsx";
import axios from "axios";
import HobbyCollector from "./HobbyCollector.tsx";

export default function HobbyCollectorGallery(){
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
            <h4>Top Hobbies</h4>
            {hobbyCollectors?.sort((hobbyA,hobbyB)=>{return hobbyB.counter - hobbyA.counter})
                .map((hobby) => (
                    <HobbyCollector hobbyCollector={hobby} key={hobby.id}/>
                ))}
        </>
    )
}