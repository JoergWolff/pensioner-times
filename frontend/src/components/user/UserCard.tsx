import {UserModel} from "../../models/user/UserModel.tsx";
import {AgeCalculator} from "../../helpers/AgeCalculator.tsx";
import {Link} from "react-router-dom";

type UserProps = {
    user: UserModel
    loginUser: UserModel | undefined
}
export default function UserCard(props: Readonly<UserProps>){
    const age = AgeCalculator(props.user?.birthDay)
    return(
        <article>
            <h3>{props.user.lastName + " " + props.user.firstName}</h3>
            <p>Alter: {age}</p>
            {props.user.id === props.loginUser?.id ? <Link to={`/user/${props.user.id}`}>Details</Link>:""}
        </article>
    )
}