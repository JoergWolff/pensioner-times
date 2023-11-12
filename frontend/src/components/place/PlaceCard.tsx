import {PlaceModel} from "../../models/place/PlaceModel.tsx";
import {Link} from "react-router-dom";
import {UserModel} from "../../models/user/UserModel.tsx";

type PlaceCardProps = {
    place: PlaceModel
    loginUser: UserModel | undefined
}

export default function PlaceCard(props: Readonly<PlaceCardProps>) {
    return (
        <article>
            <h3>{props.place.postalCode} {props.place.town}</h3>
            <p>{props.place.street}</p>
            <p>{props.place.description}</p>
            {props.loginUser ?
                <Link to={`/place/${props.place.id}`}>Details</Link>
                :
                ""}

        </article>
    )
}