import Header from "../header/Header.tsx";
import Navigation from "../navigation/Navigation.tsx";
import {useNavigate, useParams} from "react-router-dom";
import {ApiPaths} from "../../helpers/ApiPaths.tsx";
import {ChangeEvent, FormEvent, useEffect, useState} from "react";
import axios from "axios";
import {MeetingModel} from "../../models/meeting/MeetingModel.tsx";


export default function MeetingAddDetail() {

    const params = useParams();
    const paramId: string | undefined = params.meetingId;

    const navigate = useNavigate();
    const uri: string = ApiPaths.MEETING_API;

    const [id, setId] = useState<string>("")
    const [meetingDay, setMeetingDay] = useState<string>("")
    const [meetingTime, setMeetingTime] = useState<string>("")
    const [description, setDescription] = useState<string>("")
    const [maxUser, setMaxUser] = useState<string>("2")
    const [userCounter, setUserCounter] = useState<string>("1")
    const [userIds, setUserIds] = useState<string[]>([""])
    const [placeId, setPlaceId] = useState<string>("")

    useEffect(() => {
        if (paramId) {
            getMeetingById(paramId)
        }
    }, []);

    function getMeetingById(id: string) {
        axios.get(uri + "/" + id)
            .then(response => setMeetingDetails(response.data))
            .catch((error) => {
                alert(error.response.data)
                navigate("/")
            })
    }

    function setMeetingDetails(meetingInformation: MeetingModel) {
        if (meetingInformation) {
            if (meetingInformation.id) {
                setId(meetingInformation.id)
            }
            setMeetingDay(meetingInformation.meetingDay)
            setMeetingTime(meetingInformation.meetingTime)
            if (meetingInformation.description) {
                setDescription(meetingInformation.description)
            }
            setMaxUser(meetingInformation.maxUser)
            setUserCounter(meetingInformation.userCounter)
            setUserIds(meetingInformation.userIds)
            setPlaceId(meetingInformation.placeId)
        }
    }

    function onChangeMeetingDate(event: ChangeEvent<HTMLInputElement>) {
        setMeetingDay(event.target.value);
    }

    function onChangeMeetingTime(event: ChangeEvent<HTMLInputElement>) {
        setMeetingTime(event.target.value);
    }

    function onChangeDescription(event: ChangeEvent<HTMLTextAreaElement>) {
        setDescription(event.target.value);
    }

    function onChangeMaxUser(event: ChangeEvent<HTMLInputElement>) {
        setMaxUser(event.target.value);
    }

    function onChangeUserCounter(event: ChangeEvent<HTMLInputElement>) {
        setUserCounter(event.target.value);
    }

    function onChangePlace(event: ChangeEvent<HTMLInputElement>) {
        setPlaceId(event.target.value)
    }

    function onHandelSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault()
        if (event) {
            const meeting: MeetingModel = {
                id: id,
                meetingDay: meetingDay,
                meetingTime: meetingTime,
                description: description,
                maxUser: maxUser,
                userCounter: userCounter,
                userIds: userIds,
                placeId: placeId
            }
            console.log(meeting)
            if (paramId) {
                axios.put(uri + "/update/" + meeting.id)
                    .then(() => navigate("/meetings"))
                    .catch((error) => {
                        alert(error.response.data)
                    })
            } else {
                axios.post(uri, meeting)
                    .then(() => navigate("/meetings"))
                    .catch((error) => {
                        alert(error.response.data)
                    })
            }
        }
    }

    function onHandleCancel() {
        navigate("/meetings")
    }

    return (
        <>
            {paramId ? <Header headAddOn={"Detail Meeting"}/> : <Header headAddOn={"New Meeting"}/>}
            <Navigation site={"MeetingAddDetail"}/>
            <main>
                {paramId ? <h2>Your Meeting</h2> : <h2>Create Meeting</h2>}
                <form onSubmit={onHandelSubmit}>
                    <label htmlFor="meetingDay">Meeting Day:</label>
                    <input name="meetingDay" type="date" required onInput={onChangeMeetingDate} value={meetingDay}/>
                    <label htmlFor="meetingTime">Meeting Time:</label>
                    <input name="meetingTime" type="time" required onInput={onChangeMeetingTime} value={meetingTime}/>
                    <label htmlFor="description">Description:</label>
                    <textarea name="description" rows={5} onInput={onChangeDescription} value={description}/>
                    <label htmlFor="maxUser">Maximal Users:</label>
                    <input name="maxUser" type="number" min={2} onInput={onChangeMaxUser} value={maxUser}/>
                    <label htmlFor="userCounter">User Counter:</label>
                    <input name="userCounter" type="number" min={1} onInput={onChangeUserCounter} value={userCounter}/>
                    <label htmlFor="placeId">Place ID:</label>
                    <input name="placeId" type="text" onInput={onChangePlace} value={placeId}/>
                    <label htmlFor="userIds">User Ids:</label>
                    <input name="userIds" type="text" value={userIds} readOnly/>
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