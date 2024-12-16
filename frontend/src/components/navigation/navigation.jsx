import { Link } from "react-router-dom"

const Navigation = () => {
    return (
        <nav className='flex pad-10'>
            <Link to="/" className="pad-10">Home</Link>
        </nav>
    )
}

export default Navigation