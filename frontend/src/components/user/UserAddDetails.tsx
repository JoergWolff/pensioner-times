import Header from "../header/Header.tsx";
import Navigation from "../navigation/Navigation.tsx";
import {ChangeEvent, FormEvent, useEffect, useState} from "react";
import {UserModel} from "../../models/user/UserModel.tsx";
import {ApiPaths} from "../../helpers/ApiPaths.tsx";
import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";
import {HobbyInputModel} from "../../models/hobby/HobbyInputModel.tsx";
import HobbyInput from "../hobby/HobbyInput.tsx";
import {HobbyModel} from "../../models/hobby/HobbyModel.tsx";

export default function UserAddDetails() {

    const params = useParams()
    const paramId: string | undefined = params.userId

    const navigate = useNavigate();
    const uri: string = ApiPaths.USER_API;

    const [id,setId]= useState<string>("")
    const [firstName, setFirstName] = useState<string>("");
    const [lastName, setLastName] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const [birthDay, setBirthDay] = useState<string>("");
    const [hobbies, setHobbies] = useState<HobbyModel[]>([]);
    const [fieldSetCounter, setFieldSetCounter] = useState(1)
    const [hobbyInputFields, setHobbyInputFields] = useState<HobbyInputModel[]>([])

    useEffect(() => {
        if (paramId) {
            getUserById(paramId)
        }
    }, []);

    function getUserById(id: string) {
        axios.get(uri + "/" + id)
            .then((response) => setUserDetails(response.data))
            .catch((error) => {
                alert(error.response.data)
                navigate("/")
            });
    }

    function setUserDetails(userInformation: UserModel) {
        if (userInformation) {
            if(userInformation.id){
                setId(userInformation.id)
            }
            setFirstName(userInformation.firstName)
            setLastName(userInformation.lastName)
            setEmail(userInformation.email)
            setBirthDay(userInformation.birthDay)
            if (userInformation.hobbies) {
                setHobbies(userInformation.hobbies)
                let counter: number = 1
                const hobbyInputs: HobbyInputModel[] = []
                userInformation.hobbies.forEach((hobby: HobbyModel) => {
                    hobbyInputs.push({"id": counter.toString(), "value": hobby.name})
                    counter++
                })
                setHobbyInputFields(hobbyInputs)
            }
        }
    }

    function onChangeFirstName(event: ChangeEvent<HTMLInputElement>) {
        setFirstName(event.target.value);
    }

    function onChangeLastName(event: ChangeEvent<HTMLInputElement>) {
        setLastName(event.target.value);
    }

    function onChangeEmail(event: ChangeEvent<HTMLInputElement>) {
        setEmail(event.target.value);
    }

    function onChangeBirthday(event: ChangeEvent<HTMLInputElement>) {
        setBirthDay(event.target.value);
    }

    function onHandleAddHobby() {
        const newInput: HobbyInputModel = {
            id: fieldSetCounter.toString(),
            value: ""
        }
        setHobbyInputFields([...hobbyInputFields, newInput])
        setFieldSetCounter(fieldSetCounter + 1)
    }

    function onHandleRemoveHobby(id: string) {
        const inputHobbies = hobbyInputFields.filter(field => field.id != id)
        setHobbyInputFields(inputHobbies)
    }

    function onChangeHobbies(event: FormEvent<HTMLFieldSetElement>) {
        if (event) {
            getValuesFromFieldSet()
        }
    }

    function getValuesFromFieldSet() {
        const fieldSet = document.getElementById("fieldSet");
            if (fieldSet) {
                const inputFields = fieldSet.querySelectorAll("input[name='hobby']");
                const values: HobbyModel[] = [];

                inputFields.forEach((element) => {
                    if (element instanceof HTMLInputElement) {
                        values.push({name: element.value})
                    }
                });
                setHobbies(values)
            }
    }

    function onHandleCancel() {
        navigate("/")
    }

    function onHandleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault()
        if (event) {
            getValuesFromFieldSet()
            const user: UserModel = {
                id:id,
                firstName: firstName,
                lastName: lastName,
                email: email,
                birthDay: birthDay,
                hobbies: hobbies
            }
            if (paramId) {
                axios.put(uri + "/update/" + user.id ,user)
                    .then(()=> navigate("/users"))
                    .catch((error)=>{
                        alert(error.response.data)
                    })
            } else {
                axios.post(uri, user)
                    .then(() => navigate("/users"))
                    .catch((error) => {
                        alert(error.response.data)
                    })
            }
        }
    }

    function onHandleDelete() {
        axios.delete(uri + "/delete/" + id)
            .then(()=> navigate("/users"))
            .catch((error)=>{
                alert(error.response.data)
            })
    }

    return (
        <>
            {paramId ? <Header headAddOn={"Detail User"}/> : <Header headAddOn={"New User"}/>}
            <Navigation site={"UserAddDetails"}/>
            <main>
                {paramId ? <h2>Your Information's</h2> : <h2>Create Information's</h2>}
                <form onSubmit={onHandleSubmit}>
                    <label htmlFor="firstName">Firstname:</label>
                    <input name="firstName" type="text" required onInput={onChangeFirstName} value={firstName}/>
                    <label htmlFor="lastName">Lastname:</label>
                    <input name="lastName" type="text" required onInput={onChangeLastName} value={lastName}/>
                    <label htmlFor="email">Email:</label>
                    <input name="email" type="email" required onInput={onChangeEmail} value={email}/>
                    <label htmlFor="birthDay">Birthday:</label>
                    <input name="birthDay" type="date" required onInput={onChangeBirthday} value={birthDay}/>
                    <label htmlFor="hobbies">Hobbies:</label>
                    <fieldset name="hobbies" id="fieldSet" onInput={onChangeHobbies}>
                        {hobbyInputFields.map((field) =>
                            <HobbyInput key={field.id} hobbyInputModel={field}
                                        onHandleRemoveHobby={onHandleRemoveHobby}/>)}
                    </fieldset>

                    <input type="button" value="Add HobbyModel" onClick={onHandleAddHobby}/>
                    <div className="fieldset_div">
                        <input type="button" value="CANCEL" className="fieldset_button" onClick={onHandleCancel}/>
                        {paramId ?
                            <input type="submit" value="UPDATE" className="fieldset_button"/>
                            :
                            <input type="submit" value="SAVE" className="fieldset_button"/>}
                    </div>
                </form>
                {paramId ? <button onClick={onHandleDelete}>DELETE</button> : ""}
            </main>
        </>
    )
}