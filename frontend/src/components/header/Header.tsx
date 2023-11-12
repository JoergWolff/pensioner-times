

type HeaderProps = {
    headAddOn: string
}
export default function Header(props: Readonly<HeaderProps>) {
    return (
        <header>
            <h1>Pensioner Times</h1><div className="header_div">{props.headAddOn}</div>
        </header>
    )
}