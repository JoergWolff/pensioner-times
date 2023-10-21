import {ReactNode} from "react";

type HeaderProps = {
    children: ReactNode
}
export default function Header(props:HeaderProps) {
    return (
        <header>
            <h1>Pensioner Times {props.children}</h1>
        </header>
    )
}