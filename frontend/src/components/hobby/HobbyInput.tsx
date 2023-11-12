import {HobbyInputModel} from "../../models/hobby/HobbyInputModel.tsx";
import {ChangeEvent, useState} from "react";
import {UserModel} from "../../models/user/UserModel.tsx";

type HobbyInput = {
    hobbyInputModel: HobbyInputModel,
    onHandleRemoveHobby: (id: string) => void,
    loginUser: UserModel | undefined
}
export default function HobbyInput(props: Readonly<HobbyInput>) {
    const [hobby, setHobby] = useState<string>(props.hobbyInputModel.value || "")



    function onHandleChange(event: ChangeEvent<HTMLInputElement>) {
        if (event) {
            const value = event.target.value
            setHobby(value)
        }
    }

    return (
        <div id={props.hobbyInputModel.id}>
            <input type="text" name="hobby" onChange={onHandleChange} value={hobby}/>
            {props.loginUser?.id ?
                <button onClick={() => props.onHandleRemoveHobby(props.hobbyInputModel.id)}>X</button>
                :
                ""}

        </div>
    )
}