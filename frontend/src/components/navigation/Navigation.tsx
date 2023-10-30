import {Link} from "react-router-dom";

type NavigationProps = {
    site: string
}
export default function Navigation(props:NavigationProps) {
    return (
        <>
            {props.site == "Home" &&
                <nav>
                    <Link to="/users">USERS</Link>
                    <Link to="/user/add">NEW USER</Link>
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
        </>


    )
}