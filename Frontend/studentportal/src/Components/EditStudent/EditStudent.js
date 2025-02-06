import { useParams } from 'react-router-dom';
import '../AddStudent/AddStudent.css';
import { useEffect, useState } from "react";

const   EditStudent = () => {

    const {id} = useParams();
    const [dataUpdated, setDataUpdated] = useState("");

    const [formData, setFormData] = useState({
        name: '',
        age: '',
        class: '',
        phonenumber: '',
    });
    
    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };


    const handleFormSubmit = (event) => {
        event.preventDefault();
            const student = {
                name: formData.name,
                age:  formData.age,
                className:  formData.class,
                phoneNumber: formData.phonenumber
            };
            console.log(student);
            fetch(`http://localhost:9080/updateStudentRecord/${id}`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(student)
                })
                .then(response => response.json())
                .then(data => {
                    console.log('Student added:', data);
                    setDataUpdated("Student updated successfully!");
                    setFormData({
                        name: '',
                        age: '',
                        class: '',
                        phonenumber: '',
                    });
                    })
                .catch(error => {
                console.error('Error:', error);
                setDataUpdated("Internal server error");
            });
    }

    useEffect(() => {
        fetch(`http://localhost:9080/getStudentById/${id}`)
            .then(response => response.json())
            .then(data => {
                console.log('Student:', data);
                setFormData({
                    name: data.name,
                    age: data.age,
                    class: data.className,
                    phonenumber: data.phoneNumber,
                });
            })
            .catch(error => console.error('Error:', error));
    }, [id]);

    return (
        <div className="add-student-container">
           <div className="add-student">
            <div className="student-form-container">
                    <form onSubmit={handleFormSubmit}>
                        <label>
                            <div className="row">
                                <div className="col-1">Name</div>
                                <div className="col-2">
                                    <input 
                                        type="text" 
                                        name="name"
                                        value={formData.name}
                                        onChange={handleInputChange} 
                                    />
                                </div>
                            </div>
                        </label>
                        <br />
                        <label>
                            <div className="row">
                                <div className="col-1">Age</div>
                                <div className="col-2">
                                    <input 
                                        type="text" 
                                        name="age"
                                        value={formData.age}
                                        onChange={handleInputChange} 
                                    />
                                </div>
                            </div>
                        </label>
                        <br />
                        <label>
                            <div className="row">
                                <div className="col-1">Class</div>
                                <div className="col-2">
                                    <input 
                                        type="text" 
                                        name="class" 
                                        value={formData.class}
                                        onChange={handleInputChange} 
                                    />
                                </div>
                            </div>
                        </label>
                        <br />
                        <label>
                            <div className="row">
                                <div className="col-1">Phone Number</div>
                                <div className="col-2">
                                    <input
                                         type="number" 
                                         name="phonenumber" 
                                         value={formData.phonenumber}
                                         onChange={handleInputChange} 
                                    />
                                </div>
                            </div>
                        </label>
                        <br />
                        <button type="submit">Update the record</button>
                        <h2>{dataUpdated}</h2>
                    </form>
             </div>
            </div>
        </div>
    );
}

export default EditStudent;