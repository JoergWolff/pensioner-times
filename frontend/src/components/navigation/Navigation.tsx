import {Link} from "react-router-dom";
import {UserModel} from "../../models/user/UserModel.tsx";

type NavigationProps = {
    site: string
    loginUser: UserModel | undefined
}
export default function Navigation(props: Readonly<NavigationProps>) {
    return (
        <>
            {props.site == "Home" &&
                <nav>
                    <Link to="/users">USERS</Link>
                    <Link to="/places">PLACES</Link>
                    <Link to="/meetings">MEETINGS</Link>
                </nav>
            }
            {props.site == "UserGallery" &&
                <nav>
                    <Link to="/">HOME</Link>
                    {props.loginUser ? ""
                        :
                        <Link to="/user/add">NEW USER</Link>}
                </nav>
            }
            {props.site == "UserAddDetails" &&
                <nav>
                    <Link to="/">HOME</Link>
                    <Link to="/users">USERS</Link>
                </nav>
            }
            {props.site == "PlaceGallery" &&
                <nav>
                    <Link to="/">HOME</Link>
                    {props.loginUser ?
                        <Link to="/place/add">NEW PLACE</Link>
                        :
                        ""}

                </nav>
            }
            {props.site == "PlaceAddDetail" &&
                <nav>
                    <Link to="/">HOME</Link>
                    <Link to="/places">PLACES</Link>
                </nav>
            }
            {props.site == "MeetingGallery" &&
                <nav>
                    <Link to="/">HOME</Link>
                    {props.loginUser ?
                        <Link to="/meeting/add">NEW MEETING</Link>
                        :
                        ""}

                </nav>
            }
            {props.site == "MeetingAddDetail" &&
                <nav>
                    <Link to="/">HOME</Link>
                    <Link to="/meetings">MEETINGS</Link>

                </nav>
            }
        </>


    )
}