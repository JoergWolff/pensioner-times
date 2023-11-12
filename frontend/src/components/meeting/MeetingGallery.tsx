import MeetingCard from "./MeetingCard.tsx";
import Header from "../header/Header.tsx";
import Navigation from "../navigation/Navigation.tsx";
import {ApiPaths} from "../../helpers/ApiPaths.tsx";
import axios from "axios";
import {useEffect, useState} from "react";
import {MeetingModel} from "../../models/meeting/MeetingModel.tsx";
import {UserModel} from "../../models/user/UserModel.tsx";

type MeetingProps = {
    loginUser: UserModel | undefined
}

export default function MeetingGallery(props: Readonly<MeetingProps>) {

    const uri: string = ApiPaths.MEETING_API;
    const [meetings, setMeetings] = useState<MeetingModel[]>()

    useEffect(() => {
        getAllMeetings()
    }, []);

    function getAllMeetings() {
        axios.get(uri)
            .then(response => setMeetings(response.data))
            .catch(() => {
                alert('Check your connections...')
            })
    }

    return (
        <>
            <Header headAddOn="Meeting Gallery"/>
            <Navigation loginUser={props.loginUser} site="MeetingGallery"/>
            <main>
                {meetings?.map((meeting) => (
                    <div key={meeting.id}>
                        <MeetingCard loginUser={props.loginUser} meeting={meeting}/>
                    </div>
                ))}
            </main>
        </>
    )
}