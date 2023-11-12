import {MeetingModel} from "../../models/meeting/MeetingModel.tsx";
import {UserModel} from "../../models/user/UserModel.tsx";

type MeetingCardProps = {
    meeting: MeetingModel
    loginUser: UserModel | undefined
}
export default function MeetingCard(props: Readonly<MeetingCardProps>) {
    return (
        <article>
            <h3>{props.meeting.meetingDay} {props.meeting.meetingTime}</h3>
            <p>{props.meeting.description}</p>
            <p>Max User: {props.meeting.maxUser} Currently: {props.meeting.userCounter}</p>
            {props.loginUser && props.meeting.maxUser > props.meeting.userCounter ? <p>Participate?</p> : <p>Sorry Full House</p>}
            <p></p>

        </article>
    )
}