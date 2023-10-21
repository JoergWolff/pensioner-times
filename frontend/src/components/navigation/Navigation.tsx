import {Link} from "react-router-dom";

export default function Navigation() {
    return (
        <nav>
            <Link to="/">HOME</Link>
            <Link to="/users">USERS</Link>
        </nav>
    )
}