import {HobbyInputModel} from "../../models/hobby/HobbyInputModel.tsx";
import {ChangeEvent, useState} from "react";

type HobbyInput = {
    hobbyInputModel: HobbyInputModel
    onHandleRemoveHobby: (id: string) => void,
}
export default function HobbyInput(props: HobbyInput) {
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
            <button onClick={() => props.onHandleRemoveHobby(props.hobbyInputModel.id)}>X</button>
        </div>
    )
}