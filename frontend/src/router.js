import { Route, Routes } from "react-router-dom"
import Home from "./pages/home/Home"

const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/" element={<Home />} />
        </Routes>
    )
}

export default AppRoutes