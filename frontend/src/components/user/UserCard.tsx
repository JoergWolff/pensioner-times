import {User} from "../../model/User.tsx";
import {AgeCalculator} from "../../helpers/AgeCalculator.tsx";

type UserProps = {
    user: User
}
export default function UserCard(props:UserProps){
    const age = AgeCalculator(props.user.birthDay)
    return(
        <article>
            <h3>{props.user.lastName + " " + props.user.firstName}</h3>
            <p>Alter: {age}</p>
        </article>
    )
}