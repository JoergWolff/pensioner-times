import {HobbyCollectorModel} from "../../models/hobby/HobbyCollectorModel.tsx";

type HobbyCollectorProps = {
    hobbyCollector: HobbyCollectorModel
}
export default function HobbyCollector(props: Readonly<HobbyCollectorProps>) {
    return (
        <div>
            <h3>{props.hobbyCollector.name} : {props.hobbyCollector.counter}</h3>
        </div>
    )
}