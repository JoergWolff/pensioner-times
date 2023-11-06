import Header from "../header/Header.tsx";
import Navigation from "../navigation/Navigation.tsx";
import {ApiPaths} from "../../helpers/ApiPaths.tsx";
import {useEffect, useState} from "react";
import {PlaceModel} from "../../models/place/PlaceModel.tsx";
import axios from "axios";
import PlaceCard from "./PlaceCard.tsx";

export default function PlaceGallery() {
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
            <Navigation site="PlaceGallery"/>
            <main>
                {places?.map((place) => (
                        <div key={place.id}>
                            <PlaceCard place={place}/>
                        </div>
                    )
                )}
            </main>
        </>
    )
}