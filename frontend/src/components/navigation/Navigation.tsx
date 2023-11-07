import {Link} from "react-router-dom";

type NavigationProps = {
    site: string
}
export default function Navigation(props: Readonly<NavigationProps>) {
    return (
        <>
            {props.site == "Home" &&
                <nav>
                    <Link to="/users">USERS</Link>
                    <Link to="/user/add">NEW USER</Link>
                    <Link to="/places">PLACES</Link>
                </nav>
            }
            {props.site == "UserGallery" &&
                <nav>
                    <Link to="/">HOME</Link>
                    <Link to="/user/add">NEW USER</Link>
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
                    <Link to="/place/add">NEW PLACE</Link>
                </nav>
            }
            {props.site == "PlaceAddDetail" &&
                <nav>
                    <Link to="/">HOME</Link>
                    <Link to="/places">PLACES</Link>
                    <Link to="/place/add">NEW PLACE</Link>
                </nav>
            }
        </>


    )
}