import {UserModel} from "../../models/user/UserModel.tsx";
import {AgeCalculator} from "../../helpers/AgeCalculator.tsx";
import {Link} from "react-router-dom";

type UserProps = {
    user: UserModel
}
export default function UserCard(props: Readonly<UserProps>){
    const age = AgeCalculator(props.user.birthDay)
    return(
        <article>
            <h3>{props.user.lastName + " " + props.user.firstName}</h3>
            <p>Alter: {age}</p>
            <Link to={`/user/${props.user.id}`}>Details</Link>
        </article>
    )
}