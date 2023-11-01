

type HeaderProps = {
    headAddOn: string
}
export default function Header(props: Readonly<HeaderProps>) {
    return (
        <header>
            <h1>Pensioner Times {props.headAddOn}</h1>
        </header>
    )
}