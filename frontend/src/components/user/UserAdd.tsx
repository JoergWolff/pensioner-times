import Header from "../header/Header.tsx";
import Navigation from "../navigation/Navigation.tsx";
import {ChangeEvent, FormEvent, useState} from "react";
import {User} from "../../models/user/User.tsx";
import {ApiPaths} from "../../helpers/ApiPaths.tsx";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import {Hobby} from "../../models/hobby/Hobby.tsx";

export default function UserAdd() {

    const navigate = useNavigate();
    const uri: string = ApiPaths.USER_API;

    const [fieldsetCounter, setFieldsetCounter] = useState(1);
    const [firstName, setFirstName] = useState<string>("");
    const [lastName, setLastName] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const [birthDay, setBirthday] = useState<string>("");
    const [hobbies, setHobbies] = useState<Hobby[]>([]);

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
        setBirthday(event.target.value);
    }

    function onChangeHobbies(event: FormEvent<HTMLFieldSetElement>) {
        if (event) {
            getValuesFromFieldSet("fieldSet")
        }
    }

    function addInputToFieldSet(id: string) {
        if (id) {
            const fieldSet = document.getElementById(id)
            if (fieldSet) {
                const newDiv = document.createElement("div")
                newDiv.setAttribute("id", fieldsetCounter.toString())
                fieldSet.appendChild(newDiv)
                const newInputField = document.createElement("input")
                newInputField.setAttribute("type", "text")
                newInputField.setAttribute("name", "hobby")
                newDiv.appendChild(newInputField)
                const newButton = document.createElement("button")
                newButton.innerHTML = "X"
                newButton.onclick = () => removeInputFromFieldset(fieldsetCounter.toString())
                newDiv.appendChild(newButton)
                setFieldsetCounter(fieldsetCounter + 1)
            }
        }
    }

    function removeInputFromFieldset(id: string) {
        const deleteInputField = document.getElementById(id);
        if (deleteInputField) {
            deleteInputField.remove();
        }
    }

    function getValuesFromFieldSet(id: string) {
        if (id) {
            const fieldSet = document.getElementById(id);
            if (fieldSet) {
                const inputFields = fieldSet.querySelectorAll("input[name='hobby']");
                const values: Hobby[] = [];

                inputFields.forEach((element) => {
                    if (element instanceof HTMLInputElement) {
                        values.push({name: element.value})
                    }
                });
                setHobbies(values)
            }
        }
    }

    function onHandleCancel() {
        navigate("/")
    }

    function onHandleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault()
        if (event) {
            const user: User = {
                firstName: firstName,
                lastName: lastName,
                email: email,
                birthDay: birthDay,
                hobbies: hobbies
            }

            axios.post(uri, user)
                .then(() => navigate("/users"))
                .catch((error) => {
                    alert(error.response.data)
                })
        }
    }

    return (
        <>
            <Header children={"New User"}/>
            <Navigation/>
            <main>
                <h2>Your Information's</h2>
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
                    </fieldset>
                    <input type="button" value="Add Hobby" onClick={() => addInputToFieldSet("fieldSet")}/>
                    <div className="fieldset_div">
                        <input type="button" value="CANCEL" className="fieldset_button" onClick={onHandleCancel}/>
                        <input type="submit" value="SAVE" className="fieldset_button"/>
                    </div>
                </form>
            </main>
        </>
    )
}