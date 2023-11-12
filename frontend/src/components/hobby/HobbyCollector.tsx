import {HobbyCollectorModel} from "../../models/hobby/HobbyCollectorModel.tsx";

type HobbyCollectorProps = {
    hobbyCollector: HobbyCollectorModel
}
export default function HobbyCollector(props: Readonly<HobbyCollectorProps>) {
    return (
        <div className="div_hobby_collector">
            <p>{props.hobbyCollector.name}:</p>
            <p>{props.hobbyCollector.counter}</p>
        </div>
    )
}