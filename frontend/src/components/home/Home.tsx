import Navigation from "../navigation/Navigation.tsx";
import Header from "../header/Header.tsx";
import HobbyCollectorGallery from "../hobby/HobbyCollectorGallery.tsx";
import {UserModel} from "../../models/user/UserModel.tsx";
import UserCard from "../user/UserCard.tsx";
import HomeAnnouncement from "./HomeAnnouncement.tsx";

type HomeProps = {
    loginUser: UserModel | undefined
    login: () => void
    logout: () => void
}
export default function Home(props: Readonly<HomeProps>) {

    function onHandleLoginButton() {
        props.login()
    }

    function onHandleLogoutButton() {
        props.logout()
    }

    return(
        <>
            <Header headAddOn={"Home"}/>
            <Navigation loginUser={props.loginUser} site={"Home"}/>
            <main>
                <div className="div_dash">
                    <div><HobbyCollectorGallery/></div>
                    {!props.loginUser && <div><HomeAnnouncement/></div>}
                    {props.loginUser?.id ?
                        <div><UserCard user={props.loginUser} loginUser={props.loginUser}/>
                            <button onClick={onHandleLogoutButton}>Logout</button>
                        </div>
                        :
                        <div>
                            <button onClick={onHandleLoginButton}>Login</button>
                        </div>
                    }
                </div>
            </main>
        </>
    )
}