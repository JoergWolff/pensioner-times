import Navigation from "../navigation/Navigation.tsx";
import Header from "../header/Header.tsx";

export default function Home(){
    return(
        <>
            <Header children={"Home"}/>
            <Navigation site={"Home"}/>
            <main>
                <h2>Nothing to see...</h2>
            </main>
        </>

    )
}