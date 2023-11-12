import {Link} from "react-router-dom";

export default function HomeAnnouncement(){
    return(
        <div className="div_home_announcement">
        <h2>Welcome to Pensioner Times</h2>
            <p>When the pensioner's time has come and the children have left home, he will of course have no more time...</p>
            <p>But do you perhaps also want to have time for yourself?</p>
            <p>Meet others your age? Attend or arrange meetings?</p>
            <Link to="/user/add" className="participate">Participate</Link>
        </div>
    )
}
