import { react, useEffect, useState } from "react";
import DatePicker from "react-date-picker";
import 'react-date-picker/dist/DatePicker.css';
import ApiService from "../../service/ApiService";


const RoomSearch = ({ handelSearchResult }) =>
{
    const [startDate, setStartDate] = useState(null);
    const [endDate, setEndDate] = useState(null);
    const [roomType, setRoomType] = useState('');
    const [roomTypes, setRoomTypes] = useState([]);
    const [error, setError] = useState('');


    useEffect(() => {
        const fetchRoomType = async () => {
            try{
                const types = ApiService.getRoomTypes;
                setRoomTypes(types);
            }
            catch(err){
                console.log(err.message)
            }
        }
    }, [])

    const showError= (message, timeOut= 5000) =>
    {
        setError(message);
        setTimeout(() =>{
            setError=('');
        }, timeOut)
    };

    return (
        <section>
            <div className="search-container">
                <div className="search-field">
                    <label>Check-in Date</label>
                    <DatePicker
                        selected={startDate}
                        onChange={(date) => setStartDate(date)}
                        dateFormat="dd/MM/yyyy"
                        placeholderText="Select Check-in Date"
                    />
                </div>
                <div className="search-field">
                    <label>Check-out Date</label>
                    <DatePicker
                        selected={endDate}
                        onChange={(date) => setEndDate(date)}
                        dateFormat="dd/MM/yyyy"
                        placeholderText="Select Check-out Date"
                    />
                </div>

                <div className="search-field">
                    <label>Room Type</label>
                    <select value={roomType} onChange={(e) => setRoomType(e.target.value)}>
                        <option disabled value="">
                        Select Room Type
                        </option>
                        {roomTypes.map((roomType) => (
                        <option key={roomType} value={roomType}>
                            {roomType}
                        </option>
                        ))}
                    </select>
                </div>
                {/* <button className="home-search-button" onClick={handleInternalSearch}>
                    Search Rooms
                </button> */}
            </div>
            {error && <p className="error-message">{error}</p>}
        </section>
    );
}
export default RoomSearch;