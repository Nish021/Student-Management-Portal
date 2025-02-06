import { useEffect, useState } from 'react';
import './Dashboard.css';
import { Link } from 'react-router-dom';

const Dashboard = () => {
    const [students, setStudents] = useState([]);  
    useEffect(() => {
        fetch(`http://localhost:9080/getAllStudentRecords`, {
            method: 'GET'
        })
        .then(response => response.json())
        .then(data => {
            console.log('Students:', data);
            setStudents(data);
        })
        .catch(error => console.error('Error:', error));
    }, []);
    return (
        <>
            <div className="dashboard-container">
                <div className="dashboard-new-student">
                    <button><Link to="/add">Add New Student</Link></button>
                </div>
                <div className="dashboard-header">
                    <h1>Student Dashboard</h1>
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
                        {students.map((student) => (
                            <tr key={student.id}>
                            <td>{student.id}</td>
                            <td>{student.name}</td>
                            <td>{student.age}</td>
                            <td>{student.className}</td>
                            <td>{student.phoneNumber}</td>
                            <td><button>Edit</button> <button>Delete</button></td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </>
    )
}

export default Dashboard;