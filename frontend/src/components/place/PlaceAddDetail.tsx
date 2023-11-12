import Header from "../header/Header.tsx";
import Navigation from "../navigation/Navigation.tsx";
import {useNavigate, useParams} from "react-router-dom";
import {ApiPaths} from "../../helpers/ApiPaths.tsx";
import {ChangeEvent, FormEvent, useEffect, useState} from "react";
import axios from "axios";
import {PlaceModel} from "../../models/place/PlaceModel.tsx";

export default function PlaceAddDetail() {

    const params = useParams();
    const paramId: string | undefined = params.placeId;

    const navigate = useNavigate();
    const uri: string = ApiPaths.PLACE_API;

    const [id, setId] = useState<string>("");
    const [town, setTown] = useState<string>("");
    const [postalCode, setPostalCode] = useState<string>("");
    const [street, setStreet] = useState<string>("");
    const [description, setDescription] = useState<string>("");

    useEffect(() => {
        if (paramId) {
            getPlaceById(paramId)
        }
    }, []);

    function getPlaceById(id: string) {
        axios.get(uri + "/" + id)
            .then(response => setPlaceDetails(response.data))
            .catch((error) => {
                alert(error.response.data)
                navigate("/")
            });
    }

    function setPlaceDetails(placeInformation: PlaceModel) {
        if (placeInformation) {
            if (placeInformation.id) {
                setId(placeInformation.id)
                setTown(placeInformation.town)
                setPostalCode(placeInformation.postalCode)
                setStreet(placeInformation.street)
                if (placeInformation.description) {
                    setDescription(placeInformation.description)
                }
            }
        }
    }

    function onChangeTown(event: ChangeEvent<HTMLInputElement>) {
        setTown(event.target.value)
    }

    function onChangePostalCode(event: ChangeEvent<HTMLInputElement>) {
        setPostalCode(event.target.value)
    }

    function onChangeStreet(event: ChangeEvent<HTMLInputElement>) {
        setStreet(event.target.value)
    }

    function onChangeDescription(event: ChangeEvent<HTMLTextAreaElement>) {
        setDescription(event.target.value)
    }

    function onHandleCancel() {
        navigate("/places")
    }

    function onHandleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault()
        if (event) {
            const place: PlaceModel = {
                id: id,
                town: town,
                postalCode: postalCode,
                street: street,
                description: description
            }
            if (paramId) {
                axios.put(uri + "/update/" + place.id, place)
                    .then(() => navigate("/places"))
                    .catch((error) => {
                        alert(error.response.data)
                    })
            } else {
                axios.post(uri, place)
                    .then(() => navigate("/places"))
                    .catch((error) => {
                        alert(error.response.data)
                    })
            }
        }
    }

    return (
        <>
            {paramId ? <Header headAddOn="Detail Place"/> : <Header headAddOn="New Place"/>}
            <Navigation loginUser={undefined} site="PlaceAddDetail"/>
            <main>
                <form onSubmit={onHandleSubmit}>
                    <label htmlFor="town">Town:</label>
                    <input name="town" type="text" required onInput={onChangeTown} value={town}/>
                    <label htmlFor="postalCode">Postal Code:</label>
                    <input name="postalCode" type="text" minLength={5} maxLength={5} required
                           onInput={onChangePostalCode} value={postalCode}/>
                    <label htmlFor="street">Street:</label>
                    <input name="street" type="text" required onInput={onChangeStreet} value={street}/>
                    <label htmlFor="description">Description:</label>
                    <textarea name="description" rows={5} onInput={onChangeDescription} value={description}/>
                    <div className="form_button_div">
                        <input type="button" value="CANCEL" className="form_button" onClick={onHandleCancel}/>
                        {paramId ?
                            <input type="submit" value="UPDATE" className="form_button"/>
                            :
                            <input type="submit" value="SAVE" className="form_button"/>}
                    </div>
                </form>
                {paramId ? <button>DELETE</button> : ""}
            </main>
        </>

    )
}