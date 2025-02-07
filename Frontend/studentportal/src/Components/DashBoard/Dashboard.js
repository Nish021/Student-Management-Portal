import { useEffect, useState } from 'react';
import './Dashboard.css';
import { Link } from 'react-router-dom';
import Pagination from '../Pagination/Pagination';

const Dashboard = () => {
    const [students, setStudents] = useState([]);  
    const [search, setSearch] = useState('');
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const pageSize = 5;
    const [deleted, setDeleted] = useState("");
    const [searchError, setSearchError] = useState("");

    const handleChange = (event) => {
        setSearch(event.target.value);
    }

    const handleOnDelete = (id) => {
        fetch(`http://localhost:9080/deleteStudentRecord/${id}`, {
            method: 'DELETE'
        })
        .then(response => response.text())
        .then(data => {
            console.log('Student deleted:', data);
            setDeleted("Student deleted successfully!");
            setStudents(students.filter(student => student.id !== id));
            setTimeout(() => {
                setDeleted("");
            }, 1000);
        })
        .catch(error => {
            console.error('Error:', error)
            setDeleted("Internal server error");
            setTimeout(() => {
                setDeleted("");
            }, 1000);
        });
    }

    const handleOnClick = (id) => {
        setSearchError("");
        let api = "";
        if(search === ""){
            api = `http://localhost:9080/getAllStudentRecords?page=${currentPage}&limit=${pageSize}`
        }else {
            api = `http://localhost:9080/getStudentsByName?studentName=${search}&page=${currentPage}&limit=${pageSize}`
        }
        fetch(api, {
            method: 'GET'
        })
        .then(response => response.json())
        .then(data => {
            console.log('Student:', data);
            if (data.content.length === 0) {
                setSearchError("No students found matching the search term.");
            } else {
                setStudents(data.content);
                setTotalPages(data.totalPages);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            setSearchError("Student not present in the database");
    });
    }

    const handlePageChange = (page) => {
        if (page >= 0 && page < totalPages) {
            setCurrentPage(page);
        }
    };

    const fetchStudents = (currentPage) => {
        fetch(`http://localhost:9080/getAllStudentRecords?page=${currentPage}&limit=${pageSize}`, {
            method: 'GET'
        })
        .then(response => response.json())
        .then(data => {
            console.log('Students:', data);
            setStudents(data.content);
            setTotalPages(data.totalPages);
        })
        .catch(error => console.error('Error:', error));
    };

    useEffect(() => {
        setSearchError("");
        fetchStudents(currentPage);
    }, [currentPage]);

    return (
        <>
            <div className="dashboard-container">
                <div className="dashboard-new-student">
                    <button><Link to="/add">Add New Student</Link></button>
                </div>
                <div className="dashboard-header">
                    <h1>Student Dashboard</h1>
                </div>
                <div className="dashboard-search">
                    <div className="search-label">
                        <input
                         placeholder="Search by name"
                         value={search}
                         onChange={handleChange}
                         type="text" 
                         name="name"
                         ></input>
                    </div>
                    <div className='search-button'>
                        <button
                        onClick={handleOnClick}
                        >Search</button>
                    </div>  
                </div>
                <div className="dashboard-table">
                    <table>
                        <thead>
                            <tr>
                                <th>Student Id</th>
                                <th>Name</th>
                                <th>Age</th>
                                <th>Class</th>
                                <th>Phone Number</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                        {searchError ? (
                            <tr>
                                <td colSpan="6">{searchError}</td>
                            </tr>
                        ) : students.map((student) => (
                            <tr key={student.id}>
                            <td>{student.id}</td>
                            <td>{student.name}</td>
                            <td>{student.age}</td>
                            <td>{student.className}</td>
                            <td>{student.phoneNumber}</td>
                            <td>
                                <button><Link to={`/edit/${student.id}`}>Edit</Link></button>
                                <button onClick={() => handleOnDelete(student.id)}>Delete</button>
                            </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
                <Pagination 
                    currentPage={currentPage}
                    totalPages={totalPages}
                    setCurrentPage={handlePageChange}
                />
                {deleted && <p className="deleted">{deleted}</p>}
            </div>
        </>
    )
}

export default Dashboard;