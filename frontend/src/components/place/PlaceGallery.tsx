import Header from "../header/Header.tsx";
import Navigation from "../navigation/Navigation.tsx";
import {ApiPaths} from "../../helpers/ApiPaths.tsx";
import {useEffect, useState} from "react";
import {PlaceModel} from "../../models/place/PlaceModel.tsx";
import axios from "axios";
import PlaceCard from "./PlaceCard.tsx";
import {UserModel} from "../../models/user/UserModel.tsx";

type PlaceGalleryProps = {
    loginUser: UserModel | undefined
}
export default function PlaceGallery(props: Readonly<PlaceGalleryProps>) {
    const uri: string = ApiPaths.PLACE_API
    const [places, setPlaces] = useState<PlaceModel[]>()
    useEffect(() => {
        getAllPlaces()
    }, []);

    function getAllPlaces() {
        axios.get(uri)
            .then(response => setPlaces(response.data))
            .catch(() => {
                alert('Check your connections...')
            })
    }

    return (
        <>
            <Header headAddOn="Place Gallery"/>
            <Navigation loginUser={props.loginUser} site="PlaceGallery"/>
            <main>
                {places?.map((place) => (
                        <div key={place.id}>
                            <PlaceCard loginUser={props.loginUser} place={place}/>
                        </div>
                    )
                )}
            </main>
        </>
    )
}