import {Route, Routes, useNavigate} from "react-router-dom";
import UserGallery from "./components/user/UserGallery.tsx";
import Home from "./components/home/Home.tsx";
import UserAddDetails from "./components/user/UserAddDetails.tsx";
import PlaceGallery from "./components/place/PlaceGallery.tsx";
import PlaceAddDetail from "./components/place/PlaceAddDetail.tsx";
import MeetingAddDetail from "./components/meeting/MeetingAddDetail.tsx";
import MeetingGallery from "./components/meeting/MeetingGallery.tsx";
import {useState} from "react";
import {UserModel} from "./models/user/UserModel.tsx";
import {ApiPaths} from "./helpers/ApiPaths.tsx";
import axios from "axios";

export default function App() {
    const uri = ApiPaths.USER_API
    const navigate = useNavigate()
    const [loginUser, setLoginUser]= useState<UserModel>()

    function getLoginUser(id: string) {
        axios.get(uri + "/" + id)
            .then((response) => setLoginUser(response.data))
            .catch((error) => {
                alert(error.response.data)
                navigate("/")
            });
    }

    function Login(){
        getLoginUser("a697e00d-c266-4a4f-b0cc-1426b680b9f1")
    }
    function Logout(){
        setLoginUser(undefined);
    }

    return (
            <Routes>
                <Route path="/" element={<Home login={Login} logout={Logout} loginUser={loginUser}/>}/>
                <Route path="/users" element={<UserGallery loginUser={loginUser}/>}/>
                <Route path="/user/add" element={<UserAddDetails loginUser={loginUser}/>}/>
                <Route path="/user/:userId" element={<UserAddDetails loginUser={loginUser}/>}/>
                <Route path="/places" element={<PlaceGallery loginUser={loginUser}/>}/>
                <Route path="/place/add" element={<PlaceAddDetail/>}/>
                <Route path="/place/:placeId" element={<PlaceAddDetail/>}/>
                <Route path="/meetings" element={<MeetingGallery loginUser={loginUser}/>}/>
                <Route path="/meeting/add" element={<MeetingAddDetail/>}/>
                <Route path="/meeting/:meetingId" element={<MeetingAddDetail/>}/>
            </Routes>
    )
}