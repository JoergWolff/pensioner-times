import {Route, Routes} from "react-router-dom";
import UserGallery from "./components/user/UserGallery.tsx";
import Home from "./components/home/Home.tsx";
import UserAdd from "./components/user/UserAdd.tsx";

export default function App() {
    return (
        <>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/users" element={<UserGallery/>}/>
                <Route path="/users/add" element={<UserAdd/>}/>
            </Routes>
        </>
    )
}
